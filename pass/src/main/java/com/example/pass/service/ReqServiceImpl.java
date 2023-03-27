package com.example.pass.service;

import com.example.pass.dto.AESCipher;
import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ReqServiceImpl implements ReqService{

    private final String Tycd = "S2001"; // 출금이체동의
    private final String companyCd = "90001";
    private static final AESCipher aesCipher = new AESCipher("YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5");
    @Override
    public ReqDto getReq(UserDto userDto) {
        userDto = convertUser(userDto);

        ReqDto reqDto = ReqDto.builder()
                .userNm(userDto.getName())
                .birthday(userDto.getBirthDay())
                .telcoTycd(userDto.getTelcoTycd())
                .phoneNo(userDto.getPhone())
                .companyCd(companyCd)
                .serviceTycd(Tycd)
                .reqTitle("reqTitle")
                .reqCSPhoneNo("reqCSPhoneNO")
                .reqEndDttm("reqEndDttm")
                .isNotification("isNotification")
                .isPASSVerify("Y")
                .signTarget("signTarget")
                .signTargetTycd("signTargetTycd")
                .reqTxId("reqTxId")
                .gender("1")
                .build();

        return reqDto;
    }

    /**
     * 유저 정보 변환,
     * 암호화
     */
    private UserDto convertUser(UserDto userDto){
        // 통신사
        switch (userDto.getTelcoTycd()){
            case "SKT":
                userDto.setTelcoTycd("S");
                break;
            case "KT":
                userDto.setTelcoTycd("K");
                break;
            case "LGU+":
                userDto.setTelcoTycd("L");
                break;
        }

        // 성별

        // 암호화 ( phoneNo, userNm, birthday )
        try {
            userDto.setName(aesCipher.encrypt(userDto.getName()));
            userDto.setPhone(aesCipher.encrypt(userDto.getPhone()));
            userDto.setBirthDay(aesCipher.encrypt(userDto.getPhone()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return userDto;
    }


}
