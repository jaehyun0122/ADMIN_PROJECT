package com.example.finalproject.dto.service;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 서비스 신청 dto
 */
@Data
public class ServiceRegisterDto {
    private String companyName;
    private String companyNo;
    private String email;
    private String originImgName;
    private String originPdfName;
    private MultipartFile image;
    private MultipartFile pdf;
}
