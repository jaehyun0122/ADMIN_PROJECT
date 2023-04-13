package com.example.cok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("carInsurance")
public class CarInsuranceController {
    @GetMapping
    public String carInsurance(){
        return "cp/carInsurance";
    }
}
