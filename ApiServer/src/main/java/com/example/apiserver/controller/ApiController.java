package com.example.apiserver.controller;

import com.example.apiserver.error.ErrorInfoEnum;
import com.example.apiserver.error.GlobalExceptionHandler;
import com.example.apiserver.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api-stg.passauth.co.kr")
public class ApiController {
    private final AuthServiceImpl authService;
    @PostMapping("v1/certification/notice")
    public void auth(HttpServletRequest request, HttpEntity<Map<String, String>> httpEntity){
        log.info("=== 인증 요청 ===");
        log.info("요청 헤더 {}", request.getHeader("Authorization"));
        log.info("요청 바디 {}", httpEntity.getBody());
        log.info("=== 인증 요청 ===");
        // header 검증
        if(!authService.headerCheck(request.getHeader("Authorization"))){
            new GlobalExceptionHandler().handleException(ErrorInfoEnum.NO_AUTH);
        }

        // body 누락 값 체크
        String leakBody = authService.authBodyCheck(httpEntity.getBody());
        if(!leakBody.equals("none")){
            new GlobalExceptionHandler().handleException(ErrorInfoEnum.LEAK_MANDATORY, leakBody);
        }

        // body 유효 값 체크



    }




}
