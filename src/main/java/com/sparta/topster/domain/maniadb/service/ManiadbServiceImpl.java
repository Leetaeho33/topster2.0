package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j(topic = "ManiaServiceImpl")
@RequiredArgsConstructor
public class ManiadbServiceImpl implements  ManiadbService{

    private final RestTemplate restTemplate;

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
        String result = objectMapper.writeValueAsString(json);
        return result;
    }

    @Override
    public String getAlbumByArtist(String query) {
        return null;
    }
}
