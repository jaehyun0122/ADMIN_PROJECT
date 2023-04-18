package com.example.cok.controller.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("smartFishing")
public class SmartPhishingController {
    @GetMapping
    public String smartFishing(){
        return "cp/smartPhishing";
    }
}
