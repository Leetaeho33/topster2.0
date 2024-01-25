package com.sparta.topster.domain.post.dto.request;

import lombok.Builder;

@Builder
public record PostSearchCond(
    String key,
    String query
) {

}
