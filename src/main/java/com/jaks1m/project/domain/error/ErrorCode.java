package com.jaks1m.project.domain.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND_USER (404,"U001","NOT FOUND USER"),
    NOT_EQUAL_PASSWORD(404,"U002","NOT EQUAL PASSWORD"),
    ALREADY_JOIN(404,"U002","ALREADY JOIN"),
    LOGOUT_USER(404,"U003","LOGOUT USER"),
    INCORRECT_PASSWORD(400,"U004","INCORRECT PASSWORD"),
    JWT_ACCESS_TOKEN_EXPIRED(401,"T001","ACCESS TOKEN EXPIRED"),
    JWT_REFRESH_TOKEN_EXPIRED(401,"T002","REFRESH TOKEN EXPIRED"),
    JWT_UNAUTHORIZED(401,"T003","UNAUTHORIZED TOKEN"),
    JWT_INVALID_SIGNATURE(401,"T004","INVALID SIGNATURE"),
    JWT_UNSUPPORTED(401,"T005","UNSUPPORTED TOKEN"),
    JWT_CLAIMS_EMPTY(401,"T006","CLAIMS_EMPTY"),
    JWT_BLACKLIST(401,"T007","TOKEN IS BLACKLIST"),
    EMAIL_TOKEN_NOT_FOUND(401,"T008","EMAIL TOKEN NOT FOUND"),
    NOT_FOUND_AUTHENTICATION(401,"S001","NOT FOUND AUTHENTICATION");


    ErrorCode(int status,String code,String message) {
        this.status=status;
        this.code=code;
        this.message=message;
    }

    private int status;
    private String code;
    private String message;
}
