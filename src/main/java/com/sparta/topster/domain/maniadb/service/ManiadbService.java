package com.sparta.topster.domain.maniadb.service;

import com.sparta.topster.domain.album.entity.Album;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ManiadbService {
    String getRawArtistData(String query);
    String getAlbumByArtist(String query);

}
