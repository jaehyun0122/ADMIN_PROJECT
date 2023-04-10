package com.example.apiserver.service;

import com.example.apiserver.dto.AuthReqInfoDto;
import com.example.apiserver.dto.AuthResInfoDto;


public interface AuthService {
    String headerCheck(String requestHeader);

    boolean authBodyCheck(AuthReqInfoDto body);
    AuthReqInfoDto bodyValidCheck(AuthReqInfoDto body, String key) throws Exception;

    AuthResInfoDto getAuthResponse(AuthReqInfoDto decryptAuthReqInfo);

}
