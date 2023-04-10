package com.example.apiserver.error;

import lombok.Getter;

@Getter
public class NoDataException extends RuntimeException{

    private ErrorInfoEnum errorInfoEnum;
    public NoDataException(ErrorInfoEnum errorInfoEnum){
        super();
        this.errorInfoEnum = errorInfoEnum;
    }
}
