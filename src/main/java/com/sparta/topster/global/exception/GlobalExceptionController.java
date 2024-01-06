package com.sparta.topster.global.exception;

import com.sparta.topster.global.response.RootResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleException(ServiceException e){
        ErrorCode code = e.getCode();
        RootResponseDto<Object> responseDto = RootResponseDto.builder()
            .code(code.getCode())
            .message(code.getMessage())
            .build();
        return ResponseEntity.status(code.getStatus()).body(responseDto);
    }


}
