package com.sparta.topster.domain.album.repository;

import com.sparta.topster.domain.album.entity.CacheAlbum;
import org.springframework.data.repository.CrudRepository;

public interface CacheAlbumRepository extends CrudRepository<CacheAlbum, String> {

}
