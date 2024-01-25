package com.sparta.topster.domain.follow.controller;

import com.sparta.topster.domain.follow.service.FollowService;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> toggleFollow
            (@PathVariable Long userId,
             @AuthenticationPrincipal UserDetailsImpl userDetails){
        followService.toggleFollow(userId, userDetails.getUser());
        return ResponseEntity.ok(200);
    }

    @GetMapping("/follower")
    public ResponseEntity<?> getFollower(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(followService.getMyFollower(userDetails.getUser()));
    }


    @GetMapping("/following")
    public ResponseEntity<?> getFollowing(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(followService.getMyFollowing(userDetails.getUser()));
    }


}
