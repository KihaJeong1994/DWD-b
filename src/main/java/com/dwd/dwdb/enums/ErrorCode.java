package com.dwd.dwdb.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USERID_ALREADY_EXISTS("4000","User id already exists")
    ,EMAIL_ALREADY_EXISTS("4001","Email already exists")
    ,JWT_TOKEN_EXPIRED("4002","Jwt token expired")
    ,INVALID_JWT_TOKEN("4003","Invalid JWT token")
    ,INVALID_JWT_SIGNATURE("4004","Invalid JWT signature")
    ,UNSUPPORTED_JWT_TOKEN("4005","Unsupported JWT token")
    ,INVALID_JWT_ARGUMENT("4006","JWT token compact of handler are invalid")
    ;


    private int status;
    private String code;
    private String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    ErrorCode(int status,String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
