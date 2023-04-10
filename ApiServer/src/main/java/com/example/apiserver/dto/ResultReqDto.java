package com.example.apiserver.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResultReqDto {
    private String companyCd;
    private String reqTxId;
    private String certTxId;
    private String userNm;
    private String phoneNo;
}
