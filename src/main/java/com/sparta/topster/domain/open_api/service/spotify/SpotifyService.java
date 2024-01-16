package com.sparta.topster.domain.open_api.service.spotify;

import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.open_api.service.OpenApiService;
import com.sparta.topster.domain.song.entity.Song;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.topster.domain.open_api.exception.ManiadbException.NOT_SERCH_ALBUM;

@Service
@Primary
@Slf4j(topic = "SpotifyService")
@RequiredArgsConstructor
public class SpotifyService implements OpenApiService {
    private final SpotifyUtil spotifyUtil;
    private final RestTemplate restTemplate;

    public String getAccessToken() {
        String accessToken =  spotifyUtil.accesstoken();
        return accessToken;
    }


    @Override
    public String getRawArtistData(String query) {
        log.info(query + "로 rawData 가져오기");
        String accessToken = getAccessToken();
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);;
        headers.add("Host", "api.spotify.com");
        headers.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest
                .exchange("https://api.spotify.com/v1/search?type=album&q="
                        + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatusCode httpStatus = responseEntity.getStatusCode();
        return responseEntity.getBody();
    }

    @Override
    public List<Album> getAlbums(String query) {
        String rawData = getRawArtistData(query);
        JSONObject rawJSONData = new JSONObject(rawData);
        log.info("rawData에서 item을 추출");
        JSONArray jsonArray = rawJSONData.getJSONObject("albums").getJSONArray("items");
        return fromJSONArrayToAlbum(jsonArray, query);

//        log.error(NOT_SERCH_ALBUM.getMessage());
//        throw new ServiceException(NOT_SERCH_ALBUM);
    }

    private List<Album> fromJSONArrayToAlbum(JSONArray items, String query){
        List<Album> albumList = new ArrayList<>();
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
                    Album album = fromJSONtoAlbum(itemObj, artistName);
//                    List<Song> songList = fromJSONToSong((JSONObject) item, album);
//                    album.setSongList(songList);
                    albumList.add(album);
                }
            }else{
                Album album = fromJSONtoAlbum(itemObj, artistName);
//                List<Song> songList = fromJSONToSong((JSONObject) item, album);
//                album.setSongList(songList);
                albumList.add(album);
            }
        }
        return albumList;
    }


    private Album fromJSONtoAlbum(JSONObject albumJSON, String artistName) {
        String title = albumJSON.getString("name");
        String release = albumJSON.getString("release_date");
        String image = albumJSON.getJSONArray("images").getJSONObject(0).getString("url");

        return Album.builder().title(title).artist(artistName).release(release).image(image).build();
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
