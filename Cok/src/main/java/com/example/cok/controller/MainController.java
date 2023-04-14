package com.example.cok.controller;

import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.GnbDto;
import com.example.cok.dto.service.ServiceDto;
import com.example.cok.dto.customer.SubContentDto;
import com.example.cok.service.ContentService;
import com.example.cok.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ContentService service;
    private final CustomerService customerService;
    @GetMapping
    public String main(Model model){
        List<ServiceDto> serviceList = service.getServiceList();
        List<GnbDto> gnbList = service.getGnbList();
        List<SubContentDto> subContentList = service.getSubContentList();
        List<CustomerDto> infoList = customerService.getInfoList("공지사항");

        model.addAttribute("infoList", infoList);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("subContentList", subContentList);
        model.addAttribute("gnbList", gnbList);

        return "/main/index_service";
    }

    @GetMapping("loginFail")
    public String loginFail(){
        return "main/loginFail";
    }

}
