package com.sparta.topster.domain.topster.controller;

import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.global.response.RootResponseDto;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/topsters")
@Controller
@RequiredArgsConstructor
public class TopsterController {
    private final TopsterService topsterService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TopsterCreateReq topsterCreateReq,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(topsterService.createTopster(topsterCreateReq, userDetails.getUser()));
    }

    @GetMapping("/{topsterId}")
    public ResponseEntity<Object> getTopstesr(@PathVariable Long topsterId){
        return ResponseEntity.ok(topsterService.getTopster(topsterId));
    }

}
