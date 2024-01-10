package com.sparta.topster.domain.post.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostDeatilRes(
    Long id,
    Long topsterId,
    String title,
    String content,
    String nickname,
    LocalDateTime createdAt
) {

}
