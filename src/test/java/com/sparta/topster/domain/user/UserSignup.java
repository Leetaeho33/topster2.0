package com.sparta.topster.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sparta.topster.domain.user.dto.signup.SignupReq;
import com.sparta.topster.domain.user.dto.signup.SignupRes;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.domain.user.repository.UserRepository;
import com.sparta.topster.domain.user.service.UserService;
import com.sparta.topster.global.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserSignup {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signupTest() {
        //given
        String username = "username";
        String password = "password";
        String email = "username@naver.com";
        String nickname = "nickname";
        String intro = "intro";

        SignupReq signupReq = SignupReq.builder()
            .username(username)
            .password(password)
            .email(email)
            .nickname(nickname)
            .intro(intro)
            .build();

        when(passwordEncoder.encode(password)).thenReturn(password);

        //when
        User user = User.builder().username(username).password(passwordEncoder.encode(password)).email(email)
            .nickname(nickname).intro(intro).build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        SignupRes saveUser = userService.signup(signupReq);
        //then
        assertEquals(saveUser.getUsername(),signupReq.getUsername());
    }
}
