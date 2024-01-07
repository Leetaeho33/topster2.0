package com.sparta.topster.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // user (1000)
    SIGNUP_FAIL(HttpStatus.BAD_REQUEST, "1000", "회원가입에 실패했습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "1001", "중복된 사용자 이름입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST,"1002","중복된 닉네임입니다."),
    WRONG_ADMIN_CODE(HttpStatus.BAD_REQUEST, "1003", "관리자 암호가 틀려 등록이 불가능합니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1004", "로그인에 실패했습니다."),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, "1005", "토큰이 틀립니다."),
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "1006", "유저가 존재하지 않습니다."),
    MODIFY_PROFILE_FAILED(HttpStatus.BAD_REQUEST, "1007", "유저 정보 수정에 실패했습니다."),
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "1008", "관리자가 아닙니다."),
    NOT_AUTHORIZATION(HttpStatus.BAD_REQUEST, "1009", "인증되지 않은 사용자입니다."),
    NOT_FOUND_PASSWORD(HttpStatus.BAD_REQUEST,"1010","비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "1011", "중복된 사용자 이름입니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
