package com.sparta.topster.domain.album.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlbumInsertReq {
    String title;
    String releaseDate;
    String artist;
    String image;
}
