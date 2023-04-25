package com.example.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String main(){
        return "/programmer/index";
    }

    @GetMapping("profile")
    public String profile(){
        return "/programmer/profile";
    }

    @GetMapping("service")
    public String viewService(){
        return "/programmer/service";
    }

    @GetMapping("question")
    public String question(){
        return "/programmer/question";
    }

    @GetMapping("login")
    public String login(){
        return "/programmer/login";
    }

    @GetMapping("register")
    public String register(){
        return "/programmer/register";
    }
}
