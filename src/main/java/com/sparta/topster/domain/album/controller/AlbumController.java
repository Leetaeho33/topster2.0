package com.sparta.topster.domain.album.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.album.service.AlbumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
@Tag(name = "앨범 API")
public class AlbumController {

    private final AlbumService albumService;
    @GetMapping("/raw-data")
    public ResponseEntity<Object> getRawData(@RequestParam("artistName") String query) throws JsonProcessingException {
        return ResponseEntity.ok(albumService.getRawArtistData(query));
    }


    @GetMapping
    public ResponseEntity<Object> getAlbums(@RequestParam("artistName") String query) throws JsonProcessingException {
        return ResponseEntity.ok(albumService.getAlbumsByArtist(query));
    }
}
