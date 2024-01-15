package com.sparta.topster.domain.album.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class AlbumRes {
    Long id;
    String title;
    String artist;
    String releaseDate;
    String image;


    @Builder
    public AlbumRes(Long id, String title, String artist, String releaseDate, String image) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.image = image;
    }
}
