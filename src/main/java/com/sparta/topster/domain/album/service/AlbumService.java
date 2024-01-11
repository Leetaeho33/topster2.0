package com.sparta.topster.domain.album.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.open_api.service.OpenApiService;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.topster.domain.album.exception.AlbumException.NOT_EXIST_ALBUM;

@Slf4j(topic = "AlbumService")
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final OpenApiService openApiService;

    public String getRawArtistData(String query){
        return openApiService.getRawArtistData(query);
    }


    public List<AlbumRes> getAlbumsByArtist(String query){
        List<Album> albumList = openApiService.getAlbums(query);

        List<AlbumRes> albumResList = new ArrayList<>();
        for(Album album : albumList){
            AlbumRes albumRes = AlbumRes.builder().id(album.getId()).artist(album.getArtist()).title(album.getTitle())
                    .image(album.getImage()).release(album.getReleaseDate()).build();
            albumResList.add(albumRes);
        }
        return albumResList;
    }


    public Album getAlbum(Long albumId){
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if(optionalAlbum.isPresent()){
            return optionalAlbum.get();
        }else {
            log.error(NOT_EXIST_ALBUM.getMessage());
            throw new ServiceException(NOT_EXIST_ALBUM);
        }
    }


    public Album getAlbumByTitleOrCreate(String albumTitle,
                                         String albumImage,
                                         String albumArtist,
                                         String albumReleaseDate){

        Album album = albumRepository.findByTitle(albumTitle)
                .orElseGet(() -> albumRepository.save(Album.builder().
                        title(albumTitle).
                        image(albumImage).
                        release(albumReleaseDate).
                        artist(albumArtist).
                        build()));
        return album;
    }
}
