package com.example.pass.service;

import com.example.pass.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

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

}