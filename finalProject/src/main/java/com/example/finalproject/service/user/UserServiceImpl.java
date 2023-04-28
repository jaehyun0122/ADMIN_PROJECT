package com.example.finalproject.service.user;

import com.example.finalproject.dto.user.UserRegisterDto;
import com.example.finalproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void insertUser(UserRegisterDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.userRegister(user);
    }
}
