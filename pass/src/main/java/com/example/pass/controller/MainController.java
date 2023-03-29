package com.example.pass.controller;

import com.example.pass.dao.PassDaoImpl;
import com.example.pass.dto.*;
import com.example.pass.key.RsaDecrypt;
import com.example.pass.service.ReqServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ReqServiceImpl reqService;
    private final PassDaoImpl passDao;
    private final String authUrl = "https://api-stg.passauth.co.kr/v1/certification/notice";
    private final String resultUrl = "https://api-stg.passauth.co.kr/certification/result";

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

    @GetMapping
    @ResponseBody
    public ReqDto info(UserDto userDto){
        return reqService.getReq(userDto);
    }

    @GetMapping("join")
    @ResponseBody
    public String join(ReqDto reqDto) throws JsonProcessingException {
        reqDto.setOriginalInfo(null);

        // 응답 결과 변환
        ObjectMapper om = new ObjectMapper();
        ResDto resDto = om.readValue(reqService.getRes(reqDto, "POST", authUrl).toString(), ResDto.class);

        Map<String, Object> map = new HashMap<>();
        map.put("ReqDto", reqDto);
        map.put("ResDto", resDto);

        passDao.insertAuth(map);

        return resDto.getCertTxId();
    }

    @GetMapping("result")
    public String result(@RequestParam String certTxId) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // 검증 결과 요청 파라미터
        ObjectMapper om = new ObjectMapper();
        // certTxId로 검증 결과 요청 파라미터 가져옴
        ResultReqDto resultReqDto = passDao.resultReq(certTxId);
        // 검증 결과 요청 파리미터에 대한 응답 DB에 저장
        ResultResDto resDto = om.readValue(reqService.getRes(resultReqDto, "POST", resultUrl).toString(), ResultResDto.class);
        passDao.insertAuthResult(resDto);

        // ci가 있다면 복호화
        if(resDto.getCi().length() > 0){
            RsaDecrypt rsaDecrypt = new RsaDecrypt(resDto.getCi());
            rsaDecrypt.ciDecryption();
        }

        return "result";
    }

    @GetMapping("authResult")
    @ResponseBody
    public List<ResultResDto> authResult(@RequestParam String certTxId){
        return passDao.authResult(certTxId);
    }
}
