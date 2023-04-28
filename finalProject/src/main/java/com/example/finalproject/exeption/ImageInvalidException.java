package com.example.finalproject.exeption;

import lombok.Getter;

@Getter
public class ImageInvalidException extends RuntimeException{
    private ErrorMessageEnum errorMessageEnum;
    public ImageInvalidException(ErrorMessageEnum errorMessageEnum) {
        super();
        this.errorMessageEnum = errorMessageEnum;
    }
}
