package com.sparta.topster.domain.topster.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum TopsterException implements ErrorCode {
    NOT_EXIST_TOPSTER(HttpStatus.NOT_FOUND, "T1000", "탑스터가 존재하지 않습니다.");

    private final HttpStatus Status;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
