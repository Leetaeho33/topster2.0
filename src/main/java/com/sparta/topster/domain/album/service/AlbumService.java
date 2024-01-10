package com.sparta.topster.domain.album.service;

import com.sparta.topster.domain.album.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "AlbumService")
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    public Object getRawArtistData(String query) {
        return null;
    }
}
