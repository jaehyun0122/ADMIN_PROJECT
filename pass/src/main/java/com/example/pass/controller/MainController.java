package com.example.pass.controller;

import com.example.pass.dto.*;
import com.example.pass.service.ReqServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {
    private final ReqServiceImpl reqService;
    @Value("${url.authUrl}")
    private String authUrl;
    @Value("${url.resultUrl}")
    private String resultUrl;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("showData")
    public String showdata(){
        return "result";
    }

    @GetMapping("moveJoin")
    public String join(){
        return "join";
    }

    @PostMapping
    @ResponseBody
    public ReqDto info(UserDto userDto){
       log.info("요청 유저 정보 {}", userDto);
        return reqService.getReq(userDto);
    }

    @PostMapping("join")
    @ResponseBody
    public String join(ReqDto reqDto) throws Exception {
        reqDto.setOriginalInfo(null);

        log.info(">>> 인증 요청 정보 {}", reqDto);
        // 응답 결과 변환
        ResDto resDto = reqService.getResDto(reqDto, "POST", authUrl);
        log.info("인증 요청 응답 결과 {}", resDto);
        // insert
        reqService.insertAuth(reqDto, resDto);
        log.info("<<< 인증 요청");
        return resDto.getCertTxId();
    }

    @PostMapping("again")
    @ResponseBody
    public String againReq(@RequestParam String certTxId) throws Exception {
        log.info(">>> 다시 요청");
        ReqDto reqDto = reqService.againReq(certTxId);
        reqDto.setOriginalInfo(null);

        // userNm, phoneNo, gender, birthday 복호화 해서 전달
        reqDto = reqService.decryptInfo(reqDto);
        log.info("다시 요청 정보 {}", reqDto);
        ResDto resDto = reqService.getResDto(reqDto, "POST", authUrl);
        // insert
        reqService.insertAuth(reqDto, resDto);
        log.info("<<< 다시 요청");
        return resDto.getCertTxId();
    }

    @PostMapping("result")
    @ResponseBody
    public ResultResDto result(@RequestParam String certTxId) throws Exception {
        log.info(">>> 검증 결과 요청");
        log.info("검증 결과 요청 후 db 저장");
        // 검증 결과 요청 파라미터
        // certTxId로 검증 결과 요청 파라미터 가져옴
        ResultReqDto resultReqDto = reqService.resultReq(certTxId);

        // 검증 결과 요청 파리미터에 대한 응답 DB에 저장
        ResultResDto resDto = reqService.getResultResDto(resultReqDto, "POST", resultUrl);

        log.info("검증 결과 요청 파라미터 {}", resultReqDto);
        // 검증 결과 요청 파리미터에 대한 응답 DB에 저장
        log.info("검증 결과 응답 데이터 {}", resDto);
        reqService.insertAuthResult(resDto);

        log.info("<<< 검증 결과 요청");
        return resDto;
    }

    @GetMapping("authResult")
    @ResponseBody
    public List<ResultResDto> authResult(@RequestParam String certTxId) throws Exception {
        log.info("검증 결과 db에 요청");
        List<ResultResDto> authResult = reqService.authResult(certTxId);
        UserDto userInfo = reqService.setUserInfo(reqService.getUserInfo(certTxId));
        // ci 복호화, 성별 0~9 => 남( 홀수 ) or 여( 짝수 )
        authResult = reqService.convertResult(authResult, userInfo, certTxId);

        return authResult;
    }


}
