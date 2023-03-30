package com.example.pass.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userNm;
    private String birthday; // YYMMDD
    private String telcoTycd;
    private String phoneNo;
    private String gender;

}
