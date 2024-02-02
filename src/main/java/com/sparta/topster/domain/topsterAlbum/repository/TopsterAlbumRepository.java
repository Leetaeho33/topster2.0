package com.sparta.topster.domain.topsterAlbum.repository;

import com.sparta.topster.domain.topsterAlbum.entity.TopsterAlbum;

import java.util.List;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = TopsterAlbum.class, idClass = Long.class)
public interface TopsterAlbumRepository {
    TopsterAlbum save(TopsterAlbum topster);

    List<TopsterAlbum> saveAll(Iterable<TopsterAlbum> topsterAlbums);
}
