package com.example.finalproject.mapper;

import com.example.finalproject.dto.service.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ServiceMapper {
    // 서비스 등록
    int serviceRegister(ServiceRegisterDto serviceRegisterDto);

    // 로그인 유저 서비스 목록 가져오기
    List<FindServiceDto> getServiceList(Map<String, String> paramMap);

    // 로그인 유저 총 서비스 수 가져오기
    int getUserServiceCount(String email);

    // 검색에 맞는 등록 서비스 가져오기.
    List<FindServiceDto> getService(String type);

    // 해당 페이지 데이터 가져오기
    List<FindServiceDto> allServiceList(Map<String, Integer> map);

    // 모든 서비스 개수 가져오기
    int getListSize();

    // 서비스 상세 목록
    FindServiceDto getServiceDetail(int id);

    // file(img or pdf) 다운로드
    FindFileDto getFile(int id);

    // serviceId에 해당하는 파일 가져오기
    List<FindFileDto> getFileList(int id);

    // serviceId에 해당하는 파일 업데이트
    void updateGuidFile(Map<String, Object> map);

    // file 업로드
    int uploadFile(Map<String, Object> fileInfo);

    // 서비스 승인하기
    int updateIsPermit(Map<String, Object> reqData);

    // 서비스 승인 후 키값 저장
    int insertKey(Map<String, Object> keyMap);

    // 승인 메일 발송을 위한 서비스 등록자 이메일 가져오기
    String getServiceRegUser(int id);

    // 서비스 반려하기
    int denyService(Map<String, Object> reqData);

    // 서비스 키값 존재 확인 (재발송 경우)
    boolean isRegServiceKey(int serviceId);

    // 서비스 키 존재 시 업데이트
    int updateServiceKey(Map<String, Object> keyMap);

    // 대기, 승인, 반려 상태에 따른 서비스 개수
    int getServiceCount(Map<String, String> map);

    // 문의 내역 현황
    int getQuestionCount(Map<String, String> map);


}
