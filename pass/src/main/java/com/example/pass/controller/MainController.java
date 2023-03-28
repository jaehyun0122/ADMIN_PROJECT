package com.example.pass.controller;

import com.example.pass.dto.AESCipher;
import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import com.example.pass.service.ReqServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ReqServiceImpl reqService;
    @GetMapping("/login")
    public String test(){
        return "login";
    }

    @GetMapping
    @ResponseBody
    public ReqDto info(UserDto userDto){
        ReqDto reqDto = reqService.getReq(userDto);
//        reqService.getRes(reqDto);
        return reqDto;
    }

    @GetMapping("moveJoin")
    public String join(){
        return "join";
    }

    @GetMapping("join")
    public String join(ReqDto reqDto){
        reqDto.setOriginalInfo(null);

        reqService.getRes(reqDto);
        return "join";
    }
}
