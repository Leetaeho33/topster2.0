package com.sparta.topster.domain.like.controller;

import com.sparta.topster.domain.like.service.LikeService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topster")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{topster}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long topsterId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            boolean liked = likeService.toggleLike(topsterId, userDetails);

            if(liked) {
                return ResponseEntity.ok().body(new RootNoDataRes("200", "좋아요"));
            } else {
                return ResponseEntity.ok().body(new RootNoDataRes("200", "좋아요 취소"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RootNoDataRes(e.getMessage(), "400"));
        }
    }
}
