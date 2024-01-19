package com.sparta.topster.domain.user.controller;

import static com.sparta.topster.domain.user.excepetion.UserException.MODIFY_PROFILE_FAILED;

import com.sparta.topster.domain.user.dto.getUser.GetUserRes;
import com.sparta.topster.domain.user.dto.login.LoginReq;
import com.sparta.topster.domain.user.dto.update.UpdateReq;
import com.sparta.topster.domain.user.dto.update.UpdateRes;
import com.sparta.topster.domain.user.service.UserService;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.security.UserDetailsImpl;
import com.sparta.topster.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginReq loginReq, HttpServletResponse response){
        return ResponseEntity.ok(userService.loginUser(loginReq,response));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid UpdateReq updateReq,
        BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            throw new ServiceException(MODIFY_PROFILE_FAILED);
        }

        UpdateRes updateRes = userService.updateUser(userDetails.getUser(), updateReq);

        return ResponseEntity.ok(updateRes);
    }

    @GetMapping
    public ResponseEntity<?> getUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        GetUserRes user = userService.getUser(userDetails.getUser());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Map<String, String> map) {
        userService.deleteUser(userDetails.getUser(), map.get("password"));

        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader Map<String, String> map) {
        try {
            String newAccessToken = userService.refreshToken(map.get("refreshToken"));
            return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER, newAccessToken).build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getCode());
        }
    }
}
