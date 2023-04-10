package com.example.apiserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResInfoDto {
    private String certTxId;
    private String reqTxId;
    private String telcoTxId;

    public void setTelcoTxId(String telcoTxId) {
        this.telcoTxId = telcoTxId;
    }
}
