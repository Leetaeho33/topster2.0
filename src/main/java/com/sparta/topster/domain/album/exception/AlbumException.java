package com.sparta.topster.domain.album.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlbumException implements ErrorCode {
    NOT_EXIST_ALBUM(HttpStatus.NOT_FOUND, "A1000", "앨범이 존재하지 않습니다.");

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
