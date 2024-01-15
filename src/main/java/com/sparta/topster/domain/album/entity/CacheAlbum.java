package com.sparta.topster.domain.album.entity;


import com.sparta.topster.domain.album.dto.res.AlbumRes;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "cacheAlbum", timeToLive = 60 * 30)
public class CacheAlbum {

    @Id
    private String artist;
    private List<AlbumRes> albums;

    @Builder
    public CacheAlbum(String artist, List<AlbumRes> albums) {
        this.artist = artist;
        this.albums = albums;
    }
}
