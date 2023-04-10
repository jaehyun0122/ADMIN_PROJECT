package com.example.pass.service;

import com.example.pass.dto.*;
import java.util.List;

public interface ReqService {
    ReqDto getReq(UserDto userDto);
//    ReqDto getReqDto(Object reqDto, String method, String reqUrl) throws Exception;
    ResDto getResDto(ReqDto reqDto, String method, String reqUrl) throws Exception;
    ResultResDto getResultResDto(ResultReqDto reqDto, String method, String reqUrl) throws Exception;

    String deAes(String str) throws Exception;


    int insertAuth(ReqDto reqDto, ResDto resDto);

    ResultReqDto resultReq(String certTxId);

    int insertAuthResult(ResultResDto resDto) throws Exception;

    List<ResultResDto> authResult(String certTxId);

    UserDto getUserInfo(String certTxId);

    List<ResultResDto> convertResult(List<ResultResDto> authResult, UserDto userInfo, String certTxId) throws Exception;
    UserDto setUserInfo(UserDto userInfo) throws Exception;
    ResultResDto setResult(ResultResDto res, UserDto user);

    ReqDto againReq(String certTxId);

    ReqDto decryptInfo(ReqDto reqDto) throws Exception;
    String getRsaKey(int id);
}
