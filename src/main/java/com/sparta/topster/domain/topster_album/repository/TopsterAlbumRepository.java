package com.sparta.topster.domain.topster_album.repository;

import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = TopsterAlbum.class, idClass = Long.class)
public interface TopsterAlbumRepository {
    TopsterAlbum save(TopsterAlbum topster);
}
