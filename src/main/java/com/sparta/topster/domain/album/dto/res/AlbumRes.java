package com.sparta.topster.domain.album.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumRes {
    String title;
    String artist;
    String releaseDate;
    String image;


    @Builder
    public AlbumRes(String title, String artist, String release, String image) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = release;
        this.image = image;
    }
}
