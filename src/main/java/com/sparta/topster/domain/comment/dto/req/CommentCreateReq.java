package com.sparta.topster.domain.comment.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateReq {
    String content;
}
