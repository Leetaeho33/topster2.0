package com.sparta.topster.domain.post.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sparta.topster.domain.post.dto.request.PostCreateReq;
import com.sparta.topster.domain.post.dto.request.PostUpdateReq;
import com.sparta.topster.domain.post.service.PostService;
import com.sparta.topster.global.response.RootResponseDto;
import com.sparta.topster.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/topster/{topsterId}/posts")
    public ResponseEntity<?> create(@Valid @RequestBody PostCreateReq req,
        @PathVariable Long topsterId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long postId = postService.save(req, topsterId, userDetails.getUser());
        RootResponseDto<Object> res = RootResponseDto.builder()
            .message(postId + "번 게시글 생성 완료하였습니다.")
            .code("201")
            .build();
        return ResponseEntity.status(CREATED).body(res);
    }

    
    @PatchMapping("/posts/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody PostUpdateReq req,
        @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long postId = postService.update(req, id, userDetails.getUser().getId());
        RootResponseDto<Object> res = RootResponseDto.builder()
            .message(postId + "번 게시글 수정 완료하였습니다.")
            .code("200")
            .build();
        return ResponseEntity.ok(res);
    }

}
