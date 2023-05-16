package com.example.finalproject.service.user;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.dto.user.UserRegisterDto;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 회원 가입 처리
     * @param user
     */
    void insertUser(UserDto user);

    // 유저 상세 정보 조회
    UserDto getUserProfile(String email);

    // 유저 패스워드 수정
    void modifyPassword(Authentication authentication, String password);

    // 유저 이름 수정
    void modifyUserName(Authentication authentication, String userName);

    // admin or user 목록 가져오기
    List<UserDto> getUserList(String type, int page, int pagePerData);

    // admin or user 총 인원 가져오기
    int getUserCount(String type);

    // 최근 접속일 설정
    void setLastLoginDate(Authentication authentication);
}
