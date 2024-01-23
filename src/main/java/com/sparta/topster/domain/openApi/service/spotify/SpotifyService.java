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
//    @PostConstruct
//    private void init(){
//        accessToken =spotifyUtil.accesstoken();
//    }
//
//    @Scheduled(cron = "* 55 * * * *")
//    public void getAccessToken() {
//        accessToken =  spotifyUtil.accesstoken();
//    }


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
                        + query, HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public List<AlbumRes> getAlbums(String query) {
        String rawData = getRawArtistData(query);
        JSONObject rawJSONData = new JSONObject(rawData);
        log.info("rawData에서 item을 추출");
//        if(rawJSONData.getJSONObject("albums").getBigInteger("total").equals(0)){
            JSONArray jsonArray = rawJSONData.getJSONObject("albums").getJSONArray("items");
            return fromJSONArrayToAlbum(jsonArray, query);
//        }
//        log.error(NOT_SERCH_ALBUM.getMessage());
//        throw new ServiceException(NOT_SERCH_ALBUM);
    }

    private List<AlbumRes> fromJSONArrayToAlbum(JSONArray items, String query){
        List<AlbumRes> albumResList = new ArrayList<>();
        for(Object item : items){
            JSONObject itemObj = (JSONObject) item;

            // 가수를 검색했을 때 제목에 가수가 포함된 것도 포함됨.
            // 따라서 albumartists가 query를 포함한 것만 필터링
            // maniadb에서 대문자/소문자를 구분하기 때문에 첫글자를 대문자로 변환하는 메소드 사용

            JSONArray artistArray = itemObj.getJSONArray("artists");
            String artistName = artistArray.getJSONObject(0).getString("name");
            log.info("artistName is " + artistName);
            if(query.matches("^[a-zA-Z]*$")){
                if(artistName.contains(initialUpperCase(query))) {
                    log.info("쿼리문이 영어로 이루어져 있을 때");
                    AlbumRes album = fromJSONtoAlbumRes(itemObj, artistName);
                    albumResList.add(album);
                }
            }else{
                AlbumRes albumRes = fromJSONtoAlbumRes(itemObj, artistName);
                albumResList.add(albumRes);
            }
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
