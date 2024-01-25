package com.sparta.topster.domain.post.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sparta.topster.domain.post.dto.request.PostCreateReq;
import com.sparta.topster.domain.post.dto.request.PostPageReq;
import com.sparta.topster.domain.post.dto.request.PostSearchCond;
import com.sparta.topster.domain.post.dto.request.PostSortReq;
import com.sparta.topster.domain.post.dto.request.PostUpdateReq;
import com.sparta.topster.domain.post.dto.response.PostDeatilRes;
import com.sparta.topster.domain.post.dto.response.PostListRes;
import com.sparta.topster.domain.post.service.PostService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 API")
public class PostController {

    private final PostService postService;

    @PostMapping("/topster/{topsterId}/posts")
    public ResponseEntity<RootNoDataRes> create(@Valid @RequestBody PostCreateReq req,
        @PathVariable Long topsterId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long postId = postService.save(req, topsterId, userDetails.getUser());
        RootNoDataRes res = RootNoDataRes.builder()
            .message(postId + "")
            .code("201")
            .build();
        return ResponseEntity.status(CREATED).body(res);
    }


    @PatchMapping("/posts/{id}")
    public ResponseEntity<RootNoDataRes> update(@Valid @RequestBody PostUpdateReq req,
        @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long postId = postService.update(req, id, userDetails.getUser().getId());
        RootNoDataRes res = RootNoDataRes.builder()
            .message(postId + "")
            .code("201")
            .build();
        return ResponseEntity.ok(res);
    }


    @DeleteMapping("/posts/{id}")
    public ResponseEntity<RootNoDataRes> delete(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long deleteId = postService.delete(id, userDetails.getUser().getId());
        RootNoDataRes res = RootNoDataRes.builder()
            .message(deleteId + "번 게시글 삭제 완료하였습니다.")
            .code("200")
            .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDeatilRes> getPostDetail(@PathVariable Long id) {
        PostDeatilRes res = postService.getPostDetail(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostListRes>> getPostList(PostSearchCond cond, PostPageReq pageReq,
        PostSortReq sortReq) {

        Page<PostListRes> res = postService.getPostList(cond, pageReq, sortReq);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/posts/{id}/isAuthor")
    public ResponseEntity<RootNoDataRes> isAuthor(@PathVariable Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.isAuthor(id, userDetails.getUser().getId());
        return ResponseEntity.ok(RootNoDataRes.builder()
            .code("200")
            .message("본인의 게시글이 맞습니다")
            .build());
    }

    @GetMapping("/posts/my")
    public ResponseEntity<Page<PostListRes>> getMyPosts(
        @AuthenticationPrincipal UserDetailsImpl userDetails, PostSearchCond cond,
        PostPageReq pageReq, PostSortReq sortReq) {

        Page<PostListRes> res = postService.getMyPosts
            (userDetails.getUser().getId(), cond, pageReq, sortReq);
        return ResponseEntity.ok(res);
    }

}
