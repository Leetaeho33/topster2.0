package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TopsterCreateRes {
    Long id;
    String title;
    String content;
    String author;
    LocalDateTime createdAt;
    List<AlbumRes> albums;

    @Builder
    public TopsterCreateRes(Long id, String title, String content, String author, LocalDateTime createdAt, List<AlbumRes> albums) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.albums = albums;
    }
}
