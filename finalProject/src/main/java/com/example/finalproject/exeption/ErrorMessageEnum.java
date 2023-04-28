package com.example.finalproject.exeption;

import lombok.Getter;

/**
 * 에러 메시지 관리 Enum
 */
@Getter
public enum ErrorMessageEnum {

    IMAGE_SIZE_EXCEED("이미지 파일은 168*168 또는 192*600(이하) 만 가능합니다"),
    SQL_ERROR("서버에 에러가 있습니다.");
    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
