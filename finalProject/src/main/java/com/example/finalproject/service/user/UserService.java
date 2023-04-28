package com.example.finalproject.service.user;

import com.example.finalproject.dto.user.UserRegisterDto;

public interface UserService {
    /**
     * 회원 가입 처리
     * @param user
     */
    void insertUser(UserRegisterDto user);

}
