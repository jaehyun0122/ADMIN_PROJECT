package com.example.cok.dao;

import com.example.cok.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserDao {
    Optional<UserDto> isMember(String userId);
}
