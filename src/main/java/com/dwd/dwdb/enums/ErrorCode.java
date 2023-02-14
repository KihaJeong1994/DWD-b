package com.dwd.dwdb.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USERID_ALREADY_EXISTS("4000","User id already exists"),
    EMAIL_ALREADY_EXISTS("4001","Email already exists");


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
