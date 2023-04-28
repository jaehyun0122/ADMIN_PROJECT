package com.example.finalproject.dto.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String userName;
    private String email;
    private String phoneNo;
    private String password;
}
