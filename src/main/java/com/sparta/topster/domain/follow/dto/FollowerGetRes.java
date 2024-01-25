package com.sparta.topster.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record FollowerGetRes(
        Long userId,
    String nickname

) {

}
