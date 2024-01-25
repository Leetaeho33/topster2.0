package com.sparta.topster.domain.user.service.user;

import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_EMAIL;
import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_NICKNAME;
import static com.sparta.topster.domain.user.excepetion.UserException.DUPLICATE_USERNAME;
import static com.sparta.topster.domain.user.excepetion.UserException.INVALID_NICKNAME;
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
import com.sparta.topster.domain.user.excepetion.UserException;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    private final String ADMIN_TOKEN = "AAlrnYxKZ0aHgTBcXukeZygoC";

    @Override
    public SignupRes signUp(SignupReq signupReq) {
        String username = signupReq.getUsername();
        String nickname = signupReq.getNickname();
        String password = passwordEncoder.encode(signupReq.getPassword());
        String signupCode = redisUtil.getData(signupReq.getEmail());

        checkPassword(signupCode == null || !signupCode.equals(signupReq.getCertification()),
            NOT_FOUND_AUTHENTICATION_CODE);

        Optional<User> byUsername = userRepository.findByUsername(username);
        Optional<User> byNickname = userRepository.findBynickname(nickname);
        Optional<User> byEmail = userRepository.findByUserEmail(signupReq.getEmail());

        checkPassword(byUsername.isPresent(), DUPLICATE_USERNAME);

        checkPassword(byNickname.isPresent(), DUPLICATE_NICKNAME);

        checkPassword(byEmail.isPresent(), DUPLICATE_EMAIL);

        UserRoleEnum role = UserRoleEnum.USER;

        if (signupReq.isAdmin()) {
            checkPassword(!ADMIN_TOKEN.equals(signupReq.getAdminToken()), WRONG_ADMIN_CODE);
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

    @Override
    public LoginRes loginUser(LoginReq loginReq, HttpServletResponse response) {
        String username = loginReq.getUsername();
        Optional<User> byUsername = userRepository.findByUsername(username);

        checkPassword(
            !passwordEncoder.matches(loginReq.getPassword(), byUsername.get().getPassword()),
            NOT_FOUND_PASSWORD);

        UserRoleEnum role = byUsername.get().getRole();

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,
            jwtUtil.createToken(byUsername.get().getUsername(), role));
        response.setHeader(JwtUtil.REFRESH_TOKEN_PREFIX,
            jwtUtil.createRefreshToken(byUsername.get().getUsername()));

        return LoginRes.builder().username(byUsername.get().getUsername()).build();
    }

    @Override
    @Transactional
    public UpdateRes updateUser(User user, UpdateReq updateReq) {
        User findByUser = findByUser(user.getId());

        checkPassword(updateReq, findByUser);

        checkPassword(updateReq);

        checkIntro(findByUser.getNickname().equals(updateReq.getNickname()), DUPLICATE_NICKNAME);

        if (checkIntro(updateReq, findByUser)) {
            return UpdateRes.builder()
                .intro(updateReq.getIntro())
                .build();
        }

        if (checkNickname(updateReq, findByUser)) {
            return UpdateRes.builder()
                .nickname(updateReq.getNickname())
                .build();
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

    @Override
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

    @Override
    @Transactional
    public String refreshToken(String refreshToken) {
        String username = redisUtil.getData(refreshToken);

        if (refreshToken != null) {
            Optional<User> byUsername = userRepository.findByUsername(username);
            return jwtUtil.createToken(username, byUsername.get().getRole());
        } else {
            throw new ServiceException(TOKEN_ERROR);
        }
    }

    private User findByUser(Long userId) {
        return userRepository.findById(userId);
    }

    private void checkPassword(boolean passwordEncoder, UserException notFoundPassword) {
        if (passwordEncoder) {
            log.error(notFoundPassword.getMessage());
            throw new ServiceException(notFoundPassword);
        }
    }

    private static boolean checkNickname(UpdateReq updateReq, User findByUser) {
        if (updateReq.getNickname() != null && updateReq.getIntro() == null) {
            findByUser.updateNickname(updateReq.getNickname());
            return true;
        }
        return false;
    }

    private static boolean checkIntro(UpdateReq updateReq, User findByUser) {
        if (updateReq.getIntro() != null && updateReq.getNickname() == null) {
            findByUser.updateIntro(updateReq.getIntro());
            return true;
        }
        return false;
    }

    private static void checkIntro(boolean findByUser, UserException duplicateNickname) {
        if (findByUser) {
            throw new ServiceException(duplicateNickname);
        }
    }

    private static void checkPassword(UpdateReq updateReq) {
        checkIntro(updateReq.getNickname() != null && updateReq.getNickname().trim().isEmpty(),
            INVALID_NICKNAME);
    }

    private void checkPassword(UpdateReq updateReq, User findByUser) {
        checkIntro(!passwordEncoder.matches(updateReq.getPassword(), findByUser.getPassword()),
            NOT_FOUND_PASSWORD);
    }
}
