package com.sparta.topster.global.exception;

import lombok.Builder;

@Builder
public record ValidationError(
    String field,
    String message
) {

}
