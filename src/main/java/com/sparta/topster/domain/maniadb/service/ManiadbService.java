package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.song.entity.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface ManiadbService {
    String getRawArtistData(String query) throws JsonProcessingException;
    JSONArray getAlbumsJSONArray(String query) throws JsonProcessingException;
    List<Album>  fromJSONArrayToAlbum(JSONArray items, String query);
    List<Song> fromJSONToSong(JSONObject item, Album album);
    Album fromJSONtoAlbum(JSONObject albumJSON);
}
