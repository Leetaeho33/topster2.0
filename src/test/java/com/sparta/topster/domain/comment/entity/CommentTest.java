package com.sparta.topster.domain.comment.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class CommentTest {
  @Test
  public void testUpdateComment() {

    // Given
    String initialContent = "초기 댓글 내용";
    User mockUser = Mockito.mock(User.class);
    Post mockPost = Mockito.mock(Post.class);
    Comment comment = Comment.builder()
        .content(initialContent)
        .user(mockUser)
        .post(mockPost)
        .build();

    String updatedContent = "수정된 댓글 내용";

    // When
    comment.update(updatedContent);

    // Then
    assertEquals(updatedContent, comment.getContent());

  }
}