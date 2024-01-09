package com.sparta.topster.domain.album.repository;

import com.sparta.topster.domain.album.entity.Album;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Album.class, idClass = Long.class)
public interface AlbumRepository {
}
