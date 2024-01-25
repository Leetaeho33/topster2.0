package com.sparta.topster.domain.post.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostUpdateReq(
    @Size(min = 3, max = 50, message = "제목은 3 ~ 50자 입니다.") String title,
    @Size(min = 10, max = 500, message = "내용은 10 ~ 500자 입니다.") String content
) {

}
