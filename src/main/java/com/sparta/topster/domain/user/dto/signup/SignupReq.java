package com.sparta.topster.domain.user.dto.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupReq {

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Size(max = 20)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 30, message = "30자 이내 간단한 소개")
    private String intro;

    @Builder.Default
    private String adminToken = "";

    @Builder.Default
    boolean admin = false;

}
