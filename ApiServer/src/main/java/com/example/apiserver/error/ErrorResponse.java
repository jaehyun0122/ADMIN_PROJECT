package com.example.apiserver.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private int errorCd;
    private String errorMessage;
    private String errorPointCd;
    private String telcoTxId;
    private String reqTxId;
    private String certTxId;

    public ErrorResponse(ErrorInfoEnum errorInfoEnum){
        this.errorCd = errorInfoEnum.getErrorCd();
        this.errorMessage = errorInfoEnum.getErrorMessage();
        this.errorPointCd = errorInfoEnum.getErrorPointCd();
    }

    public ErrorResponse(ErrorInfoEnum errorInfoEnum, String leakBody){
        this.errorCd = errorInfoEnum.getErrorCd();
        this.errorPointCd = errorInfoEnum.getErrorPointCd();

        if("필수항목".equals(errorMessage)){
            this.errorMessage = "필수항목 " + leakBody + "이 누락되었습니다.";
        }else{
            this.errorMessage = leakBody + " 값이 유효하지 않습니다.";
        }
    }

}
