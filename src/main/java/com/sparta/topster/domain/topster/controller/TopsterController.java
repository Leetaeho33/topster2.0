package com.sparta.topster.domain.topster.controller;

import com.sparta.topster.domain.topster.dto.req.TopsterCreateReq;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.response.RootResponseDto;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@Controller
@RequiredArgsConstructor
public class TopsterController {
    private final TopsterService topsterService;

    @PostMapping("/topsters")
    public ResponseEntity<Object> create(@RequestBody TopsterCreateReq topsterCreateReq,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(topsterService.createTopster(topsterCreateReq, userDetails.getUser()));
    }


    @GetMapping("/topsters/{topsterId}")
    public ResponseEntity<Object> getTopster(@PathVariable Long topsterId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(topsterService.getTopsterService(topsterId, userDetails.getUser()));
    }


    @GetMapping("/users/{userId}/topsters")
    public ResponseEntity<Object> getTopsterByUser(@PathVariable Long userId){
        return ResponseEntity.ok(topsterService.getTopsterByUserService(userId));
    }


    @GetMapping("/topsters/my")
    public ResponseEntity<?> getMyTopster(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(topsterService.getTopsterByUserService(userDetails.getUser()
                .getId()));
    }


    @GetMapping("/topsters/top-three")
    public ResponseEntity<?> getTopThreeTopster(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(topsterService.getTopsterTopThree(userDetails.getUser()));
    }


    @DeleteMapping("/topsters/{topsterId}")
    public ResponseEntity<Object> deleteTopstesr(@PathVariable Long topsterId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        topsterService.deleteTopster(topsterId, userDetails.getUser());
        return ResponseEntity.ok(RootNoDataRes.builder().
                code(HttpStatus.OK.toString()).
                message("탑스터가 정상적으로 삭제 되었습니다.").build());
    }


    @PostMapping("/topsters/{topsterId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long topsterId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(topsterService.toggleTopsterLike(topsterId, userDetails.getUser()));
    }


}
