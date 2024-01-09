package com.sparta.topster.domain.song.repository;

import com.sparta.topster.domain.song.entity.Song;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Song.class, idClass = Long.class)
public interface SongRepository {
    Song save(Song song);
}
