package com.sparta.topster.domain.user.service;

import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_EMAIL;
import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_NICKNAME;
import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_USERNAME;
import static com.sparta.topster.domain.user.excepetion.UserException.NOT_FOUND_AUTHENTICATION_CODE;
import static com.sparta.topster.domain.user.excepetion.UserException.NOT_FOUND_PASSWORD;
import static com.sparta.topster.domain.user.excepetion.UserException.TOKEN_ERROR;
import static com.sparta.topster.domain.user.excepetion.UserException.WRONG_ADMIN_CODE;

import com.sparta.topster.domain.user.dto.deleteDto.DeleteReq;
import com.sparta.topster.domain.user.dto.getUser.GetUserRes;
import com.sparta.topster.domain.user.dto.login.LoginReq;
import com.sparta.topster.domain.user.dto.login.LoginRes;
import com.sparta.topster.domain.user.dto.signup.SignupReq;
import com.sparta.topster.domain.user.dto.signup.SignupRes;
import com.sparta.topster.domain.user.dto.update.UpdateReq;
import com.sparta.topster.domain.user.dto.update.UpdateRes;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.domain.user.entity.UserRoleEnum;
import com.sparta.topster.domain.user.repository.UserRepository;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.util.JwtUtil;
import com.sparta.topster.global.util.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    private final String ADMIN_TOKEN = "AAlrnYxKZ0aHgTBcXukeZygoC";

    public SignupRes signup(SignupReq signupReq) {
        String username = signupReq.getUsername();
        String nickname = signupReq.getNickname();
        String password = passwordEncoder.encode(signupReq.getPassword());
        String signupCode = redisUtil.getData(signupReq.getEmail());

        if (signupCode == null || !signupCode.equals(signupReq.getCertification())) {
            log.error(NOT_FOUND_AUTHENTICATION_CODE.getMessage());
            throw new ServiceException(NOT_FOUND_AUTHENTICATION_CODE);
        }

        Optional<User> byUsername = userRepository.findByUsername(username);
        Optional<User> byNickname = userRepository.findBynickname(nickname);
        Optional<User> byEmail = userRepository.findByUserEmail(signupReq.getEmail());

        if (byUsername.isPresent()) {
            log.error(DUPLICATE_USERNAME.getMessage());
            throw new ServiceException(DUPLICATE_USERNAME);
        }

        if (byNickname.isPresent()) {
            log.error(DUPLICATE_NICKNAME.getMessage());
            throw new ServiceException(DUPLICATE_NICKNAME);
        }

        if (byEmail.isPresent()) {
            log.error(DUPLICATE_EMAIL.getMessage());
            throw new ServiceException(DUPLICATE_EMAIL);
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (signupReq.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signupReq.getAdminToken())) {
                log.error(WRONG_ADMIN_CODE.getMessage());
                throw new ServiceException(WRONG_ADMIN_CODE);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
            .username(username)
            .nickname(nickname)
            .password(password)
            .email(signupReq.getEmail())
            .intro(signupReq.getIntro())
            .role(role)
            .build();

        User saveUser = userRepository.save(user);

        return SignupRes.builder()
            .username(saveUser.getUsername())
            .nickname(saveUser.getNickname())
            .role(saveUser.getRole())
            .build();
    }

    public LoginRes loginUser(LoginReq loginReq, HttpServletResponse response) {
        String username = loginReq.getUsername();
        Optional<User> byUsername = userRepository.findByUsername(username);

        if (passwordEncoder.matches(loginReq.getPassword(), byUsername.get().getPassword())) {
            UserRoleEnum role = byUsername.get().getRole();

            response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(byUsername.get().getUsername(), role));
            response.setHeader(JwtUtil.REFRESH_TOKEN_PREFIX, jwtUtil.createRefreshToken(byUsername.get().getUsername()));

            return LoginRes.builder().username(byUsername.get().getUsername()).build();
        }else{
            log.error(NOT_FOUND_PASSWORD.getMessage());
            throw new ServiceException(NOT_FOUND_PASSWORD);
        }
    }

    @Transactional
    public UpdateRes updateUser(User user, UpdateReq updateReq) {
        User findByUser = findByUser(user.getId());

        if (!passwordEncoder.matches(updateReq.getPassword(), findByUser.getPassword())) {
            throw new ServiceException(NOT_FOUND_PASSWORD);
        }

        if (findByUser.getNickname().equals(updateReq.getNickname())) {
            throw new ServiceException(DUPLICATE_NICKNAME);
        }

        findByUser.updateIntro(updateReq);

        return UpdateRes.builder()
            .nickname(updateReq.getNickname())
            .intro(updateReq.getIntro())
            .build();
    }

    public GetUserRes getUser(User user) {
        findByUser(user.getId());

        return GetUserRes.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .intro(user.getIntro())
            .build();
    }

    @Transactional
    public void deleteUser(User user, DeleteReq deleteReq) {
        String password = deleteReq.getPassword();
        User getUser = findByUser(user.getId());
        if (passwordEncoder.matches(password, getUser.getPassword())) {
            userRepository.deleteById(getUser.getId());
        } else {
            throw new ServiceException(NOT_FOUND_PASSWORD);
        }
    }

    @Transactional
    public String refreshToken(String refreshToken) {
        String username = redisUtil.getData(refreshToken);

        if(refreshToken != null){
            Optional<User> byUsername = userRepository.findByUsername(username);
            return jwtUtil.createToken(username,byUsername.get().getRole());
        }else{
            throw new ServiceException(TOKEN_ERROR);
        }
    }

    private User findByUser(Long userId) {
        return userRepository.findById(userId);
    }
}
