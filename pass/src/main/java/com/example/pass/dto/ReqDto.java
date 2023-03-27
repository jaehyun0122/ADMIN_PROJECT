package com.example.pass.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReqDto {
    private String companyCd;
    private String channelTycd;
    private String channelNm;
    private String agencyCd;
    private String serviceTycd;
    private String telcoTycd;
    private String phoneNo;
    private String userNm;
    private String birthday;
    private String gender;
    private String reqTitle;
    private String reqContent;
    private String reqCSPhoneNo;
    private String reqEndDttm;
    private String isNotification;
    private String isPASSVerify;
    private String verifyURL;
    private String signTargetTycd;
    private String signTarget;
    private String isUserAgreement;
    private String originalInfo;
    private String reqTxId;
    private String isDgitalSign;
    private String isCombineAuth;
}
