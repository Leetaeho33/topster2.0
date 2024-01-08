package com.sparta.topster.domain.comment.service;

import com.sparta.topster.domain.comment.dto.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.CommentRes;
import com.sparta.topster.domain.comment.entity.Comment;
import com.sparta.topster.domain.comment.repository.CommentRespository;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRespository commentRespository;
//  private final PostRepository postRepository;

  public void createComment(Long postId, CommentCreateReq commentCreateReq, UserDetailsImpl userDetails) {
//    Post post = postRepository.findById();
    String comment = commentCreateReq.getComment();

//    if(post.getUsername().equals(userDetails.getUser().getUsername())){
        commentRespository.save(comment);
//    }

  }

  public List<CommentRes> getComment(Long postId) {
//    Post post = postRepository.findById(postId);
    List<Comment> comments = commentRespository.findAll();
    List<CommentRes> commentResList = new ArrayList<>();

    for (Comment comment : comments) {
      commentResList.add(new CommentRes(comment));
    }
//    if(post.getPostId().equals(comment.getCommentId())) {
        return commentResList;
//    }
  }

  public void modifyComment(Long commentId, CommentModifyReq commentModifyReq, UserDetailsImpl userDetails) {
    Comment comment = commentRespository.findById(commentId);

    if(comment.getUser().getUsername().equals(userDetails.getUsername())) {
      comment.save(commentModifyReq);
    }
    commentRespository.save(comment);
  }

  public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
    Comment comment = commentRespository.findById(commentId);

    if(comment.getUser().getUsername().equals(userDetails.getUsername())) {
      commentRespository.delete(comment);
    }
  }
}
