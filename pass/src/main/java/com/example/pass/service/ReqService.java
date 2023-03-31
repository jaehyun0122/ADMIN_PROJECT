package com.example.pass.service;

import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;

public interface ReqService {
    ReqDto getReq(UserDto userDto);
    StringBuilder getRes(Object reqDto, String method, String reqUrl);

    String deAes(String str) throws Exception;
}
