package com.example.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidInfoDto {
    private String companyCd;
    private String reqTxId;
    private String serviceTycd;
    private String telcoTycd;
    private String phoneNo;
    private String userNm;
    private String birthday;
    private String gender;
}
