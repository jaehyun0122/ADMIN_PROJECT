package com.example.pass.dao;

import com.example.pass.dto.ResultReqDto;
import com.example.pass.dto.ResultResDto;
import com.example.pass.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PassDaoImpl implements PassDao{
    private final SqlSession sqlSession;
    @Override
    public int insertAuth(Map<String, Object> map) {
        return sqlSession.getMapper(PassDao.class).insertAuth(map);
    }

    @Override
    public ResultReqDto resultReq(String certTxId) {
        return sqlSession.getMapper(PassDao.class).resultReq(certTxId);
    }

    @Override
    public int insertAuthResult(ResultResDto resDto) {
        return sqlSession.getMapper(PassDao.class).insertAuthResult(resDto);
    }

    @Override
    public List<ResultResDto> authResult(String certTxId) {
        return sqlSession.getMapper(PassDao.class).authResult(certTxId);
    }

    @Override
    public UserDto getUserInfo(String certTxId) {
        return sqlSession.getMapper(PassDao.class).getUserInfo(certTxId);
    }
}
