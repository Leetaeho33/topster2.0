package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopsterCreateRes {
    String title;
    String content;
    List<AlbumRes> albums;

    @Builder
    public TopsterCreateRes(String title, String content, List<AlbumRes> albumResList) {
        this.title = title;
        this.content = content;
        this.albums = albumResList;
    }
}
