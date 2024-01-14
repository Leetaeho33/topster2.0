package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TopsterCreateLoginRes {
    Long id;
    String title;
    String content;
    String author;
    Long likeCount;
    Boolean likeStatus;
    LocalDateTime createdAt;
    List<AlbumRes> albums;

    @Builder
    public TopsterCreateLoginRes(Long id, String title, String content, String author, LocalDateTime createdAt, List<AlbumRes> albums) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.albums = albums;
        this.likeCount =0L;
        this.likeStatus = false;
    }
}
