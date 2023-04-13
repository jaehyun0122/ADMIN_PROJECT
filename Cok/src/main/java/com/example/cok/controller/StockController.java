package com.example.cok.controller;

import com.example.cok.dto.customer.CustomerDto;
import com.example.cok.dto.service.stock.BannerDto;
import com.example.cok.dto.service.stock.InvestDto;
import com.example.cok.dto.service.stock.ItemDto;
import com.example.cok.service.ContentService;
import com.example.cok.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {
    private final CustomerService customerService;
    private final ContentService contentService;
    @GetMapping()
    public String stock(Model model){
        List<ItemDto> foreignBuyTop = contentService.getForeignBuyTop();
        List<InvestDto> investList = contentService.getInvestList();
        List<String> stockHeadList = contentService.getStockHeadList();
        List<BannerDto> bannerList = contentService.getBannerList();
        List<CustomerDto> infoList = customerService.getInfoList("증권추천");

        model.addAttribute("foreignTop", foreignBuyTop);
        model.addAttribute("investList", investList);
        model.addAttribute("stockHeadList", stockHeadList);
        model.addAttribute("bannerList", bannerList);
        model.addAttribute("infoList", infoList);

        return "cp/stock_new_2021";
    }
}
