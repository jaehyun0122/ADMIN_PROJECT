package com.example.finalproject.mapper;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.dto.user.UserRegisterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mapper
public interface UserMapper {
    /**
     * 유저 등록
     * @param user
     * @return
     */
    int userRegister(UserDto user);

    // 유저 상세 조회
    UserDto getUserProfile(String email);

    /**
     *로그인 요청의 이메일이 존재하는 지 확인
     */
    Optional<UserDto> isMember(String email);

    /**
     * password 틀린 횟수 리턴
     */
    int getPasswordCount(String email);

    /**
     * password 틀린 횟수 카운트
     */
    void passwordCount(String email);

    /**
     * password 틀린 횟수 초기화
     */
    void passwordReset(String email);

    /**
     * 계정 잠금 설정
     */
    void accountLock(String email);

    /**
     * 유저 이름 변경
     */
    int modifyUserName(Map<String, String> data);

    /**
     * 유저 비밀번호 변경
     */
    int modifyPassword(Map<String, String> data);

    // admin 유저 목록 가져오기
    List<UserDto> getUserList(String type);

    // admin user 등록
    int insertAdminUser(UserDto userDto);

    // 최근 접속일 설정
    int updateLastLoginDate(Map<String, Object> reqData);
}
