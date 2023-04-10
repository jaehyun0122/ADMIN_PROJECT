package com.example.apiserver.dao;

import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthDao {
    UserDto getUserInfo(@Param("phoneNo") String phoneNo);

    void insertAuthReqInfo(AuthReqInfoDto authReqInfoDto);

    void insertAuthResInfo(AuthReqInfoDto authReqInfoDto);

    String getKey(String token);
}
