package com.sparta.topster.domain.comment.service;

import com.sparta.topster.domain.comment.dto.req.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.req.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.res.CommentRes;
import com.sparta.topster.domain.comment.entity.Comment;
import com.sparta.topster.domain.comment.exception.CommentException;
import com.sparta.topster.domain.comment.repository.CommentRespository;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.post.repository.PostRepository;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

  private final CommentRespository commentRespository;
  private final PostRepository postRepository;

  public CommentRes createComment(Long postId, CommentCreateReq commentCreateReq, UserDetailsImpl userDetails) {
    log.info("댓글 작성");
    Post post = postRepository.findById(postId).orElseThrow(() ->
        new ServiceException(CommentException.NO_COMMENT));

    Comment comment = new Comment(commentCreateReq, post, userDetails);
    commentRespository.save(comment);
    log.info("댓글 작성 완료");
    return new CommentRes(comment);
  }

  public List<CommentRes> getComment(Long postId) {
    log.info("해당 게시글에 대한 댓글 조회");
    List<Comment> findCommentList = commentRespository.findByPostId(postId);
    List<CommentRes> postCommentList = new ArrayList<>();

    for (Comment comment : findCommentList) {
      if(comment.getPost().getId().equals(postId)) {
        postCommentList.add(new CommentRes(comment));
      }
    }
        return postCommentList;
  }

  public CommentRes modifyComment(Long commentId, CommentModifyReq commentModifyReq, UserDetailsImpl userDetails) {
    log.info("댓글 수정");
    Comment comment = modifyAndDeleteCommentAuthor(commentId, userDetails);

    comment.save(commentModifyReq);

    log.info("댓글 수정 완료");
    commentRespository.save(comment);

    return new CommentRes(comment);
  }

  public CommentRes deleteComment(Long commentId, UserDetailsImpl userDetails) {
    log.info("댓글 삭제");
    Comment comment = modifyAndDeleteCommentAuthor(commentId, userDetails);

    log.info("댓글 삭제 완료");
    commentRespository.delete(comment);

    return new CommentRes(comment);
  }


  public Comment modifyAndDeleteCommentAuthor(Long commentId, UserDetailsImpl userDetails) {

    Comment comment = commentRespository.findById(commentId);

    if(comment == null) {
      throw new ServiceException(CommentException.NO_COMMENT);  // 댓글이 존재하지 않습니다.
    }

    if(!comment.getUser().getUsername().equals(userDetails.getUsername())) {
      throw new ServiceException(CommentException.MODIFY_AND_DELETE_ONLY_AUTHOR); // 작성자만 수정 및 삭제 할 수 있습니다.
    }

    return comment;
  }
}
