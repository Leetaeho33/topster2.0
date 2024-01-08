package com.sparta.topster.global.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

    private final ErrorCode code;

    public ServiceException(ErrorCode errorCode){
        this.code = errorCode;
    }

}
