package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sparta.topster.domain.album.dto.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.song.entity.Song;
import com.sparta.topster.domain.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "ManiaServiceImpl")
@RequiredArgsConstructor
public class ManiadbServiceImpl implements  ManiadbService{

    private final RestTemplate restTemplate;
    private final AlbumRepository albumRepository;
    private  final SongRepository songRepository;

    @Override
    public String getRawArtistData(String query) throws JsonProcessingException {

        //maniadb에 api 요청을 날리기 위한 uri
        URI uri = UriComponentsBuilder.fromUriString("http://www.maniadb.com")
                .path("api/search/" + query + "/")
                .queryParam("sr", "album")
                .queryParam("display", 10)
                .queryParam("v", 0.5)
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri).build();

        //uri를 날리고 온 response(XML)
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String xml = response.getBody();
        JSONObject jsonObject = XML.toJSONObject(xml);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Object json = objectMapper.readValue(jsonObject.toString(), Object.class);
        String rawData = objectMapper.writeValueAsString(json);
        return rawData;
    }

    @Transactional
    @Override
    public List<AlbumRes> getAlbumsByArtist(String query) throws JsonProcessingException {
        log.info("rawData 가져오기");
        String rawData = getRawArtistData(query);
        JSONObject rawJSONData =new JSONObject(rawData);
        log.info("rawData에서 item을 추출");
        JSONArray items = rawJSONData.getJSONObject("rss").getJSONObject("channel").
                getJSONArray("item");
        List<AlbumRes> albumResList = new ArrayList<>();
        for(Object item : items){
            JSONObject itemObj = (JSONObject) item;
            // 가수를 검색했을 때 제목에 가수가 포함된 것도 포함됨.
            log.info("maniadb:albumartists : " + itemObj.getString("maniadb:albumartists"));
            // 따라서 albumartists가 query를 포함한 것만 필터링
            // maniadb에서 대문자/소문자를 구분하기 때문에 첫글자를 대문자로 변환하는 메소드 사용
            if(itemObj.getString("maniadb:albumartists").contains(initialUpperCase(query))){
                log.info("필터링 된 maniadb:albumartists : " + itemObj.getString("maniadb:albumartists"));
                Album album = fromJSONtoAlbum(itemObj);
                album = albumRepository.save(album);
                if(itemObj.getJSONObject("maniadb:albumtrack")
                        .getJSONObject("major_tracks").has("song")){
                    JSONArray songs = itemObj.getJSONObject("maniadb:albumtrack")
                            .getJSONObject("major_tracks").getJSONArray("song");
                    for(Object songObj : songs){
                        JSONObject songName = (JSONObject)songObj;
                        String songNameSt = songName.getString("name");
                        Song song = Song.builder().songname(songNameSt).build();
                        song.setAlbum(album);
                        album.getSongList().add(song);
                    }
                    AlbumRes albumRes = AlbumRes.builder().title(album.getTitle()).artist(album.getArtist())
                            .release(album.getReleaseDate()).image(album.getImage()).build();
                    albumResList.add(albumRes);
                }

            }
        }
        return albumResList;
    }

    public Album fromJSONtoAlbum(JSONObject albumJSON) {
        String title = albumJSON.getString("title");
        String artist = albumJSON.getString("maniadb:albumartists");
        String release = albumJSON.getString("release");
        String image = albumJSON.getString("image");

        Album album = Album.builder().title(title).artist(artist).release(release).image(image).build();
        return album;
    }

    private String initialUpperCase(String s){
        return StringUtils.capitalize(s);
    }
}
