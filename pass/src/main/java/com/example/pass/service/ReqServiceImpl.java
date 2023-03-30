package com.example.pass.service;

import com.example.pass.key.AESCipher;
import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReqServiceImpl implements ReqService{

    private static final String Tycd = "S2001"; // 출금이체동의
    private static final String companyCd = "90001";
    private static final String bear = "AT-111111";
    private static final AESCipher aesCipher = new AESCipher("YzNmOGQ2OGI1ZDEwNDA5YmJmZmRhMTI5");
    private final SqlSession sqlSession;
    private final String reqUrl = "https://api-stg.passauth.co.kr/v1/certification/notice";
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
//    @Override
//    public int insertAuth(Map<String, Object> map) {
//        sqlSession.getMapper(PassDao.class).insertAuth(map);
//        return 0;
//    }

    /**
     * body, 메소드, url
     * @return 요청 응답
     */
    @Override
    public StringBuilder getRes(Object reqDto, String method, String reqUrl) {
        return httpReq(reqDto, method, reqUrl);
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
        int year = Integer.parseInt(userDto.getBirthday().substring(0, 4));
        if("남".equals(userDto.getGender())){
            // 1800 ~ 1899 9
            if(year >=1800 && year <= 1899){
                userDto.setGender("9");
            }// 1900 ~ 1999 1
            else if(year >= 1900 && year <= 1999){
                userDto.setGender("1");
            }// 2000 ~ 2099 3
            else if(year >= 2000 && year <= 2099){
                userDto.setGender("3");
            }
        }else{
            // 1800 ~ 1899 0
            if(year >=1800 && year <= 1899){
                userDto.setGender("0");
            }// 1900 ~ 1999 2
            else if(year >= 1900 && year <= 1999){
                userDto.setGender("2");
            }// 2000 ~ 2099 4
            else if(year >= 2000 && year <= 2099){
                userDto.setGender("4");
            }
        }

        // birthday 변환 1995 => 95
        userDto.setBirthday(userDto.getBirthday().substring(2,userDto.getBirthday().length()));

        // 암호화 ( phoneNo, userNm, birthday, gender )
        try {
            userDto.setUserNm(aesCipher.encrypt(userDto.getUserNm()));
            userDto.setPhoneNo(aesCipher.encrypt(userDto.getPhoneNo()));
            userDto.setBirthday(aesCipher.encrypt(userDto.getBirthday()));
            userDto.setGender(aesCipher.encrypt(userDto.getGender()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userDto;
    }

    /**
     * signTarget 암호화,
     * regTxtId 설정 ( 특수문자 제외 )
     */
    private ReqDto convertRegDto(UserDto userDto){
        // signTarget 암호화
        String singT = null;
        try {
            singT = aesCipher.encrypt("signTarget");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                .signTarget(singT)
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
    private StringBuilder httpReq(Object reqDto, String method, String reqUrl){
        // start 요청
        HttpURLConnection connection = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(reqUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Authorization", "Bearer "+bear);
            connection.setRequestProperty("Content-type", "Application/json; charset=utf8");
            connection.setRequestProperty("Accept", "Application/json; charset=utf8");
            // request body에 데이터 담으려면 true
            connection.setDoOutput(true);

            // 요청 데이터 json 변환
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(reqDto);

            // body에 json 담기
            try (OutputStream os = connection.getOutputStream()){
                byte request_data[] = json.getBytes("utf-8");
                os.write(request_data);
            }catch(Exception e) {
                e.printStackTrace();
            }
            connection.connect();
            // end 요청
            System.out.println(json);
            // start response
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;

            while((line = br.readLine()) != null){
                sb.append(line);
            }
            // end response

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return sb;
    }

}
