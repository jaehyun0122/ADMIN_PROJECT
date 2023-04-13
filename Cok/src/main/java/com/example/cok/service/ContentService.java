package com.example.cok.service;

import com.example.cok.dto.GnbDto;
import com.example.cok.dto.service.ServiceDto;
import com.example.cok.dto.customer.SubContentDto;
import com.example.cok.dto.service.stock.BannerDto;
import com.example.cok.dto.service.stock.InvestDto;
import com.example.cok.dto.service.stock.ItemDto;

import java.util.List;

public interface ContentService {
    List<ServiceDto> getServiceList();

    List<SubContentDto> getSubContentList();
    List<GnbDto> getGnbList();

    List<String> getStockHeadList();
    List<ItemDto> getForeignBuyTop();
    List<InvestDto> getInvestList();
    List<BannerDto> getBannerList();
}
