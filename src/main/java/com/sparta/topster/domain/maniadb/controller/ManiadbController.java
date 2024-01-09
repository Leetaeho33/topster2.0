package com.sparta.topster.domain.maniadb.controller;

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


}
