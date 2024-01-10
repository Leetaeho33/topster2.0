package com.sparta.topster.domain.comment.dto.res;

import com.sparta.topster.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRes {

    Long id;
    String comment;
    String commentWriter;

    public CommentRes(Comment comment) {
        this.id = comment.getCommentId();
        this.comment = comment.getComment();
        this.commentWriter = comment.getUser().getUsername();
    }
}
