package com.example.cok.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LoginFormDto {
    private String username;
    private String password;
}
