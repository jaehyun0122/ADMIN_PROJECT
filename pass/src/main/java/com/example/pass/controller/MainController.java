package com.example.pass.controller;

import com.example.pass.dao.PassDaoImpl;
import com.example.pass.dto.*;
import com.example.pass.key.RsaDecrypt;
import com.example.pass.service.ReqServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
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
import java.util.stream.Collectors;

@Controller
@Log4j2
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
       log.info("요청 유저 정보 {}", userDto);
        return reqService.getReq(userDto);
    }

    @GetMapping("join")
    @ResponseBody
    public String join(ReqDto reqDto) throws JsonProcessingException {
        reqDto.setOriginalInfo(null);
        log.info("cerTxId, reqTxId 요청 정보 {}", reqDto);
        // 응답 결과 변환
        ObjectMapper om = new ObjectMapper();
        ResDto resDto = om.readValue(reqService.getRes(reqDto, "POST", authUrl).toString(), ResDto.class);

        Map<String, Object> map = new HashMap<>();
        map.put("ReqDto", reqDto);
        map.put("ResDto", resDto); // certTxId, reqTxId

        passDao.insertAuth(map);

        return resDto.getCertTxId();
    }

    @GetMapping("result")
    @ResponseBody
    public ResultResDto result(@RequestParam String certTxId) throws IOException, ParseException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        log.info("검증 결과 요청 후 db 저장");
        ObjectMapper om = new ObjectMapper();
        // 검증 결과 요청 파라미터
        // certTxId로 검증 결과 요청 파라미터 가져옴
        ResultReqDto resultReqDto = passDao.resultReq(certTxId);
        log.info("검증 결과 요청 파라미터 {}", resultReqDto);
        // 검증 결과 요청 파리미터에 대한 응답 DB에 저장
        ResultResDto resDto = om.readValue(reqService.getRes(resultReqDto, "POST", resultUrl).toString(), ResultResDto.class);
        log.info("검증 결과 응답 데이터 {}", resDto);
        passDao.insertAuthResult(resDto);

        return resDto;
    }

    @GetMapping("authResult")
    @ResponseBody
    public List<ResultResDto> authResult(@RequestParam String certTxId) throws Exception {
        log.info("검증 결과 db에 요청");
        List<ResultResDto> authResult = passDao.authResult(certTxId);
        // ci 복호하, 성별 0~9 => 남 or 여
        UserDto userInfo = setUserInfo(passDao.getUserInfo(certTxId));

        for(ResultResDto res : authResult){
            res = setResult(res, userInfo);
            if("1".equals(res.getResultTycd()) && certTxId.equals(res.getCertTxId())){
                RsaDecrypt rsaDecrypt = new RsaDecrypt(res.getCi());
                res.setDecryptCi(rsaDecrypt.ciDecryption());
            }
            if(Integer.parseInt(res.getGender()) % 2 == 0){
                res.setGender("여");
            }else res.setGender("남");
        }


        return authResult;
    }


    private UserDto setUserInfo(UserDto userInfo) throws Exception {
        userInfo.setUserNm(reqService.deAes(userInfo.getUserNm()));
        userInfo.setGender(reqService.deAes(userInfo.getGender()));
        userInfo.setBirthday(reqService.deAes(userInfo.getBirthday()));
        userInfo.setPhoneNo(reqService.deAes(userInfo.getPhoneNo()));
        return userInfo;
    }

    private ResultResDto setResult(ResultResDto res, UserDto user){
        res.setGender(user.getGender());
        res.setBirthday(user.getBirthday());
        res.setUserNm(user.getUserNm());
        res.setPhoneNo(user.getPhoneNo());
        return res;
    }
}
