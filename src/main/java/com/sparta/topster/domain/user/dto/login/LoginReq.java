package com.sparta.topster.domain.user.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReq {

    private String username;
    private String password;

    @Builder
    public LoginReq(String username, String password){
        this.username = username;
        this.password = password;
    }

}
