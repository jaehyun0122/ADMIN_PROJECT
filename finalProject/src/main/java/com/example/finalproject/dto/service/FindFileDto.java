package com.example.finalproject.dto.service;

import lombok.Data;

@Data
public class FindFileDto {
    private int id;
    private byte[] fileByte;
    private String fileName;
    private String base64Encode;
    private String type;
}
