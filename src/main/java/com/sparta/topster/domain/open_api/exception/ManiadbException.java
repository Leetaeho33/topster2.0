package com.sparta.topster.domain.open_api.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ManiadbException implements ErrorCode {
    NOT_SERCH_ALBUM(HttpStatus.NOT_FOUND, "M1000", "검색 가수의 앨범이 존재하지 않습니다.");

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
