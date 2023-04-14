package com.example.cok.dto.service;

import com.example.cok.dao.UserDao;
import com.example.cok.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final SqlSession sqlSession;
    @Override
    public Optional<UserDto> isUser(String userId) {
        Optional<UserDto> member = sqlSession.getMapper(UserDao.class).isMember(userId);


        return Optional.empty();
    }
}
