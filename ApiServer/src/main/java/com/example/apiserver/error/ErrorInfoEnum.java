package com.example.apiserver.error;

import lombok.Getter;

@Getter
public enum ErrorInfoEnum {
    NO_AUTH(9001, "권한이 없습니다.", "error1", 401),
    NO_BODY(9001, "요청 Body가 없습니다.", "error1", 400),
    UNSURPPORT_HTTP(9003, "지원하지 않는 HTTP Method입니다.", "error1",400),
    LEAK_MANDATORY(3101, "필수항목", "error1", 400)
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
