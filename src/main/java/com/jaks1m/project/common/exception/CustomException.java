package com.jaks1m.project.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        this.errorCode=errorCode;
    }
}
