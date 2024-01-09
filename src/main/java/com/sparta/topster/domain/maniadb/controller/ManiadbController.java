package com.sparta.topster.domain.maniadb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.topster.domain.maniadb.service.ManiadbService;
import com.sparta.topster.global.response.RootResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/maniadb")
public class ManiadbController {

    private final ManiadbService maniadbService;

    // RootResponse로 응답받으면 데이터가 너무 지저분해서 그냥 바로 받는걸로 할게요!
    // 정제된 데이터는 rootResponse로 응답하겠습니다.
    @GetMapping("/artist")
    public ResponseEntity<Object> getRawData(@RequestParam("artistName") String query) throws JsonProcessingException {
        return ResponseEntity.ok(maniadbService.getRawArtistData(query));
    }
}
