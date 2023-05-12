package com.example.finalproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Test
    void sendMail(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jungjh122@admin.com");
        message.setSubject("subject");
        message.setText("text");
        message.setTo("jeongjh122@atoncorp.com");

        javaMailSender.send(message);
    }

    @Test
    void excelCreateTest(){
        // 새로운 Workbook 생성
        Workbook workbook = new XSSFWorkbook();

        // 새로운 Sheet 생성
        Sheet sheet = workbook.createSheet("Sheet1");

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
        cell3.setCellValue("암");
        cell3.setCellStyle(style);

        Cell cell4 = row2.createCell(2);
        cell4.setCellValue("접");
        cell4.setCellStyle(style);

        // Excel 파일 저장
        try {
            FileOutputStream fileOut = new FileOutputStream("example.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Excel 파일이 생성되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Workbook 종료
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void randomStringTest(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 32; // 생성할 문자열 길이
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                // StringBuilder에 유니코드 포인터 추가. 자바에서 문자는 utf-16으로 표현되며 일부 문자는 2개의 코드 포인터로 표현. 이런 문자를 정확히 표현하기위해
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
    }

}