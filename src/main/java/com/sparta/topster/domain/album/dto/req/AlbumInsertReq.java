package com.sparta.topster.domain.album.dto.req;

import lombok.Getter;

@Getter
public class AlbumInsertReq {
    String title;
    String releaseDate;
    String artist;
    String image;
}
