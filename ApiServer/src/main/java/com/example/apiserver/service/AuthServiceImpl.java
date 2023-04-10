package com.example.apiserver.service;

import com.example.apiserver.dao.AuthDao;
import com.example.apiserver.dao.CompanyDao;
import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.dto.AuthResInfoDto;
import com.example.apiserver.dto.UserDto;
import com.example.apiserver.error.NoDataException;
import com.example.apiserver.error.ErrorInfoEnum;
import com.example.apiserver.error.NoMandatoryOrValidException;
import com.example.apiserver.key.AESCipher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao;
    private final CompanyDao companyDao;

    @Value("${body.auth-request}")
    private final List<String> mandatoryBody;
    /**
     *요청 header 검증
     */
    @Override
    public String headerCheck(String requestHeader) {
        // null 확인
        Optional.ofNullable(requestHeader)
                .orElseThrow(() -> new NoDataException(ErrorInfoEnum.NO_AUTH));

        requestHeader = requestHeader.replace("Bearer ", "");
        // DB에 있는지 확인해서 auth_key리턴
        String key = authDao.getKey(requestHeader);
        Optional.ofNullable(key).orElseThrow(
                ()->new NoDataException(ErrorInfoEnum.NO_AUTH)
        );

        return key;
    }

    /**
     * 인증 요청 바디 누락 값 체크
     * 누락값 O 해당 값 error
     * 누락값 X true
     */
    @Override
    public boolean authBodyCheck(AuthReqInfoDto body) {
        JSONObject jsonObject = new JSONObject(body);
        ObjectMapper om = new ObjectMapper();


        // 필수값 포함 확인
        for(String mandatoryInfo : mandatoryBody){
            if(!jsonObject.has(mandatoryInfo)){
                throw new NoMandatoryOrValidException(ErrorInfoEnum.LEAK_MANDATORY, mandatoryInfo);
            }
        }
        // originalInfo가 필수 값인 경우 체크
        originalInfoCheck(jsonObject);
        // isPASSVerify => serviceTycd = S3001 || S3002인 경우는 Y값
        isPassVerifyCheck(jsonObject);
        // isCombineAUth => serviceTycd = S1001, 1002, 1003, 2001 인 경우 N
        isCombineAuthCheck(jsonObject);

        return true;
    }

    @Override
    public AuthReqInfoDto bodyValidCheck(AuthReqInfoDto body, String key) throws Exception {
        JSONObject object = new JSONObject(body);
        ObjectMapper om = new ObjectMapper();
        AuthReqInfoDto authReqInfoDto = om.readValue(object.toString(), AuthReqInfoDto.class);
        // reqEndDttm 검증
        isAfterTime(authReqInfoDto.getReqEndDttm());

        AESCipher aesCipher = new AESCipher(key);
        authReqInfoDto = decryptValidInfo(aesCipher, authReqInfoDto);
        log.info("인증 요청 복호화 정보 {}", authReqInfoDto);

        // 유효성 검사
        // ⇒ companyCd 5 M ⇒ DB에서 이용기관 코드 확인
        if(!companyDao.getCompanyCd(authReqInfoDto.getCompanyCd())){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "companyCd");
        }
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

        // phoneNo로 유저 정보 가져오기
        UserDto userInfo = authDao.getUserInfo(authReqInfoDto.getPhoneNo());
        // 유저 정보 없으면 error
        Optional.ofNullable(userInfo)
                .orElseThrow(() -> new NoDataException(ErrorInfoEnum.NO_USER));
        // validInfoDto 값 복호화해서 userInfo랑 비교
        log.info("DB 유저 정보 {}", userInfo);
        authDao.insertAuthReqInfo(authReqInfoDto);
        isSameUserInfo(authReqInfoDto, userInfo);

        return authReqInfoDto;
    }

    @Override
    public AuthResInfoDto getAuthResponse(AuthReqInfoDto decryptAuthReqInfo) {
        String certTxId = makeCertTxId();
        decryptAuthReqInfo.setCertTxId(certTxId);

        authDao.insertAuthResInfo(decryptAuthReqInfo);

        AuthResInfoDto authResInfoDto = AuthResInfoDto.builder()
                .certTxId(decryptAuthReqInfo.getCertTxId())
                .reqTxId(decryptAuthReqInfo.getReqTxId())
                .build();

        if(decryptAuthReqInfo.getIsNotification().equals("N")){
            authResInfoDto.setTelcoTxId("telcoTxId");
        }
        return authResInfoDto;
    }

    /**
     *isCombineAUth => serviceTycd = S1001, 1002, 1003, 2001 인 경우 N
     */
    private boolean isCombineAuthCheck(JSONObject jsonObject) {
        if(
                jsonObject.get("serviceTycd").equals("S1001") ||
                        jsonObject.get("serviceTycd").equals("S1002") ||
                        jsonObject.get("serviceTycd").equals("S1003") ||
                        jsonObject.get("serviceTycd").equals("S2001")
        ){
            if(!jsonObject.has("isCombineAuth") || jsonObject.get("isCombineAuth").equals("Y")){
                throw new NoDataException(ErrorInfoEnum.WRONG_BODY);
            }
        }
        return true;
    }

    /**
     *isPASSVerify => serviceTycd = S3001 || S3002인 경우는 Y값
     */
    private boolean isPassVerifyCheck(JSONObject jsonObject) {
        if(
                (jsonObject.get("serviceTycd").equals("S3001") || jsonObject.get("serviceTycd").equals("3002"))
                        && ((!jsonObject.has("isPASSVerify")) || jsonObject.get("isPASSVerify").equals("N"))
        ){
            throw new NoDataException(ErrorInfoEnum.WRONG_BODY);
        }
        // verifyURL => isPASSVerify = N 인 경우 필수
        if(jsonObject.has("isPASSVerify") && jsonObject.get("isPASSVerify").equals("N")){
            if(!jsonObject.has("verifyURL")) {
                throw new NoMandatoryOrValidException(ErrorInfoEnum.LEAK_MANDATORY, "verifyURL");
            }
        }
        return true;
    }

    /**
     *originalInfo가 필수 값인 경우 체크
     */
    private boolean originalInfoCheck(JSONObject jsonObject) {
        if(jsonObject.has("serviceTycd") && jsonObject.has("signTargetTycd")
                &&
                (jsonObject.get("serviceTycd").equals("S1001") ||
                        jsonObject.get("serviceTycd").equals("S1003") ||
                        jsonObject.get("serviceTycd").equals("S2001"))
                &&
                (jsonObject.get("signTargetTycd").equals("2") ||
                        jsonObject.get("signTargetTycd").equals("3"))
                &&
                !jsonObject.has("originalInfo")
        ){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.LEAK_MANDATORY, "originalInfo");
        }

        return true;
    }

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LENGTH = 20;

    public String makeCertTxId() {
        Random random = new SecureRandom();
        char[] text = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            text[i] = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }

    private boolean isAfterTime(String reqEndDttm){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            endTime = LocalDateTime.parse(reqEndDttm, formatter);
        }catch(DateTimeParseException e){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "reqEndDTTM");
        }

        if(now.isAfter(endTime)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "reqEndDTTM");
        }
        return true;
    }

    private boolean isSameUserInfo(AuthReqInfoDto validInfoDto, UserDto userInfo){
        if(!(validInfoDto.getPhoneNo().equals(userInfo.getPhoneNo()) &&
                validInfoDto.getGender().equals(userInfo.getGender()) &&
                validInfoDto.getUserNm().equals(userInfo.getUserNm()) &&
                validInfoDto.getBirthday().equals(userInfo.getBirthday()))
        ) {
            throw new NoDataException(ErrorInfoEnum.NO_USER);
        }

        return true;
    }

    /**
     *userNm, phoneNo, birthday, gender
     * aes 복호화
     */
    private AuthReqInfoDto decryptValidInfo(AESCipher aesCipher, AuthReqInfoDto validInfoDto) throws Exception {
        try{
            validInfoDto.setBirthday(aesCipher.decrypt(validInfoDto.getBirthday()));
            validInfoDto.setGender(aesCipher.decrypt(validInfoDto.getGender()));
            validInfoDto.setPhoneNo(aesCipher.decrypt(validInfoDto.getPhoneNo()));
            validInfoDto.setUserNm(aesCipher.decrypt(validInfoDto.getUserNm()));
        }catch (Exception e){

        }

        return validInfoDto;
    }


    private boolean phoneNoCheck(String phoneNo){
        String pattern = "^[0-9]*$";
        if(phoneNo.length() > 40 || !phoneNo.matches(pattern)){
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "phoneNo");
        }
        return true;
    }

    private boolean userNmCheck(String userNm){
        String pattern = "^[a-zA-Z\\s]*$|[가-힣]*$";
        if(userNm.length() > 300 || !userNm.matches(pattern)){
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

    private boolean birthdayCheck(String birthday){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        try {
            LocalDate.parse(birthday, formatter);
            return true;
        } catch (DateTimeParseException e) {
            throw new NoMandatoryOrValidException(ErrorInfoEnum.NOT_VALID, "birthday");
        }
    }
}
