package com.sparta.topster.domain.album.service;

import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.album.exception.AlbumException;
import com.sparta.topster.domain.album.repository.AlbumRepository;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.topster.domain.album.exception.AlbumException.NOT_EXIST_ALBUM;

@Service
@Slf4j(topic = "AlbumSerivce")
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
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

    public boolean isExistAlbum(String albumTitle){
        Optional<Album> optionalAlbum = albumRepository.findByTitle(albumTitle);
        return optionalAlbum.isPresent();
    }
}
