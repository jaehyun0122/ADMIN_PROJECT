package com.example.apiserver.dao;

import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao{

    private final SqlSession sqlSession;

    @Override
    public UserDto getUserInfo(String phoneNo) {
        return sqlSession.getMapper(AuthDao.class).getUserInfo(phoneNo);
    }

    @Override
    public void insertAuthReqInfo(AuthReqInfoDto authReqInfoDto) {
        sqlSession.getMapper(AuthDao.class).insertAuthReqInfo(authReqInfoDto);
    }

    @Override
    public void insertAuthResInfo(AuthReqInfoDto authReqInfoDto) {
        sqlSession.getMapper(AuthDao.class).insertAuthResInfo(authReqInfoDto);
    }

    @Override
    public String getKey(String token) {
        return sqlSession.getMapper(AuthDao.class).getKey(token);
    }
}
