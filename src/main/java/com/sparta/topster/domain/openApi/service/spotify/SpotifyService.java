package com.sparta.topster.domain.openApi.service.spotify;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.openApi.service.OpenApiService;
import com.sparta.topster.global.exception.ServiceException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.topster.domain.openApi.exception.ManiadbException.NOT_SERCH_ALBUM;

@Service
@Primary
@Slf4j(topic = "SpotifyService")
@RequiredArgsConstructor
public class SpotifyService implements OpenApiService {
    private final SpotifyUtil spotifyUtil;
    private String accessToken;
    @PostConstruct

    private void init(){
        accessToken = spotifyUtil.accesstoken();
    }

    @Scheduled(fixedDelay = 60 * 55 * 1000L)
    public void getAccessToken() {
        accessToken =  spotifyUtil.accesstoken();
    }


    @Override
    public String getRawArtistData(String query) {
        accessToken = spotifyUtil.accesstoken();
        log.info(query + "로 rawData 가져오기");
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest
                .exchange("https://api.spotify.com/v1/search?type=album&q="
                        + query + "&limit=30", HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }


    @Override
    public List<AlbumRes> getAlbums(String query) {
        String rawData = getRawArtistData(query);
        JSONObject rawJSONData = new JSONObject(rawData);
        log.info("rawData에서 item을 추출");
        if(rawJSONData.getJSONObject("albums").getJSONArray("items").isEmpty()){
            log.error(NOT_SERCH_ALBUM.getMessage());
            throw new ServiceException(NOT_SERCH_ALBUM);
        }
        JSONArray jsonArray = rawJSONData.getJSONObject("albums").getJSONArray("items");
        return fromJSONArrayToAlbum(jsonArray);
    }

    private List<AlbumRes> fromJSONArrayToAlbum(JSONArray items){
        List<AlbumRes> albumResList = new ArrayList<>();

        for (Object item : items) {
            JSONObject itemObj = (JSONObject) item;
            JSONArray artistArray = itemObj.getJSONArray("artists");
            StringBuilder sb = new StringBuilder();
            for (Object artist : artistArray) {
                sb.append(((JSONObject)artist).getString("name") + ", ");
            }
            String artistNames = sb.substring(0, sb.length() - 2).toString();
            log.info("artistName is " + artistNames);
            //
            AlbumRes albumRes = fromJSONtoAlbumRes(itemObj, artistNames);
            albumResList.add(albumRes);
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


    private String initialUpperCase(String s){
        String[] subString = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String sIndex : subString){
            sb.append(StringUtils.capitalize(sIndex));
        }
        return StringUtils.capitalize(sb.toString());
    }
}
