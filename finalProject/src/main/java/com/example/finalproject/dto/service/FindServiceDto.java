package com.example.finalproject.dto.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindServiceDto {
    private int id;
    private String companyName;
    private String companyNo;
    private String email;
    private String originImgName;
    private String originPdfName;
    private byte[] image;
    private byte[] pdf;
    private String base64Img;
    private String base64Pdf;
    private int isPermit; // 0:대기 , 1:승인 , 2:반려
    private LocalDateTime regAt;
    private LocalDateTime permitAt;
}
