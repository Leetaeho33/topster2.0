package com.sparta.topster.domain.comment.controller;

import com.sparta.topster.domain.comment.dto.req.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.req.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.res.CommentRes;
import com.sparta.topster.domain.comment.service.CommentService;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "댓글 API")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentRes> createComment(@PathVariable Long postId,
                                                  @RequestBody CommentCreateReq commentCreateReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.createComment(postId, commentCreateReq, userDetails.getUser()));
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<?> getComment(@PathVariable Long postId) {

    return ResponseEntity.ok().body(commentService.getComments(postId));

  }

  @PatchMapping("/comments/{commentId}")
  public ResponseEntity<RootNoDataRes> modifyComment(@PathVariable Long commentId,
                                                  @RequestBody CommentModifyReq commentModifyReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.modifyComment(commentId, commentModifyReq, userDetails.getUser()));
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<RootNoDataRes> deleteComment(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.deleteComment(commentId, userDetails.getUser()));
  }
  @GetMapping("/comments/{commentId}/isAuthor")
  public ResponseEntity<RootNoDataRes> isAuthor(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ResponseEntity.ok().body(commentService.isAuthor(commentId, userDetails.getUser()));
  }
}
