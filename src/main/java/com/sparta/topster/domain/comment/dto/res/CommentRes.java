package com.sparta.topster.domain.comment.dto.res;

import com.sparta.topster.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRes {

    Long id;
    String content;
    String author;
    LocalDateTime createdAt;

}
