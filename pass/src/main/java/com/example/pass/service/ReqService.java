package com.example.pass.service;

import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;

public interface ReqService {
    ReqDto getReq(UserDto userDto);
}
