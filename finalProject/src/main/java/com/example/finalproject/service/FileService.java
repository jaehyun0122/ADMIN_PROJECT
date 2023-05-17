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

    // 파일 확장자 체크
    void imageTypeCheck(List<MultipartFile> image);
    void pdfTypeCheck(MultipartFile pdf);

    // admin 서비스 가져오기
    List<FindServiceDto> getServiceList(int page);

    // 접속 유저 서비스 페이지 데이터 가져오기
    List<FindServiceDto> getServiceList(Authentication authentication, int page);

    // 접속 유저 등록 서비스 총 개수 가져오기
    int getUserServiceCount(Authentication authentication);

    // 모든 서비스 개수 가져오기
    int getListSize();

    // 서비스 상세 정보
    FindServiceDto getServiceDetail(int id);

    // file(img or pdf) 다운로드
    FindFileDto downloadFile(int id);

    // serviceId에 해당하는 file 가져오기
    List<FindFileDto> getFile(int id);

    // serviceId에 해당하는 file update
    void updateGuidFile(Map<String, Object> data);

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

    // 대기, 승인, 반려에 따른 서비스 개수 가져오기
    int getServiceCount(Authentication authentication, String type);

    // 문의 내역 현화 가져오기
    int getQuestionCount(Authentication authentication, String type);

}
