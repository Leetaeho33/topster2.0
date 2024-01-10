package com.sparta.topster.domain.album.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.dto.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.maniadb.service.ManiadbService;
import com.sparta.topster.domain.maniadb.service.ManiadbServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "AlbumService")
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ManiadbServiceImpl maniadbService;

    public String getRawArtistData(String query) throws JsonProcessingException {
        return maniadbService.getRawArtistData(query);
    }

    public List<AlbumRes> getAlbumsByArtist(String query) throws JsonProcessingException {
        JSONArray items =  maniadbService.getAlbumsJSONArray(query);
        List<Album> albumList = maniadbService.fromJSONArrayToAlbum(items, query);

        List<AlbumRes> albumResList = new ArrayList<>();
        for(Album album : albumList){
            albumRepository.save(album);
            AlbumRes albumRes = AlbumRes.builder().artist(album.getArtist()).title(album.getTitle())
                    .image(album.getImage()).release(album.getReleaseDate()).build();
            albumResList.add(albumRes);
        }
        return albumResList;
    }



}
