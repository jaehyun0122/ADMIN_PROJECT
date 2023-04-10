package com.example.pass.service;

import com.example.pass.dao.PassDao;
import com.example.pass.dto.*;
import com.example.pass.key.AESCipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReqServiceImpl implements ReqService {

    @Value("${info.Tycd}")
    private String Tycd; // 출금이체동의
    @Value("${info.companyCd}")
    private String companyCd;
    @Value("${url.bear}")
    private String bear;
    private static final AESCipher aesCipher = new AESCipher("YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5");
    private final SqlSession sqlSession;
    @Override
    public ReqDto getReq(UserDto userDto) {
        userDto = convertUser(userDto);
        ReqDto reqDto = convertRegDto(userDto);

        return reqDto;
    }

    @Override
    public String deAes(String str) throws Exception {
        return aesCipher.decrypt(str);
    }

    @Override
    public ResDto getResDto(ReqDto reqDto, String method, String reqUrl) throws Exception {
        ObjectMapper om = new ObjectMapper();
        // phoneNo, userNm, gender, birthday
        // signTarget => AES 암호화
        reqDto.setUserNm(aesCipher.encrypt(reqDto.getUserNm()));
        reqDto.setGender(aesCipher.encrypt(reqDto.getGender()));
        reqDto.setBirthday(aesCipher.encrypt(reqDto.getBirthday()));
        reqDto.setPhoneNo(aesCipher.encrypt(reqDto.getPhoneNo()));
        reqDto.setSignTarget(aesCipher.encrypt(reqDto.getSignTarget()));

        return om.readValue(httpReq(reqDto, method, reqUrl), ResDto.class);
    }
    @Override
    public ResultResDto getResultResDto(ResultReqDto reqDto, String method, String reqUrl) throws Exception {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(httpReq(reqDto, method, reqUrl), ResultResDto.class);
    }

    @Override
    public int insertAuth(ReqDto reqDto, ResDto resDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("ReqDto", reqDto);
        map.put("ResDto", resDto); // certTxId, reqTxId

        return sqlSession.getMapper(PassDao.class).insertAuth(map);
    }

    @Override
    public ResultReqDto resultReq(String certTxId) {
        return sqlSession.getMapper(PassDao.class).resultReq(certTxId);
    }

    @Override
    public int insertAuthResult(ResultResDto resDto) throws Exception {
        // 유저 정보 aes 복호화 해서 db 저장할 때 암호화
        return sqlSession.getMapper(PassDao.class).insertAuthResult(resDto);
    }

    @Override
    public ReqDto decryptInfo(ReqDto reqDto) throws Exception {
        reqDto.setPhoneNo(aesCipher.decrypt(reqDto.getPhoneNo()));
        reqDto.setUserNm(aesCipher.decrypt(reqDto.getUserNm()));
        reqDto.setBirthday(aesCipher.decrypt(reqDto.getBirthday()));
        reqDto.setGender(aesCipher.decrypt(reqDto.getGender()));

        return reqDto;
    }

    @Override
    public List<ResultResDto> authResult(String certTxId) {
        return sqlSession.getMapper(PassDao.class).authResult(certTxId);
    }

    @Override
    public UserDto getUserInfo(String certTxId) {
        return sqlSession.getMapper(PassDao.class).getUserInfo(certTxId);
    }

    /**
     * 통신사, 성별 변환,
     * 암호화
     */
    private UserDto convertUser(UserDto userDto){
        // 통신사
        if(userDto != null){
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
        }

        // 성별, 나이에 따른 gender 세팅
        // 1,3, 외국인 5,7
        // 2,4, 외국인 6,8
        // birthday 변환 950122 1
        userDto.setGender(userDto.getBirthday().substring(6));
        userDto.setBirthday(userDto.getBirthday().substring(0, 6));
        log.info("인증 요청 유저 정보 : {}", userDto);


        // 암호화 ( phoneNo, userNm, birthday, gender )
//        try {
//            userDto.setUserNm(aesCipher.encrypt(userDto.getUserNm()));
//            userDto.setPhoneNo(aesCipher.encrypt(userDto.getPhoneNo()));
//            userDto.setBirthday(aesCipher.encrypt(userDto.getBirthday()));
//            userDto.setGender(aesCipher.encrypt(userDto.getGender()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return userDto;
    }

    /**
     * signTarget 암호화,
     * regTxtId 설정 ( 특수문자 제외 )
     */
    private ReqDto convertRegDto(UserDto userDto){
        // signTarget 암호화
//        String singT = null;
//        try {
//            singT = aesCipher.encrypt("signTarget");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        // reqTxId : 랜덤 ( 특수문제 제외 ) , 20자리 => 0~9까지의 숫자 조합으로 설정
        String reqTxId = randomStr();
        // 현재시간 +5분으로 인증 만료시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ReqDto reqDto = ReqDto.builder()
                .userNm(userDto.getUserNm())
                .birthday(userDto.getBirthday())
                .telcoTycd(userDto.getTelcoTycd())
                .phoneNo(userDto.getPhoneNo())
                .companyCd(companyCd)
                .serviceTycd(Tycd)
                .reqTitle("reqTitle") // 아무 텍스트
                .reqCSPhoneNo("023332256") // 아무 번호
                .reqEndDttm(sdf.format(calendar.getTime())) // YYYY-MM-DD hh:mi:ss , 현재시간 +5분
                .isNotification("Y")
                .isPASSVerify("Y")
                .signTarget("signTarget")
                .signTargetTycd("1")
                .reqTxId(reqTxId)
                .gender(userDto.getGender())
                .reqContent("요청 content")
                .channelTycd("PW")
                .channelNm("채널 이름")
                .agencyCd("11")
                .isDigitalSign("Y")
                .isCombineAuth("N")
                .isUserAgreement("N")
                .originalInfo("originalInfo")
                .build();

        return reqDto;
    }

    // reqTxId : 랜덤 ( 특수문제 제외 ) , 20자리
    private String randomStr(){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        // 20 자리
        for(int i=0; i<20; i++){
            sb.append(random.nextInt(9));
        }

        return sb.toString();
    }


    /**
     *body, method, url 로 http요청
     * @return 요청 응답 데이터
     */
    private String httpReq(Object reqDto, String method, String reqUrl) throws Exception {
        // start 요청
        // http client 생성
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(reqDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(reqUrl))
                .header("Authorization", "Bearer "+bear)
                .header("Content-type", "Application/json; charset=utf8")
                .header("Accept", "Application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            throw new Exception(response.body());
        }
        return response.body();
    }

    @Override
    public List<ResultResDto> convertResult(List<ResultResDto> authResult, UserDto userInfo, String certTxId) throws Exception {
        String decryptKey = this.getRsaKey(1);

        for(ResultResDto res : authResult){
            res = setResult(res, userInfo);
            if("1".equals(res.getResultTycd()) && certTxId.equals(res.getCertTxId())){
//                RsaDecrypt rsaDecrypt = new RsaDecrypt(res.getCi(), decryptKey);
//                res.setDecryptCi(rsaDecrypt.ciDecryption());
                res.setDecryptCi(aesCipher.decrypt(res.getCi()));
            }

            if(Integer.parseInt(res.getGender()) % 2 == 0){
                res.setGender("여");
            }else res.setGender("남");

            if("S".equals(res.getTelcoTycd())){
                res.setTelcoTycd("SKT");
            }else if("K".equals(res.getTelcoTycd())){
                res.setTelcoTycd("KT");
            }else if("L".equals(res.getTelcoTycd())){
                res.setTelcoTycd("LGU+");
            }
        }

        return authResult;
    }

    @Override
    public UserDto setUserInfo(UserDto userInfo) throws Exception {
        userInfo.setUserNm(this.deAes(userInfo.getUserNm()));
        userInfo.setGender(this.deAes(userInfo.getGender()));
        userInfo.setBirthday(this.deAes(userInfo.getBirthday()));
        userInfo.setPhoneNo(this.deAes(userInfo.getPhoneNo()));

        return userInfo;
    }

    @Override
    public ResultResDto setResult(ResultResDto res, UserDto user){
        res.setGender(user.getGender());
        res.setBirthday(user.getBirthday());
        res.setUserNm(user.getUserNm());
        res.setPhoneNo(user.getPhoneNo());

        return res;
    }

    @Override
    public ReqDto againReq(String certTxId) {
        return sqlSession.getMapper(PassDao.class).againReq(certTxId);
    }

    @Override
    public String getRsaKey(int id) {
        return sqlSession.getMapper(PassDao.class).getRsaKey(1);
    }
}
