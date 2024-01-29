package com.sparta.topster.domain.comment.dto.res;

import com.sparta.topster.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRes {

    Long id;
    String content;
    String author;
    LocalDateTime createdAt;

    public CommentRes(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
    }
}
