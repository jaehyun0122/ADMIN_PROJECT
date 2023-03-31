package com.example.apiserver.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String header = "AT-111111";
    private static final List<String> mandatoryBody = Arrays.asList(
            "companyCd", "reqTxId", "serviceTycd", "telcoTycd", "phoneNo", "userNm", "birthday", "gender", "reqTitle", "reqCSPhoneNo", "reqEndDttm", "signTargetTycd", "signTarget"
    );
    /**
     *요청 header 검증
     */
    @Override
    public boolean headerCheck(String requestHeader) {
        requestHeader = requestHeader.replace("Bearer ", "");

        if(header.equals(requestHeader)){
            return true;
        }
        return false;
    }

    /**
     * 인증 요청 바디 누락 값 체크
     * 누락값 O 해당 값 리턴
     * 누락값 X none 리턴
     */
    @Override
    public String authBodyCheck(Map<String, String> body) {
        JSONObject jsonObject = new JSONObject(body);

        for(String mandatoryInfo : mandatoryBody){
            if(!jsonObject.containsKey(mandatoryInfo)){
                return mandatoryInfo;
            }
        }

        // originalInfo가 필수 값인 경우 체크
        if(jsonObject.containsKey("serviceTycd") && jsonObject.containsKey("signTargetTycd")
                &&
                (jsonObject.get("serviceTycd").equals("S1001") ||
                        jsonObject.get("serviceTycd").equals("S1003") ||
                        jsonObject.get("serviceTycd").equals("S2001"))
                &&
                (jsonObject.get("signTargetTycd").equals("2") ||
                        jsonObject.get("signTargetTycd").equals("3"))
                &&
                !jsonObject.containsKey("originalInfo")
        ){
            return "originalInfo";
        }

        return "none";
    }
}
