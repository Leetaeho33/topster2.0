package com.sparta.topster.domain.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.sparta.topster.domain.comment.dto.req.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.req.CommentModifyReq;
import com.sparta.topster.domain.comment.dto.res.CommentRes;
import com.sparta.topster.domain.comment.entity.Comment;
import com.sparta.topster.domain.comment.repository.CommentRespository;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.post.service.PostService;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.response.RootNoDataRes;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class) // Mockito의 목 객체 자동으로 초기화
class CommentServiceTest {

  @InjectMocks
  private CommentService commentService;
  @Mock
  private CommentRespository commentRepository;
  @Mock
  private PostService postService;

  User user;
  User otherUser;
  Post post;

  @BeforeEach
  void init(){
    user = User.builder()
        .nickname("asdf")
        .username("hhhh")
        .build();
    otherUser = User.builder()
        .nickname("zxcv")
        .username("qqqq")
        .build();
    post = Post.builder()
        .user(user)
        .build();

    ReflectionTestUtils.setField(user, "id", 1L);
    ReflectionTestUtils.setField(otherUser, "id", 2L);
    ReflectionTestUtils.setField(post, "id", 1L);

  }

  @Test
  void 댓글_작성_성공_테스트() {
    // Given
    CommentCreateReq req = CommentCreateReq.builder()
        .content("댓글테스트")
        .build();

    given(postService.getPost(any())).willReturn(post);

    // When
    CommentRes result = commentService.createComment(post.getId(), req, user);

    // Then
    assertThat(result.getContent()).isEqualTo(req.getContent());
    assertThat(result.getAuthor()).isEqualTo(user.getNickname());
  }

  @Test
  void 수정을_할_때_댓글이_존재하지_않으면_예외를_반환한다() {
    //given
    CommentModifyReq req = CommentModifyReq.builder()
        .content("댓글 수정")
        .build();

    // When, Then
    assertThatThrownBy(() -> commentService.modifyComment(9999L, req, user)).isInstanceOf(
        ServiceException.class).hasMessage("댓글이 존재하지 않습니다.");
  }

  @Test
  void 수정을_할_때_작성자가_아니면_예외를_반환한다() {
    CommentModifyReq req = CommentModifyReq.builder()
        .content("댓글 수정")
        .build();

    Comment comment = Comment.builder()
        .content("댓글")
        .post(post)
        .user(user)
        .build();

    given(commentRepository.findById(any())).willReturn(Optional.of(comment));

    assertThatThrownBy(() -> commentService.modifyComment(9999L, req, otherUser)).isInstanceOf(
        ServiceException.class).hasMessage("작성자만 수정 및 삭제 할 수 있습니다.");
  }

  @Test
  void 수정_성공_테스트() {
    CommentModifyReq req = CommentModifyReq.builder()
        .content("댓글 수정")
        .build();

    Comment comment = Comment.builder()
        .content("댓글")
        .post(post)
        .user(user)
        .build();
    ReflectionTestUtils.setField(comment, "id", 1L);

    given(commentRepository.findById(any())).willReturn(Optional.of(comment));

    RootNoDataRes res = commentService.modifyComment(comment.getId(), req, user);

    assertThat(res.code()).isEqualTo("200");
    assertThat(res.message()).isEqualTo(comment.getId()+ "번 댓글을 수정하였습니다.");
  }

  @Test
  void 삭제_성공_테스트() {
    Comment comment = Comment.builder()
        .content("댓글")
        .post(post)
        .user(user)
        .build();
    ReflectionTestUtils.setField(comment, "id", 1L);

    given(commentRepository.findById(any())).willReturn(Optional.of(comment));

    RootNoDataRes res = commentService.deleteComment(comment.getId(), user);
    assertThat(res.code()).isEqualTo("200");
    assertThat(res.message()).isEqualTo(comment.getId()+ "번 댓글을 삭제하였습니다.");
  }
}