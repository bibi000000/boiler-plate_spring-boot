package com.wyaa.demo2.common.exception;

import com.wyaa.demo2.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private ErrorCode errorCode;
    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
