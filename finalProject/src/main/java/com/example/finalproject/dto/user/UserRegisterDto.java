package com.example.finalproject.dto.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private String roles;
}
