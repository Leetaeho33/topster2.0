package com.sparta.topster.domain.like.controller;

import com.sparta.topster.domain.like.dto.LikeCountStatusRes;
import com.sparta.topster.domain.like.service.LikeService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/topsters")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;


    @GetMapping("/{topsterId}/like-count/status")
    public ResponseEntity<LikeCountStatusRes> getLikeCountAndStatus(@PathVariable Long topsterId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails != null) {
            return ResponseEntity.ok().body(likeService.getLikeCountAndStatus(topsterId, userDetails.getUser().getId()));
        }
        return ResponseEntity.ok(likeService.getLikeCountAndStatus(topsterId, null));
    }
//    @PostMapping("/{topsterId}/like")
//    public ResponseEntity<?> toggleLike(@PathVariable Long topsterId,
//                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        try {
//            boolean liked = likeService.toggleLike(topsterId, userDetails);
//
//            if(liked) {
//                return ResponseEntity.ok().body(new RootNoDataRes("200", "좋아요"));
//            } else {
//                return ResponseEntity.ok().body(new RootNoDataRes("200", "좋아요 취소"));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new RootNoDataRes(e.getMessage(), "400"));
//        }
//    }
}
