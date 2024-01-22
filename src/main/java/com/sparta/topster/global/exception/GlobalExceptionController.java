package com.sparta.topster.global.exception;

import com.sparta.topster.global.response.RootResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleException(ServiceException e) {
        ErrorCode code = e.getCode();
        RootResponseDto<Object> responseDto = RootResponseDto.builder()
            .code(code.getCode())
            .message(code.getMessage())
            .build();
        return ResponseEntity.status(code.getStatus()).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RootResponseDto> validationException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = new ArrayList<>();
        e.getAllErrors().forEach(error -> errors.add(ValidationError.builder()
            .field(((FieldError) error).getField())
            .message(error.getDefaultMessage())
            .build()));
        RootResponseDto response = RootResponseDto.builder()
            .code("G1000")
            .message("입력 조건이 맞지 않습니다.")
            .data(errors)
            .build();
        return ResponseEntity.badRequest().body(response);
    }

}
