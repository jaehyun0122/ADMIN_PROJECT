package com.example.pass.controller;

import com.example.pass.dto.ReqDto;
import com.example.pass.dto.UserDto;
import com.example.pass.service.ReqServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ReqServiceImpl reqService;
    @GetMapping("/login")
    public String test(){
        return "login";
    }

    @GetMapping
    public String info(UserDto userDto){
        ReqDto reqDto = reqService.getReq(userDto);

        return "join";
    }
}
