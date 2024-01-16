package com.sparta.topster.domain.open_api.service.maniadb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.open_api.service.OpenApiService;
import com.sparta.topster.domain.song.entity.Song;
import com.sparta.topster.domain.song.repository.SongRepository;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.topster.domain.open_api.exception.ManiadbException.NOT_SERCH_ALBUM;

@Service
@Qualifier("maniadb")
@Slf4j(topic = "ManiaServiceImpl")
@RequiredArgsConstructor
public class ManiadbService implements OpenApiService {

    private final RestTemplate restTemplate;

    @Override
    public String getRawArtistData(String query) {

        //maniadb에 api 요청을 날리기 위한 uri
        URI uri = UriComponentsBuilder.fromUriString("http://www.maniadb.com")
                .path("api/search/" + query + "/")
                .queryParam("sr", "album")
                .queryParam("display", 20)
                .queryParam("v", 0.5)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri).build();

        //uri를 날리고 온 response(XML)
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String rawDataJSON = fromXMLtoJSON(response.getBody());
        return rawDataJSON;
    }


    @Override
    public List<Album> getAlbums(String query) {
        log.info(query + "로 rawData 가져오기");
        String rawData = getRawArtistData(query);
        JSONObject rawJSONData = new JSONObject(rawData);
        log.info("rawData에서 item을 추출");
        if(rawJSONData.getJSONObject("rss").getJSONObject("channel").
                has("item")){
            return fromJSONArrayToAlbum(rawJSONData.getJSONObject("rss").getJSONObject("channel").
                    getJSONArray("item"), query);
        }
        log.error(NOT_SERCH_ALBUM.getMessage());
        throw new ServiceException(NOT_SERCH_ALBUM);
    }

    private List<Album> fromJSONArrayToAlbum(JSONArray items, String query){
        List<Album> albumList = new ArrayList<>();
        for(Object item : items){
            JSONObject itemObj = (JSONObject) item;
            log.info("maniadb:albumartists : " + itemObj.getString("maniadb:albumartists"));

            // 가수를 검색했을 때 제목에 가수가 포함된 것도 포함됨.
            // 따라서 albumartists가 query를 포함한 것만 필터링
            // maniadb에서 대문자/소문자를 구분하기 때문에 첫글자를 대문자로 변환하는 메소드 사용


            if(query.matches("^[a-zA-Z]*$")){
                if(itemObj.getString("maniadb:albumartists").contains(initialUpperCase(query))) {
                    log.info("쿼리문이 영어로 이루어져 있을 때");
                    log.info("필터링 된 maniadb:albumartists : " + itemObj.getString("maniadb:albumartists"));
                    Album album = fromJSONtoAlbum(itemObj);
                    List<Song> songList = fromJSONToSong((JSONObject) item, album);
                    album.setSongList(songList);
                    albumList.add(album);
                }
            }else{
                Album album = fromJSONtoAlbum(itemObj);
                List<Song> songList = fromJSONToSong((JSONObject) item, album);
                album.setSongList(songList);
                albumList.add(album);
            }
        }
        return albumList;
    }

    private List<Song> fromJSONToSong(JSONObject item, Album album){
        String trackList = item.getJSONObject("maniadb:albumtrack").getString("maniadb:tracklist");
        List<Song> songList = fromStringToSong(trackList,album);
        return songList;
    }

    private Album fromJSONtoAlbum(JSONObject albumJSON) {
        String title = albumJSON.getString("title");
        String artist = albumJSON.getString("maniadb:albumartists");
        String release = albumJSON.getString("release");
        String image = albumJSON.getString("image");

        Album album = Album.builder().title(title).artist(artist).release(release).image(image).build();
        return album;
    }

    private String initialUpperCase(String s){
        String[] subString = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String sIndex : subString){
            sb.append(StringUtils.capitalize(sIndex));
        }
        return StringUtils.capitalize(sb.toString());
    }

    // 여기도 tracklist가 비어있는 경우가 있음
    private List<Song> fromStringToSong(String trackList, Album album) {
        if (trackList.length() >= 9) {
            String subSt = trackList.substring(9);
            String[] stringSplit = subSt.split(" / ");
            List<Song> songList = new ArrayList<>();
            for (String s : stringSplit) {
                Song song = Song.builder().songname(s).build();
                song.setAlbum(album);
                songList.add(song);
            }
            return songList;
        }
        return null;
    }

    private String fromXMLtoJSON(String xml){
        try {
            JSONObject jsonObject = XML.toJSONObject(xml);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = objectMapper.readValue(jsonObject.toString(), Object.class);
            String rawData = objectMapper.writeValueAsString(json);
            return rawData;
        }
        catch (JsonProcessingException e){
            throw new ServiceException(NOT_SERCH_ALBUM);
        }
    }

}
