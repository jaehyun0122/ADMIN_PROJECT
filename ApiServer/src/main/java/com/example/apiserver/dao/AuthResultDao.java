package com.example.apiserver.dao;

import com.example.apiserver.dto.ResultResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface AuthResultDao {
    ResultResDto getReqInfo(@Param("certTxId") String certTxId);

    void insertResultInfo(ResultResDto resultResTransDto);

    Map<String, String> getReqEndDttm(String cerTxId);
}
