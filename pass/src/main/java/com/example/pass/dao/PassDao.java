package com.example.pass.dao;

import com.example.pass.dto.ResultReqDto;
import com.example.pass.dto.ResultResDto;
import com.example.pass.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PassDao {
    int insertAuth(Map<String, Object> map);

    ResultReqDto resultReq(String certTxId);

    int insertAuthResult(ResultResDto resDto);

    List<ResultResDto> authResult(String certTxId);

    UserDto getUserInfo(String certTxId);
}
