package com.example.apiserver.service;

import com.example.apiserver.dao.AuthResultDaoImpl;
import com.example.apiserver.dto.ResultReqDto;
import com.example.apiserver.dto.ResultResDto;
import com.example.apiserver.error.ErrorInfoEnum;
import com.example.apiserver.error.NoDataException;
import com.example.apiserver.error.NoMandatoryOrValidException;
import com.example.apiserver.key.AESCipher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService{
    private final AuthResultDaoImpl resultDao;
    @Value("${body.result-request}")
    private final List<String> resultReqMandatoryBody;

    @Override
    public boolean resultBodyCheck(ResultReqDto body) {
        JSONObject jsonObject = new JSONObject(body);

        for(String mandatoryInfo : resultReqMandatoryBody){
            if(!jsonObject.has(mandatoryInfo)){
                throw new NoMandatoryOrValidException(ErrorInfoEnum.RESULT_LEAK_MANDATORY, mandatoryInfo);
            }
        }
        return true;
    }

    @Override
    public ResultResDto resultBodyValidCheck(ResultReqDto body, String key) throws Exception {
        JSONObject jsonObject = new JSONObject(body);
        ObjectMapper om = new ObjectMapper();
        ResultReqDto userResultReq = om.readValue(jsonObject.toString(), ResultReqDto.class);
        // certTxId로 조회 resultTycd , digitalSign, CI,
        // reqTxId, telcoTxId, certTxId, resultDttm, userNm, birthday, gender, phoneNo, taelcoTycd
        ResultResDto findReqInfo = resultDao.getReqInfo(userResultReq.getCertTxId());
        Optional.ofNullable(findReqInfo).orElseThrow(
                () -> new NoDataException(ErrorInfoEnum.NOT_VALID_TIMEOUT)
        );
        log.info("DB에서 찾은 정보 {}", findReqInfo);
        // reqEndDttm이 유효하면
         Map<String, String> findreqEndDttmAndSignTarget = resultDao.getReqEndDttm(userResultReq.getCertTxId());
        String findReqEndDttm = findreqEndDttmAndSignTarget.get("reqEndDttm");
        // digitalSign 만들때 signTarget을 aes 암호화
        String signTarget = findreqEndDttmAndSignTarget.get("signTarget");
        isAfterTime(findReqEndDttm);

        findReqInfo.setTelcoTxId("telcoTxId");
        findReqInfo.setResultDttm(getResultDttm());
        // resultTycd, digitalSign, Ci 만들어서 보내기
        findReqInfo.setResultTycd("1");
        if(findReqInfo.getResultTycd().equals("1")){
            // resultTycd : 1 인 경우
            findReqInfo.setCi(makeCi(key, userResultReq.getUserNm(), userResultReq.getPhoneNo()));
            // isDigitalSign : Y || resultTycd : 1
            findReqInfo.setDigitalSign(makeDigitalSign(signTarget, key));
        }

        AESCipher aesCipher = new AESCipher(key);
        findReqInfo = encryptUserInfo(aesCipher ,findReqInfo);
        resultDao.insertResultInfo(findReqInfo);

        return findReqInfo;
    }

    private String getResultDttm(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return now.format(formatter);
    }

    /**
     * userNm, phoneNo, birthday, gender AES 암호화
     */
    private ResultResDto encryptUserInfo(AESCipher aesCipher, ResultResDto resultResTransDto) throws Exception {
        resultResTransDto.setUserNm(aesCipher.encrypt(resultResTransDto.getUserNm()));
        resultResTransDto.setBirthday(aesCipher.encrypt(resultResTransDto.getBirthday()));
        resultResTransDto.setGender(aesCipher.encrypt(resultResTransDto.getGender()));
        resultResTransDto.setPhoneNo(aesCipher.encrypt(resultResTransDto.getPhoneNo()));
        return resultResTransDto;
    }

    /**
     *ci 값 생성
     * AES 암호화
     * 200 자리
     */
    private String makeCi(String key, String userNm, String phoneNo) throws Exception {
        AESCipher aesCipher = new AESCipher(key);
        return aesCipher.encrypt(userNm+phoneNo);
    }

    /**
     *digitalSign 생성
     */
    private String makeDigitalSign(String signTarget, String key) throws Exception {
        AESCipher aesCipher = new AESCipher(key);
        return aesCipher.encrypt(signTarget);
    }

    private boolean isAfterTime(String reqEndDttm){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            endTime = LocalDateTime.parse(reqEndDttm, formatter);
        }catch(DateTimeParseException e){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.RESULT_NOT_VALID, "reqEndDTTM");
        }

        if(now.isAfter(endTime)){
            throw new NoDataException(ErrorInfoEnum.NOT_VALID_TIMEOUT);
        }
        return true;
    }
}
