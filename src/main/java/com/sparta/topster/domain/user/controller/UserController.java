package com.sparta.topster.domain.user.controller;

import com.sparta.topster.domain.user.dto.update.UpdateRes;
import com.sparta.topster.domain.user.dto.update.UpdateReq;
import com.sparta.topster.domain.user.service.UserService;
import com.sparta.topster.global.exception.ErrorCode;
import com.sparta.topster.global.exception.ServiceException;
import com.sparta.topster.global.response.RootResponseDto;
import com.sparta.topster.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid UpdateReq updateReq,
        BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if(!fieldErrors.isEmpty()){
            throw new ServiceException(ErrorCode.MODIFY_PROFILE_FAILED);
        }

        UpdateRes updateRes = userService.updateUser(userDetails.getUser(), updateReq);

        return ResponseEntity.ok().body(RootResponseDto.builder()
            .code("200")
            .message("성공적으로 수정되었습니다.")
            .data(updateRes)
            .build());
    }

}
