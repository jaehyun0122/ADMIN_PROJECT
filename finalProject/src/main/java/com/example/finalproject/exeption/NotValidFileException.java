package com.example.finalproject.exeption;

import lombok.Getter;

@Getter
public class NotValidFileException extends RuntimeException{
    private ErrorMessageEnum errorMessageEnum;

    public NotValidFileException(ErrorMessageEnum errorMessageEnum) {
        this.errorMessageEnum = errorMessageEnum;
    }
}
