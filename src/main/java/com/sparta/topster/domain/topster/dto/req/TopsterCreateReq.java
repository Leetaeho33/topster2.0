package com.sparta.topster.domain.topster.dto.req;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import lombok.Getter;

import java.util.List;

@Getter
public class TopsterCreateReq {
    String title;
    String content;
    List<AlbumInsertReq> albums;
}
