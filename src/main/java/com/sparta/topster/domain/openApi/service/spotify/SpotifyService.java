package com.sparta.topster.domain.openApi.service.spotify;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.openApi.service.OpenApiService;
import com.sparta.topster.global.exception.ServiceException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.sparta.topster.domain.openApi.exception.OpenApiException.NOT_SERCH_ALBUM;

@Service
@Primary
@Slf4j(topic = "SpotifyService")
@RequiredArgsConstructor
public class SpotifyService implements OpenApiService {
    private final SpotifyUtil spotifyUtil;
    private final RestClient restClient;
    private String accessToken;

    @PostConstruct
    @Scheduled(fixedDelay = 60 * 55 * 1000L)
    private void init(){
        accessToken = spotifyUtil.accesstoken();
    }


    @Override
    public String getRawArtistData(String query) {
        log.info(query + "로 rawData 가져오기");
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.spotify.com/v1/search")
                .queryParam("type", "album")
                .queryParam("q", query)
                .queryParam("limit", "30")
                .encode()
                .build()
                .toUri();

        Consumer<HttpHeaders> headersConsumer = (headers) -> {
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Host", "api.spotify.com");
            headers.add("Content-type", "application/json");
        };

        ResponseEntity<String> responseEntity = restClient.get()
                .uri(uri)
                .headers(headersConsumer)
                .retrieve()
                .toEntity(String.class);
        return responseEntity.getBody();
    }


    @Override
    public List<AlbumRes> getAlbums(String query) {
        log.info("rawData에서 item을 추출");
        String rawData = getRawArtistData(query);
        JSONArray itmes = new JSONObject(rawData).getJSONObject("albums").getJSONArray("items");
        checkExist(itmes);
        return fromJSONArrayToAlbum(itmes);
    }


    private List<AlbumRes> fromJSONArrayToAlbum(JSONArray items){
        List<AlbumRes> albumResList = new ArrayList<>();
        String artistNames;
        for (Object item : items) {
            JSONObject itemObj =  (JSONObject) item;
            artistNames = fromItemsToArtistName(itemObj);
            albumResList.add(fromJSONtoAlbumRes(itemObj, artistNames));
        }
        return albumResList;
    }



    private AlbumRes fromJSONtoAlbumRes(JSONObject albumJSON, String artistName) {
        String title = albumJSON.getString("name");
        String release = albumJSON.getString("release_date");
        String image = albumJSON.getJSONArray("images").getJSONObject(0).getString("url");

        return AlbumRes.builder().
                title(title).
                artist(artistName).
                releaseDate(release).
                image(image).
                build();
    }


    private String fromItemsToArtistName(JSONObject item){
        JSONArray artistArray = item.getJSONArray("artists");
        StringBuilder sb = new StringBuilder();
        for (Object artist : artistArray) {
            sb.append(((JSONObject)artist).getString("name") + ", ");
        }
        // 아티스트가 한명이 아닌 경우(합작 앨범) 처리
        String artistNames = sb.substring(0, sb.length() - 2);
        log.info("artistName is " + artistNames);
        return artistNames;
    }



    private void checkExist(JSONArray items){
        if(items.isEmpty()){
            log.error(NOT_SERCH_ALBUM.getMessage());
            throw new ServiceException(NOT_SERCH_ALBUM);
        }
    }
}
