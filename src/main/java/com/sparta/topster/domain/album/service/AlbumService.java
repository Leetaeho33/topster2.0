package com.sparta.topster.domain.album.service;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.entity.CacheAlbum;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.album.repository.CacheAlbumRepository;
import com.sparta.topster.domain.openApi.service.OpenApiService;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CacheAlbumRepository cacheAlbumRepository;

    public String getRawArtistData(String query){
        return openApiService.getRawArtistData(query);
    }


    public List<AlbumRes> getAlbumsByArtist(String query){

        Optional<CacheAlbum> cacheAlbum = cacheAlbumRepository.findById(query);

        if (cacheAlbum.isPresent()) {
            log.info("캐싱된 자료 사용");
            return cacheAlbum.get().getAlbums();
        }

        List<AlbumRes> albumResList = openApiService.getAlbums(query);

        CacheAlbum saveCache = CacheAlbum.builder()
            .artist(query)
            .albums(albumResList)
            .build();
        cacheAlbumRepository.save(saveCache);
        log.info("캐싱이 안 된 자료 사용 후 캐싱");
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
