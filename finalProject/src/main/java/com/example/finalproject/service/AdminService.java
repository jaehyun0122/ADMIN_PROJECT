package com.example.finalproject.service;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.user.UserDto;

import java.util.List;

public interface AdminService {
    // 관리자 계정 등록
    void adminRegister(UserDto userDto);

    // 회원 가입 승인하기
    void permitSignIn(String email);

    // 계정 잠그기
    void lockUser(String email);

    // 계정 잠금 해제
    void unlockUser(String email);

    // 계정 중지
    void pauseUser(String email);

    // 계정 중지 해제
    void unPauseUser(String email);

    // 탈퇴 시키기
    void quitUser(String email);

    // 탈퇴 취소
    void unQuitUser(String email);

    // 서비스 목록 가져오기
    List<FindServiceDto> findServiceList();
}