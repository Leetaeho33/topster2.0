package com.sparta.topster.domain.album.dto;

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
    List<String> songs = new ArrayList<>();
}
