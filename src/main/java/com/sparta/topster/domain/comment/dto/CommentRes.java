package com.sparta.topster.domain.comment.dto;

import com.sparta.topster.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRes {

    Long id;
    String comment;
    String commentWriter;

    public CommentRes(Comment comment) {
        this.comment = comment.getComment();
//        this.commentWriter = comment.getBoard().getCommentWriter;
    }
}
