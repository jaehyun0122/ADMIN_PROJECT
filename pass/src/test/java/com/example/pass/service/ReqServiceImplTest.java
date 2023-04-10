package com.example.pass.service;

import com.example.pass.dto.ResDto;
import com.example.pass.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReqServiceImplTest {
    @Test
    void test(){
        // 현재시간 +5분으로 인증 만료시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime()));
    }

    @Test
    void jsonTest() throws JsonProcessingException {
        UserDto userDto = new UserDto("정재현", "950122", "01093689836", "01093689836", "1");
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(userDto);

        System.out.println(json);
    }

    @Value("${url.authUrl}")
    private String auth;
    @Test
    void httpReq(){
        Assertions.assertThat(auth).isEqualTo("https://api-stg.passauth.co.kr/v1/certification/notice");
    }


    @Test
    void testq(){
        Assertions.assertThat("companyCd:'90001', channelTycd:'PW', channelNm:'채널 이름', agencyCd:'11', serviceTycd:'S2001', telcoTycd:'S', phoneNo:'YVE9cu5bkdJCmsmT/FUT2Q==', userNm:'sQVFORiwW6XZo4FPMdnnCw==', birthday:'fOo4Ud5ZvT4HpbIOvQ9DdA==', gender:'RTp2MqvPjWG4f2QNWpHpJA==', reqTitle:'reqTitle', reqContent:'요청 content', reqCSPhoneNo:'023332256', reqEndDttm:'2023-04-03 16:47:24', isNotification:'Y', isPASSVerify:'Y', signTargetTycd:'1', signTarget:'wsb91rsQ4jmS4pzD0FDRDQ==', isUserAgreement:'N', originalInfo:'wsb91rsQ4jmS4pzD0FDRDQ==', reqTxId:'56138735363788127758', isDigitalSign:'Y', isCombineAuth:'N'")
                .isEqualTo("companyCd:'90001', channelTycd:'PW', channelNm:'채널 이름', agencyCd:'11', serviceTycd:'S2001', telcoTycd:'S', phoneNo:'YVE9cu5bkdJCmsmT/FUT2Q==', userNm:'sQVFORiwW6XZo4FPMdnnCw==', birthday:'fOo4Ud5ZvT4HpbIOvQ9DdA==', gender:'RTp2MqvPjWG4f2QNWpHpJA==', reqTitle:'reqTitle', reqContent:'요청 content', reqCSPhoneNo:'023332256', reqEndDttm:'2023-04-03 16:47:24', isNotification:'Y', isPASSVerify:'Y', signTargetTycd:'1', signTarget:'wsb91rsQ4jmS4pzD0FDRDQ==', isUserAgreement:'N', originalInfo:'null', reqTxId:'56138735363788127758', isDigitalSign:'Y', isCombineAuth:'N'");
    }

    @Test
    void clasType(){
        ResDto resDto = new ResDto();
//        Assertions.assertThat(resDto).isInstanceOf(ResDto.class);
        if(resDto instanceof ResDto) System.out.println(true);
    }
}