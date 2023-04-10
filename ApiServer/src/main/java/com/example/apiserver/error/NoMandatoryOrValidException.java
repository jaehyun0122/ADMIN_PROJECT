package com.example.apiserver.error;

import lombok.Getter;

@Getter
public class NoMandatoryOrValidException extends RuntimeException{

    private ErrorInfoEnum errorInfoEnum;
    private String content;
    public NoMandatoryOrValidException(ErrorInfoEnum errorInfoEnum, String content){
        super();
        this.content = content;
        this.errorInfoEnum = errorInfoEnum;
    }
}
