package com.example.apiserver.dao;


import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CompanyDaoImpl implements CompanyDao{
    private final SqlSession sqlSession;
    @Override
    public boolean getCompanyCd(String companyCd) {
        return sqlSession.getMapper(CompanyDao.class).getCompanyCd(companyCd);
    }
}
