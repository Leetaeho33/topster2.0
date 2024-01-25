package com.sparta.topster.domain.follow.dto;

import lombok.Builder;

@Builder
public record FollowingGetRes(
        Long userId,
        String nickname

) {

}
