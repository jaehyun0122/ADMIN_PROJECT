package com.example.apiserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResDto {
    private String reqTxId;
    private String resultTycd;
    private String digitalSign;
    @JsonProperty("CI")
    private String ci;
    private String telcoTxId;
    private String certTxId;
    private String resultDttm;
    private String userNm;
    private String birthday;
    private String gender;
    private String phoneNo;
    private String telcoTycd;

    public void setResultTycd(String resultTycd) {
        this.resultTycd = resultTycd;
    }

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setTelcoTxId(String telcoTxId) {
        this.telcoTxId = telcoTxId;
    }

    public void setResultDttm(String resultDttm) {
        this.resultDttm = resultDttm;
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
}
