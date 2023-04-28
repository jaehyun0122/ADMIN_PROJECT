package com.example.finalproject.exeption;

import lombok.Getter;

/**
 * SQL 실행 실패 시 에러
 */
@Getter
public class SqlFailException extends RuntimeException{
    private ErrorMessageEnum errorMessageEnum;
    public SqlFailException(ErrorMessageEnum errorMessageEnum) {
        super();
        this.errorMessageEnum = errorMessageEnum;
    }
}
