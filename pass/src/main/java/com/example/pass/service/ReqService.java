package com.example.pass.service;

import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import org.json.simple.JSONArray;

public interface ReqService {
    ReqDto getReq(UserDto userDto);
    JSONArray getRes(ReqDto reqDto);
}
