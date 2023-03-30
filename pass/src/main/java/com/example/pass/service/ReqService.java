package com.example.pass.service;

import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import org.json.simple.JSONArray;

import java.util.Map;

public interface ReqService {
    ReqDto getReq(UserDto userDto);
    StringBuilder getRes(Object reqDto, String method, String reqUrl);

    String deAes(String str) throws Exception;
}
