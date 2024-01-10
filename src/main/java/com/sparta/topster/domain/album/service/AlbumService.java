package com.sparta.topster.domain.album.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.maniadb.service.ManiadbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "AlbumService")
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ManiadbService maniadbService;

    public Object getRawArtistData(String query) throws JsonProcessingException {
        return maniadbService.getRawArtistData(query);
    }
}
