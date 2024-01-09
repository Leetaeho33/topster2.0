package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.entity.Album;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ManiadbService {
    String getRawArtistData(String query) throws JsonProcessingException;
    String getAlbumByArtist(String query);

}
