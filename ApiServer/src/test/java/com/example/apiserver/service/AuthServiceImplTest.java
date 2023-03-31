package com.example.apiserver.service;

import com.example.apiserver.error.ErrorInfoEnum;
import com.example.apiserver.error.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AuthServiceImplTest {
    private final AuthServiceImpl authService = new AuthServiceImpl();
    @Test
    void madatoryBodyCheck(){
        Map<String, String> body = new HashMap<>();
        body.put("companyCd", "123");
        body.put("serviceTycd", "S1001");
        body.put("signTargetTycd", "2");

        String result = authService.authBodyCheck(body);

        System.out.println(result);
    }

    @Test
    void leakMandatoryBodyCheck(){
        Map<String, String> body = new HashMap<>();
        body.put("companyCd", "123");
        body.put("serviceTycd", "S1001");
        body.put("signTargetTycd", "2");
        // body 누락 값 체크
        String leakBody = authService.authBodyCheck(body);
        if(!leakBody.equals("none")){
            new GlobalExceptionHandler().handleException(ErrorInfoEnum.LEAK_MANDATORY, leakBody);
        }
    }
}