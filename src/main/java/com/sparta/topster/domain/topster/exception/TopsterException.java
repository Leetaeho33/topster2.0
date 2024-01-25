package com.sparta.topster.domain.topster.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum TopsterException implements ErrorCode {
    NOT_EXIST_TOPSTER(HttpStatus.NOT_FOUND, "T1000", "탑스터가 존재하지 않습니다."),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST, "T1001", "해당 탑스터 작성자가 아닙니다."),
    NOT_FOUND_TOPSTER(HttpStatus.NOT_FOUND, "T1002", "해당 유저는 탑스터를 만들지 않았습니다.");

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
