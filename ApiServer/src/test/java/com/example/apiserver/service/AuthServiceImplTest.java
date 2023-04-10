package com.example.apiserver.service;

import com.example.apiserver.dao.CompanyDao;
import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.error.ErrorInfoEnum;
import com.example.apiserver.error.NoMandatoryOrValidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    CompanyDao companyDao;

    @Test
    void reqDate(){
        String reqDttm = "2023-04-04 18:24:56";

        // 현재 날짜와 시간을 LocalDateTime 객체로 생성
        LocalDateTime now = LocalDateTime.now();

        // 날짜와 시간을 지정된 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endTime = LocalDateTime.parse(reqDttm, formatter);

        Assertions.assertThat(now.isAfter(endTime)).isTrue();
    }

    @Test
    void companyDaoTest(){
        Assertions.assertThat(companyDao.getCompanyCd("90001")).isTrue();
        Assertions.assertThat(companyDao.getCompanyCd("1231")).isFalse();
    }

    @Test
    void isValid() throws JsonProcessingException {

        String body = "{\"companyCd\":\"10000\",\"reqTxId\":\"53263584386764822652\",\"channelTycd\":\"PW\",\"channelNm\":\"채널 이름\",\"agencyCd\":\"11\",\"serviceTycd\":\"S2001\",\"telcoTycd\":\"L\",\"phoneNo\":\"YVE9cu5bkdJCmsmT/FUT2Q==\",\"userNm\":\"sQVFORiwW6XZo4FPMdnnCw==\",\"birthday\":\"fOo4Ud5ZvT4HpbIOvQ9DdA==\",\"gender\":\"RTp2MqvPjWG4f2QNWpHpJA==\",\"reqTitle\":\"reqTitle\",\"reqContent\":\"요청 content\",\"reqCSPhoneNo\":\"02333225\",\"reqEndDttm\":\"2023-05-04 10:34:59\",\"isNotification\":\"Y\",\"isPASSVerify\":\"Y\",\"signTargetTycd\":\"1\",\"signTarget\":\"wsb91rsQ4jmS4pzD0FDRDQ==\",\"isUserAgreement\":\"N\",\"originalInfo\":null,\"isDigitalSign\":\"Y\",\"isCombineAuth\":\"N\"}";
        ObjectMapper om = new ObjectMapper();
        AuthReqInfoDto authReqInfoDto = om.readValue(body, AuthReqInfoDto.class);

        // ⇒ companyCd 5 M ⇒ DB에서 이용기관 코드 확인

        // ⇒ reqTxId 20 M ⇒ 반드시 20자리 & 특수문자 불가
        reqTxIdCheck(authReqInfoDto.getReqTxId());
        // ⇒ serviceTycd 5 M ⇒ S1001 , S1002, S1003,  S2001 , S3001 ,S3002
        serviceTycdCheck(authReqInfoDto.getServiceTycd());
        // ⇒ telcoTycd 1 M ⇒ S, K, L && 1 자리
        telcoTycdCheck(authReqInfoDto.getTelcoTycd());
        // ⇒ phoneNo 40 M AES : AES 복호화해서 유저 정보 테이블에서 확인
        phoneNoCheck(authReqInfoDto.getPhoneNo());
        // ⇒ userNm 300 M AES
        userNmCheck(authReqInfoDto.getUserNm());
        // ⇒ birthday 40 M AES & YYMMDD
        birthdayCheck(authReqInfoDto.getBirthday());
    }

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LENGTH = 20;
    @Test
    void certTxIdTest(){
        String certTxId = makeCertTxId();
        System.out.println(certTxId);
    }
    public String makeCertTxId() {
        Random random = new SecureRandom();
        char[] text = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            text[i] = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }
    @Test
    void birthdayCheck(){
        String birthday = "950122";
        String birthday1 = "19950122";
        Assertions.assertThat(birthdayCheck(birthday)).isTrue();
        Assertions.assertThat(birthdayCheck(birthday1)).isFalse();
    }

    @Test
    void matternTest(){
        String userNm = "jaems pa";
        String pattern = "^[a-zA-Z\\s]*$|[가-힣]*$";
        if(userNm.length() > 300 || !userNm.matches(pattern)){
            System.out.println("no");
        }else{
            System.out.println("yes");
        }

    }


    private boolean birthdayCheck(String birthday){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        try {
            LocalDate.parse(birthday, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean phoneNoCheck(String phoneNo){
        String pattern = "^[0-9]*$";
        if(phoneNo.length() > 40 || !pattern.matches(phoneNo)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "phoneNo");
        }
        return true;
    }

    private boolean userNmCheck(String userNm){
        String pattern = "^[a-zA-Z\\s]*[가-힣]*$";
        if(userNm.length() > 300 || !pattern.matches(userNm)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "userNm");
        }
        return true;
    }

    private boolean telcoTycdCheck(String telcoTycd){
        List<String> telcoList = Arrays.asList("S", "L", "K");
        if(!telcoList.contains(telcoTycd)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "telcoTycd");
        }
        return true;
    }

    private boolean serviceTycdCheck(String serviceTycd){
        List<String> serviceTycdList = Arrays.asList("S1001", "S1002", "S1003", "S2001", "S3001", "S3002");
        if(serviceTycd.length() != 5 || !serviceTycdList.contains(serviceTycd)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "serviceTycd");
        }
        return true;
    }

    private boolean reqTxIdCheck(String reqTxId){
        String pattern = ".*[~`!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*"; // 특수문자 패턴
        if(reqTxId.length() != 20 || reqTxId.matches(pattern)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "reqTxId");
        }
        return true;
    }

}