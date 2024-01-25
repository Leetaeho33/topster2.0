package com.sparta.topster.domain.user.dto.getUser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sparta.topster.domain.user.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserRes {

    private String username;
    private String nickname;
    private String email;
    private String intro;
    @JsonInclude(Include.NON_NULL)
    private UserRoleEnum roleEnum;

}
