package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.dto.res.AlbumRes;

import java.util.List;

public interface ManiadbService {
    String getRawArtistData(String query) throws JsonProcessingException;
    List<AlbumRes> getAlbumsByArtist(String query) throws JsonProcessingException;

}
