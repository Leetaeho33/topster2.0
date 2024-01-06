package com.sparta.topster.domain.user.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRes {

    private String username;
    private String nickname;

    public LoginRes(String username) {
    }
}
