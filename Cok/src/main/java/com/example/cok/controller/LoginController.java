package com.example.cok.controller;

import com.example.cok.dto.user.LoginFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    @GetMapping()
    public String loginPage(){
        return "main/login";
    }

    @PostMapping()
    public String login(LoginFormDto loginForm, Model model){
        model.addAttribute("user", loginForm.getUsername());
        return "main/index_service";
    }

}
