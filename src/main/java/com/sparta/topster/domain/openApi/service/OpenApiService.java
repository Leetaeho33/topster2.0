package com.sparta.topster.domain.openApi.service;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;

import java.util.List;

public interface OpenApiService {
    String getRawArtistData(String query);
    List<AlbumRes> getAlbums(String query);
}
