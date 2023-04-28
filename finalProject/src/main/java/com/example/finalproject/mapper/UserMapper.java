package com.example.finalproject.mapper;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.dto.user.UserRegisterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    /**
     * 유저 등록
     * @param user
     * @return
     */
    int userRegister(UserRegisterDto user);

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

}
