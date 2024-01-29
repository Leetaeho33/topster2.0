package com.sparta.topster.domain.follow.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowException implements ErrorCode {

    CANT_FOLLOW_MYSELF(HttpStatus.BAD_REQUEST, "F1000", "자기 자신을 팔로우 할 수 없습니다."),
    NOT_FOUND_FOLLOWER(HttpStatus.NOT_FOUND, "F1001", "팔로워가 없어요 ㅠ."),
    NOT_FOUND_FOLLOWING(HttpStatus.NOT_FOUND, "F1002", "팔로잉이 없어요.");

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


