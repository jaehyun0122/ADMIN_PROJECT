package com.example.apiserver.dto;

import lombok.*;

@Getter
@ToString
public class UserDto {
    private String userNm;
    private String phoneNo;
    private String gender;
    private String birthday;
    private String telcoTycd;
    private String ci;
}
