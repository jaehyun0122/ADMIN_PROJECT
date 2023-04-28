package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    // 이미지 사이츠 체크
    boolean imageSizeCheck(MultipartFile image) throws IOException;
    // 서비스 등록
    void serviceRegister(ServiceRegisterDto serviceRegisterDto, Authentication authentication) throws IOException;

    // 서비스 목록 가져오기
    List<FindServiceDto> getServiceList(Authentication authentication, String status);
}
