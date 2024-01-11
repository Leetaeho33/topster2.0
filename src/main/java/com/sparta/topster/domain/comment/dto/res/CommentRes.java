package com.sparta.topster.domain.comment.dto.res;

import com.sparta.topster.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRes {

    Long id;
    String content;
    String commentWriter;

    public CommentRes(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commentWriter = comment.getUser().getUsername();
    }
}
