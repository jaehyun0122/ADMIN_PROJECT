package com.example.apiserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyDao {
    boolean getCompanyCd(@Param("companyCd") String companyCd);
}
