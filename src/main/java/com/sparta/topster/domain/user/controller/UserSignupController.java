package com.sparta.topster.domain.user.controller;


import static com.sparta.topster.global.exception.ErrorCode.SIGNUP_FAIL;

import com.sparta.topster.domain.user.dto.signup.SignupReq;
import com.sparta.topster.domain.user.dto.signup.SignupRes;
import com.sparta.topster.domain.user.service.UserService;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.response.RootResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserSignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<RootResponseDto> signup(@Valid @RequestBody SignupReq signupReq,
        BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            throw new ServiceException(SIGNUP_FAIL);
        }

        SignupRes signupRes = userService.signup(signupReq);

        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("회원가입 성공")
            .data(signupRes)
            .build());
    }



}
