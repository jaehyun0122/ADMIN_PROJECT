package com.example.pass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResultReqDto {
    private String companyCd;
    private String reqTxId;
    private String certTxId;
    private String PhoneNo;
    private String userNm;
}
