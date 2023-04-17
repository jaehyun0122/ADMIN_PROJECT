package com.example.cok.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {
    @Test
    void bycTest(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoder = passwordEncoder.encode("user");

    }

}