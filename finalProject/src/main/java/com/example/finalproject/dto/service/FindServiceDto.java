package com.example.finalproject.dto.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindServiceDto {
    private String companyName;
    private String companyNo;
    private String email;
    private String originImgName;
    private String originPdfName;
    private byte[] image;
    private byte[] pdf;
    private int isPermit;
    private LocalDateTime regAt;
    private LocalDateTime permitAt;
}