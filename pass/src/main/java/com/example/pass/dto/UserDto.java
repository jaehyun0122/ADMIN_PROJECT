package com.example.pass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {
    private String name;
    private String birthday; // YYMMDD
    private String telcoTycd;
    private String phone;
    private String gender;

}
