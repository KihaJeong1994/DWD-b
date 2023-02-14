package com.dwd.dwdb.exception;

import com.dwd.dwdb.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException{
    private ErrorCode errorCode;
    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
