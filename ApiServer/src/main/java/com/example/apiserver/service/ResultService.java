package com.example.apiserver.service;


import com.example.apiserver.dto.ResultReqDto;
import com.example.apiserver.dto.ResultResDto;

public interface ResultService {
    boolean resultBodyCheck(ResultReqDto body);

    ResultResDto resultBodyValidCheck(ResultReqDto body, String key) throws Exception;
}
