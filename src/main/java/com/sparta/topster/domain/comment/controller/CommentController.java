package com.sparta.topster.domain.comment.controller;

import com.sparta.topster.domain.comment.dto.req.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.req.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.res.CommentRes;
import com.sparta.topster.domain.comment.service.CommentService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentRes> createComment(@PathVariable Long postId,
                                                  @RequestBody CommentCreateReq commentCreateReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.createComment(postId, commentCreateReq, userDetails));
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<?> getComment(@PathVariable Long postId) {

    return ResponseEntity.ok().body(commentService.getComment(postId));

  }

  @PatchMapping("/comments/{commentId}")
  public ResponseEntity<RootNoDataRes> modifyComment(@PathVariable Long commentId,
                                                  @RequestBody CommentModifyReq commentModifyReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.modifyComment(commentId, commentModifyReq, userDetails));
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<RootNoDataRes> deleteComment(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.deleteComment(commentId, userDetails));
  }
}
