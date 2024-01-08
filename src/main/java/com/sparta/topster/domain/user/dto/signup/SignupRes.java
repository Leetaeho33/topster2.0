package com.sparta.topster.domain.user.dto.signup;

import com.sparta.topster.domain.user.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRes {

    private String username;
    private String nickname;
    private UserRoleEnum role;

    @Builder
    public SignupRes(String username, String nickname, UserRoleEnum role){
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }

}
