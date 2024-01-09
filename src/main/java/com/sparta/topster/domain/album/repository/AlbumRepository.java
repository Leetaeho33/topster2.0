package com.sparta.topster.domain.album.repository;

import com.sparta.topster.domain.album.entity.Album;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Album.class, idClass = Long.class)
public interface AlbumRepository {
    Album save(Album album);
    Optional<Album> findById(Long albumId);

}
