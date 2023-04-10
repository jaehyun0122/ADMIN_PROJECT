package com.example.pass.dto;

import lombok.*;

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
    private String signTargetTycd;
    private String signTarget;
    private String isUserAgreement;
    private String originalInfo;
    private String reqTxId;
    private String isDigitalSign;
    private String isCombineAuth;

    public void setOriginalInfo(String originalInfo) {
        this.originalInfo = originalInfo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSignTarget(String signTarget) {
        this.signTarget = signTarget;
    }
}
