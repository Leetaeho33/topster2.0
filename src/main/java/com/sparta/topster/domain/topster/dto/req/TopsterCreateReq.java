package com.sparta.topster.domain.topster.dto.req;

import com.sparta.topster.domain.album.dto.req.AlbumInsertReq;
import com.sparta.topster.domain.album.entity.Album;
import lombok.Getter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
public class TopsterCreateReq {
    String title;
    String content;
    List<AlbumInsertReq> albumInsertReqList;
}
