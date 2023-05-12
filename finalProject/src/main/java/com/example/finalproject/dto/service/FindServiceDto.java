package com.example.finalproject.dto.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindServiceDto {
    private int id;
    private String name;
    private String regUser;
    private String companyName;
    private String companyNo;
    private String email;
    private int isPermit; // 0:대기 , 1:승인 , 2:반려
    private LocalDateTime regAt;
    private LocalDateTime confirmAt;
    private String negativeComment;
}
