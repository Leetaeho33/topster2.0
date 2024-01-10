package com.sparta.topster.domain.album.dto.res;

import com.sparta.topster.domain.song.entity.Song;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AlbumRes {
    String title;
    String artist;
    String release;
    String image;


    @Builder
    public AlbumRes(String title, String artist, String release, String image) {
        this.title = title;
        this.artist = artist;
        this.release = release;
        this.image = image;
    }
}
