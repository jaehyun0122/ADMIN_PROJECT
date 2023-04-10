package com.example.apiserver.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
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

        if("필수항목".equals(errorInfoEnum.getErrorMessage())){
            this.errorMessage = "필수항목 " + leakBody + "이 누락되었습니다.";
        }else if("요청항목".equals(errorInfoEnum.getErrorMessage())){
            this.errorMessage = leakBody + " 값이 유효하지 않습니다.";
        }
    }

}
