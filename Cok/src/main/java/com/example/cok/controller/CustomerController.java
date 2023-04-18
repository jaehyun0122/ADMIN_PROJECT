package com.example.cok.controller;

import com.example.cok.dto.*;
import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.customer.MainInfoDto;
import com.example.cok.dto.service.ServiceQnaDto;
import com.example.cok.service.ContentService;
import com.example.cok.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ContentService service;
    private final CustomerService customerService;

    @GetMapping()
    public String customer(Model model){
        List<GnbDto> gnbList = service.getGnbList();
        List<MainInfoDto> mainInfoList = customerService.getMainInfoList();
        List<CustomerDto> customerList = customerService.getCustomerList();

        model.addAttribute("gnbList", gnbList);
        model.addAttribute("mainInfoList", mainInfoList);
        model.addAttribute("customerList", customerList);

        return "main/index_customer";
    }

    @GetMapping("service")
    public String serviceQna(Model model){
        List<ServiceQnaDto> serviceQnaList = customerService.getServiceQnaList();
        model.addAttribute("serviceQnaList", serviceQnaList);
        return "customer/service";
    }

    @GetMapping("officialInfo")
    public String officialInfo(Model model){
        List<CustomerDto> officialInfoList = customerService.getInfoList("공지사항");

        model.addAttribute("officialInfoList", officialInfoList);
        return "board/board_list";
    }

    @GetMapping("officialInfo/detail")
    public String officialDetail(@RequestParam("id") int id, Model model){
        CustomerDto infoDetail = customerService.getInfoDetail(id);
        model.addAttribute("detailInfo", infoDetail);
        return "board/board_view";
    }

    @GetMapping("FAQ")
    public String FAQ(Model model){
        List<CustomerDto> faqList = customerService.getInfoList("FAQ");
        model.addAttribute("faqList", faqList);

        return "customer/faq";
    }

    @GetMapping("strategy")
    public String strategy(Model model){
        List<CustomerDto> strategyList = customerService.getInfoList("이용약관 및 정책");
        model.addAttribute("strategyList", strategyList);

        return "customer/privacy";
    }
}
