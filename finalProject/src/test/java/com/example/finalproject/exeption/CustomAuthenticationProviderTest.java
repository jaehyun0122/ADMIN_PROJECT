package com.example.finalproject.exeption;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class CustomAuthenticationProviderTest {
    @Test
    void passwordMathTest(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        if (!passwordEncoder.matches("123", passwordEncoder.encode("123"))) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }else{
            System.out.println("matching");
        }
    }

}