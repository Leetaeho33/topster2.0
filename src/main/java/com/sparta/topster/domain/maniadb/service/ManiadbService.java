package com.sparta.topster.domain.maniadb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;

public interface ManiadbService {
    String getRawArtistData(String query) throws JsonProcessingException;
    JSONArray getAlbumsJSONArray(String query) throws JsonProcessingException;

}
