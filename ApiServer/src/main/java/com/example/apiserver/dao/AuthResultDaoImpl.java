package com.example.apiserver.dao;

import com.example.apiserver.dto.ResultResDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class AuthResultDaoImpl implements AuthResultDao{
    private final SqlSession sqlSession;
    @Override
    public ResultResDto getReqInfo(String certTxId) {
        return sqlSession.getMapper(AuthResultDao.class).getReqInfo(certTxId);
    }

    @Override
    public void insertResultInfo(ResultResDto resultResTransDto) {
        sqlSession.getMapper(AuthResultDao.class).insertResultInfo(resultResTransDto);
    }

    @Override
    public Map<String, String> getReqEndDttm(String cerTxId) {
        return sqlSession.getMapper(AuthResultDao.class).getReqEndDttm(cerTxId);
    }
}
