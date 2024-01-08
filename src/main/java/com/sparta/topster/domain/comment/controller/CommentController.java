package com.sparta.topster.domain.comment.controller;

import com.sparta.topster.domain.comment.dto.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.CommentRes;
import com.sparta.topster.domain.comment.entity.Comment;
import com.sparta.topster.domain.comment.service.CommentService;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.List;
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
  @PostMapping("/post/{postId}/comments")
  public ResponseEntity<CommentRes> createComment(@PathVariable Long postId,
                                                  @RequestBody CommentCreateReq commentCreateReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

    commentService.createComment(postId, commentCreateReq, userDetails);
    return null;
  }

  @GetMapping("/post/{postId}/comments")
  public List<CommentRes> getComment(@PathVariable Long postId) {
    return commentService.getComment(postId);

  }

  @PatchMapping("/comment/{commentId}")
  public ResponseEntity<CommentRes> modifyComment(@PathVariable Long commentId,
                                                  @RequestBody CommentModifyReq commentModifyReq,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentService.modifyComment(commentId, commentModifyReq, userDetails);
    return null;
  }

  @DeleteMapping("/comment/{commentId}")
  public ResponseEntity<CommentRes> deleteComment(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentService.deleteComment(commentId, userDetails);
    return null;
  }
}
