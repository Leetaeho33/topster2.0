package com.sparta.topster.domain.user.excepetion;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserException implements ErrorCode {
    SIGNUP_FAIL(HttpStatus.BAD_REQUEST, "1000", "회원가입에 실패했습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "1001", "중복된 USERNAME 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST,"1002","중복된 닉네임 입니다."),
    WRONG_ADMIN_CODE(HttpStatus.BAD_REQUEST, "1003", "관리자 암호가 틀려 등록이 불가능합니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1004", "로그인에 실패했습니다."),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, "1005", "토큰이 틀립니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "1006", "유저가 존재하지 않습니다."),
    MODIFY_PROFILE_FAILED(HttpStatus.BAD_REQUEST, "1007", "유저 정보 수정에 실패했습니다."),
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "1008", "관리자가 아닙니다."),
    NOT_AUTHORIZATION(HttpStatus.BAD_REQUEST, "1009", "인증되지 않은 사용자입니다."),
    NOT_FOUND_PASSWORD(HttpStatus.BAD_REQUEST,"1010","비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "1011", "중복된 사용자 이메일 입니다."),
    NOT_FOUND_AUTHENTICATION_CODE(HttpStatus.BAD_REQUEST,"1012","인증번호 오류"),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST,"1013","닉네임에는 공백이 허용되지 않습니다."),
    NOT_FOUND_USERID(HttpStatus.BAD_REQUEST,"1014","유저 ID를 찾을 수 없습니다.");

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
