package com.sparta.topster.domain.user.controller;


import static com.sparta.topster.domain.user.excepetion.UserException.SIGNUP_FAIL;

import com.sparta.topster.domain.user.dto.login.LoginReq;
import com.sparta.topster.domain.user.dto.signup.SignupReq;
import com.sparta.topster.domain.user.service.user.UserServiceImpl;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.response.RootNoDataRes;
import com.sparta.topster.global.response.RootResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
@Tag(name = "유저 Auth API")
public class UserAuthController {

    private final UserServiceImpl userService;
    // UserAuthController

    @PostMapping("/signup")
    public ResponseEntity<RootResponseDto> signup(@Valid @RequestBody SignupReq signupReq,
        BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            throw new ServiceException(SIGNUP_FAIL);
        }

        userService.signUp(signupReq);

        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("회원가입 성공")
            .build());
    }

    @PostMapping("/login")
    public ResponseEntity<RootNoDataRes> loginUser(@Valid @RequestBody LoginReq loginReq, HttpServletResponse response){
        userService.loginUser(loginReq,response);
        return ResponseEntity.ok().body(RootNoDataRes.builder()
            .code("200")
            .message("로그인 성공")
            .build());
    }



}
