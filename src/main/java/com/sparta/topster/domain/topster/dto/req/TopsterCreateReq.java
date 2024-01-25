package com.sparta.topster.domain.topster.dto.req;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopsterCreateReq {
    String title;
    List<AlbumInsertReq> albums;

    @Builder
    public TopsterCreateReq(String title, String content, List<AlbumInsertReq> albums) {
        this.title = title;
        this.albums = albums;
    }
}
