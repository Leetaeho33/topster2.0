package com.sparta.topster.domain.post.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostException implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "P1001", "해당 게시글이 존재하지 않습니다."),
    AccessDeniedError(HttpStatus.FORBIDDEN, "P1002", "해당 게시글의 작성자가 아닙니다.");

    private final HttpStatus Status;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public HttpStatus getStatus() {
        return this.Status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
