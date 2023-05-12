package com.example.finalproject.controller;

import com.example.finalproject.dto.user.UserDto;
import com.example.finalproject.dto.user.UserRegisterDto;
import com.example.finalproject.service.MailService;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    /**
     * main page
     * @return
     */
    @GetMapping
    public String main(){
        return "/programmer/index";
    }

    /**
     * 로그인 페이지
     * @return
     */
    @GetMapping("login")
    public String login(@ModelAttribute("errorMessage") String errorMessage
            , Model model){

        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }

        return "/programmer/login";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("register")
    public String register(){
        return "/programmer/user_register";
    }

    /**
     * 로그인 요청
     */
    @PostMapping("login")
    public String login(UserDto userDto){
        return "/programmer/index";
    }

    /**
     * 회원가입 요청
     * @param user
     * @return
     */
    @PostMapping("register")
    public String userRegister(Model model, UserDto user){
        userService.insertUser(user);

        return "/programmer/index";
    }


    /**
     * logout
     */
    @GetMapping("logout")
    public String logout(){
        return "/programmer/index";
    }
}
