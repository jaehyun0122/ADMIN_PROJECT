package com.example.finalproject.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Test
    void encoding(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123").length());
    }

    @Test
    void passwordExpireCheck(){
        LocalDateTime now = LocalDateTime.now().withNano(0);
        LocalDateTime tmpDate = LocalDateTime.parse("2023-04-30T13:21:13");

        Duration duration = Duration.between(now, tmpDate);
        long diffDays = duration.toDays();

        System.out.println(diffDays);

    }
}