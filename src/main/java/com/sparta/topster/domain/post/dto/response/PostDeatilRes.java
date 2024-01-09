package com.sparta.topster.domain.post.dto.response;

import lombok.Builder;

@Builder
public record PostDeatilRes(
    Long id,
    Long topsterId,
    String title,
    String content,
    String nickname,
    String createdAt
) {

}
