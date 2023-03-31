package com.example.pass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResultResDto {
    private String reqTxId;
    private String telcoTxId;
    private String certTxId;
    private String resultTycd;
    private String resultDttm;
    private String digitalSign;
    @JsonProperty("CI")
    private String ci;
    private String userNm;
    private String birthday;
    private String gender;
    private String phoneNo;
    private String telcoTycd;
    private String decryptCi;

    public void setDecryptCi(String decryptCi) {
        this.decryptCi = decryptCi;
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

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setTelcoTycd(String telcoTycd) {
        this.telcoTycd = telcoTycd;
    }
}
