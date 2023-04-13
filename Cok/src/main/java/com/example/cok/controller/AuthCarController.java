package com.example.cok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("authCar")
public class AuthCarController {
    @GetMapping
    public String authCar(){
        return "cp/authCar";
    }
}
