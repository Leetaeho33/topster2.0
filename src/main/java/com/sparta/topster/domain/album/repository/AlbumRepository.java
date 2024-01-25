package com.sparta.topster.domain.album.repository;

import com.sparta.topster.domain.album.entity.Album;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Album.class, idClass = Long.class)
public interface AlbumRepository {
    Album save(Album album);
    Optional<Album> findById(Long albumId);
    Optional<Album> findByTitle(String albumTitle);
    Album findOrCreateByTitle(String albumTitle);

    List<Album> saveAll(Iterable<Album> albums);

    @Query("select a from Album a where a.title in (:titles)")
    List<Album> findAllByAlbumTitleList(Iterable<String> titles);
}
