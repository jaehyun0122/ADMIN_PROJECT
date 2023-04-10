package com.example.apiserver.error;

import lombok.Getter;

@Getter
public enum ErrorInfoEnum {
    NO_AUTH(9000, "권한이 없습니다.", "PACPM", 401),
    NO_BODY(9001, "요청 Body가 없습니다.", "PACPM", 400),
    WRONG_BODY(9002, "요청 Body 형식이 잘못되었습니다", "PACPM", 400),
    NOT_SUPPORT_HTTP_METHOD(9003,"지원하지 않는 HTTP Method입니다.", "PACPR", 400),
    SYSTEM_ERROR(9099, "일시적인 오류가 발생했습니다. 잠시 후 다시 요청해 주세요", "PACPM", 500),
    LEAK_MANDATORY(3101, "필수항목", "PACPM", 400),
    NOT_VALID(3102, "요청항목", "PACPM", 400),
    NO_USER(3103, "존재하지 않는 사용자입니다.", "PACPM", 400),
    RESULT_LEAK_MANDATORY(4101, "필수항목", "PACPM", 400),
    RESULT_NOT_VALID(4102, "요청항목", "PACPM", 400),
    NOT_VALID_TIMEOUT(4103, "인증 요청 유효기간이 만료되었거나, 일치하는 데이터가 없습니다. ", "PACPM", 400),
    ;
    
    private int errorCd;
    private String errorMessage;
    private String errorPointCd;
    private int httpStatus;
    ErrorInfoEnum(int errorCd, String errorMessage, String errorPointCd, int httpStatus){
        this.errorCd = errorCd;
        this.errorMessage = errorMessage;
        this.errorPointCd = errorPointCd;
        this.httpStatus = httpStatus;
    }

}
