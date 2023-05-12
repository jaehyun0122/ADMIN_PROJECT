package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindFileDto;
import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.service.ServiceRegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface FileService {
    // 이미지 사이츠 체크
    boolean imageSizeCheck(List<MultipartFile> image) throws IOException;
    // 서비스 등록
    void serviceRegister(ServiceRegisterDto serviceRegisterDto, Authentication authentication, MultipartFile pdf, List<MultipartFile> images) throws IOException;

    // 서비스 목록 가져오기
    List<FindServiceDto> getServiceList(Authentication authentication, String status);

    // 등록 서비스 목록 가져오기
    // type : all - 모두, 승인 - 승인 서비스, 미승인 - 미승인 서비스
    List<FindServiceDto> getServiceList(String type);

    // 모든 서비스 목록
    List<FindServiceDto> getServiceList();

    // 서비스 상세 정보
    FindServiceDto getServiceDetail(int id);

    // file(img or pdf) 다운로드
    FindFileDto downloadFile(int id);

    // serviceId에 해당하는 file 가져오기
    List<FindFileDto> getFile(int id);

    // 파일 다운로드 Http Response 생성
    ResponseEntity createHttpResponse(FindFileDto findFile) throws UnsupportedEncodingException;

    // 서비스 승인
    void servicePermit(int id);

    // 서비스 승인 후 키값 저장
    void insertKey(String encryptKey, String contactKey, int serviceId);

    // 서비스 키 존재 시 업데이트
    void updateServiceKey(String encryptKey, String contactKey, int serviceId);

    // 메일 발송을 위한 서비스 등록자 이메일 가져오기
    String getServiceRegUser(int id);

    // 서비스 반려
    void serviceDeny(Map<String, Object> reqData, Authentication authentication);
}
