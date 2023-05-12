package com.example.finalproject.service;

import com.example.finalproject.mapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final FileService fileService;
    private final ServiceMapper serviceMapper;


    /**
     * 메일 전송 기능
     */
    public void sendMail(String destination, Authentication authentication, int serviceId) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String encryptKey = null;
        String contactKey = null;
        Map<String, Object> excelInfoMap = new HashMap<>();

        ByteArrayResource excelResource = createExcel(encryptKey, contactKey);

        try { // 서비스 암호화키, 접속키 생성
            helper.setFrom(authentication.getPrincipal().toString());
            helper.setSubject("승인 메일");
            helper.setText("서비스가 승인됐습니다");
            helper.setTo(destination);

            // 암호화키 , 접속키 생성
            encryptKey = makeKey(32);
            contactKey = makeKey(16);

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("승인실패");
        }

        try{ // Excel 파일 생성
            // excel 생성
            createExcel(encryptKey, contactKey);
            // file 테이블에 excel 파일 저장
            excelInfoMap = new HashMap<>();
            excelInfoMap.put("fileName", "가이드.xlsx");
            excelInfoMap.put("fileByte", excelResource.getByteArray());
            excelInfoMap.put("servicePk", serviceId);
            excelInfoMap.put("type", "xlsx");

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("승인실패");
        }

        try { // 메일 발송
            // 메일에 추가
            helper.addAttachment("가이드.xlsx", excelResource);

            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("승인실패");
        }

        // Excel 파일 DB 저장
        serviceMapper.uploadFile(excelInfoMap);
        // 해당 서비스 키값 DB 저장
        // 해당 서비스 키값이 있는 경우 ( 재발송 )
        if(serviceMapper.isRegServiceKey(serviceId)){
            // service 테이블 serviceId로 검색 후 있으면 update

        }else{ // 처음 전송일 경우
            fileService.insertKey(encryptKey, contactKey, serviceId);
        }

        // 메일 전송 후 승인 컬럼 true
        fileService.servicePermit(serviceId);

    }

    /**
     * 암호화 키 (32자리) , 접속키 (16자리) 생성 함수
     */
    private String makeKey(int len){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = len; // 생성할 문자열 길이
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                // StringBuilder에 유니코드 포인터 추가. 자바에서 문자는 utf-16으로 표현되며 일부 문자는 2개의 코드 포인터로 표현. 이런 문자를 정확히 표현하기위해
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }



    /**
     * Excel 생성 함수
     */
     private ByteArrayResource createExcel(String encryptKey, String contactKey) throws IOException {
         // 새로운 Workbook 생성
         Workbook workbook = new XSSFWorkbook();

         // 새로운 Sheet 생성
         Sheet sheet = workbook.createSheet("guide");

         // 셀 스타일 생성
         CellStyle style = workbook.createCellStyle();
         style.setBorderTop(BorderStyle.THIN);
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderLeft(BorderStyle.THIN);
         style.setBorderRight(BorderStyle.THIN);

         // 데이터 행 생성
         Row row1 = sheet.createRow(1);
         // 데이터 셀 생성 및 스타일 적용
         Cell cell1 = row1.createCell(1);
         cell1.setCellValue("암호화키");
         cell1.setCellStyle(style);

         Cell cell2 = row1.createCell(2);
         cell2.setCellValue("접속키");
         cell2.setCellStyle(style);

         Row row2 = sheet.createRow(2);
         Cell cell3 = row2.createCell(1);
         cell3.setCellValue(encryptKey);
         cell3.setCellStyle(style);

         Cell cell4 = row2.createCell(2);
         cell4.setCellValue(contactKey);
         cell4.setCellStyle(style);


         // Excel 파일을 byte 배열로 변환
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         workbook.write(outputStream);
         byte[] fileContent = outputStream.toByteArray();

         // 생성 파일 저장 없이 바로 메일 첨부위해 메모리에 저장
         ByteArrayResource fileResource = new ByteArrayResource(fileContent);

         // Workbook 종료
         try {
             workbook.close();
         } catch (IOException e) {
             e.printStackTrace();
         }


         return fileResource;
     }

}
