package com.example.finalproject.mapper;

import com.example.finalproject.dto.service.FindServiceDto;
import com.example.finalproject.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    // admin 사용자 등록
    int insertAdminUser(UserDto userDto);
    // 회원 가입 승인하기
    int permitSignIn(String email);

    // 계정 잠그기
    int lockUser(String email);

    // 계정 잠금 해제
    int unlockUser(String email);

    // 계정 중지
    int pauseUser(String email);

    // 계정 중지 해제
    int unPauseUser(String email);

    // 탈퇴 시키기
    int quitUser(String email);

    // 탈퇴 취소
    int unQuitUser(String email);

}
