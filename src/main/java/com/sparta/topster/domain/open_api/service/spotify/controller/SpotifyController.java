package com.sparta.topster.domain.open_api.service.spotify.controller;

import com.sparta.topster.domain.open_api.service.spotify.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    @GetMapping("/get/access-token")
    public ResponseEntity<Object> getAccessToken(){
        return ResponseEntity.ok(spotifyService.getAccessToken());
    }

}
