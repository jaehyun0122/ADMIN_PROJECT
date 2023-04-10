package com.example.apiserver.controller;

import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.dto.AuthResInfoDto;
import com.example.apiserver.dto.ResultReqDto;
import com.example.apiserver.dto.ResultResDto;
import com.example.apiserver.service.AuthService;
import com.example.apiserver.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api-stg.passauth.co.kr")
public class ApiController {
    private final AuthService authService;
    private final ResultService resultService;
    @PostMapping("v1/certification/notice")
    @ResponseBody
    public ResponseEntity<AuthResInfoDto> auth(HttpServletRequest request, @RequestBody AuthReqInfoDto httpEntity) throws Exception {
        log.info(">>> 인증 요청");
        log.info("요청 헤더 {}", request.getHeader("Authorization"));
        log.info("요청 바디 {}", httpEntity);
        // header 검증 & null check
        String key = authService.headerCheck(request.getHeader("Authorization"));
        // body 누락 값 & null 체크
        authService.authBodyCheck(httpEntity);
        // body 유효 값 체크
        AuthReqInfoDto decryptAuthReqInfo = authService.bodyValidCheck(httpEntity, key);

        // regTxId, certTxId 만들어서 보내주기
        // certTxId는 랜덤 값으로, sql, null 9099 에러는 서비스 시스템 에러
        AuthResInfoDto authResponse = authService.getAuthResponse(decryptAuthReqInfo);

        log.info("인증 결과 {}", authResponse);
        log.info("<<< 인증 요청");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/certification/result")
    public ResponseEntity<ResultResDto> result(HttpServletRequest request,@RequestBody ResultReqDto httpEntity) throws Exception {
        log.info(">>> 결과 요청");
        log.info("결과 요청 헤더 {}", request.getHeader("Authorization"));
        log.info("결과 요청 바디 {}", httpEntity);
        // header  & null 검증
        String key = authService.headerCheck(request.getHeader("Authorization"));

        // body, companyCd, reqTxId, certTxId, phoneNo, userNm
        resultService.resultBodyCheck(httpEntity);

        ResultResDto resultResDto = resultService.resultBodyValidCheck(httpEntity, key);
        log.info("결과 요청 응답 데이터 {}", resultResDto);

        log.info("<<< 걸과 요청");
        return new ResponseEntity<>(resultResDto, HttpStatus.OK);
    }
}
