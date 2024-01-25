package com.sparta.topster.global.response;


import lombok.Builder;

@Builder
public record RootNoDataRes(
    String code,
    String message
) {

}

