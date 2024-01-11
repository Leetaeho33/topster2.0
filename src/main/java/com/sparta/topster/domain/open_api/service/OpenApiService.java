package com.sparta.topster.domain.open_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.song.entity.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface OpenApiService {
    String getRawArtistData(String query);
    List<Album> getAlbums(String query);
}
