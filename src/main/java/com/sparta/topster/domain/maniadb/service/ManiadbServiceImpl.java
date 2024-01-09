package com.sparta.topster.domain.maniadb.service;

import com.sparta.topster.domain.album.entity.Album;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j(topic = "ManiaServiceImpl")
@RequiredArgsConstructor
public class ManiadbServiceImpl implements  ManiadbService{

    private final RestTemplate restTemplate;

    @Override
    public String getRawArtistData(String query) {
        return null;
    }

    @Override
    public String getAlbumByArtist(String query) {
        return null;
    }
}
