package com.sparta.topster.domain.user.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRes {

    private String username;
    private String nickname;

    @Builder
    public LoginRes(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
