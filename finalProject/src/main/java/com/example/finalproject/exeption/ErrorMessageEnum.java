package com.example.finalproject.exeption;

import lombok.Getter;

/**
 * 에러 메시지 관리 Enum
 */
@Getter
public enum ErrorMessageEnum {

    IMAGE_SIZE_EXCEED("이미지 파일은 168*168 또는 192*600(이하) 만 가능합니다"),
    SQL_ERROR("서버에 에러가 있습니다."),
    PDF_NOT_VALID("PDF 파일 형식을 확인해 주세요."),
    IMAGE_NOT_VALID("IMAGE 파일 형식을 확인해 주세요.");
    private String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }
}
