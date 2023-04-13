package com.example.cok.dao;

import com.example.cok.dto.GnbDto;
import com.example.cok.dto.service.ServiceDto;
import com.example.cok.dto.customer.SubContentDto;
import com.example.cok.dto.service.stock.BannerDto;
import com.example.cok.dto.service.stock.InvestDto;
import com.example.cok.dto.service.stock.ItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.Banner;

import java.util.List;

@Mapper
public interface ContentServiceDao {
    List<ServiceDto> getServiceList();
    SubContentDto getSubContentItem();
    SubContentDto getSubContentInvest();
    SubContentDto getSubContentStock();
    List<GnbDto> getGnbList();

    List<String> getStockHeadList();
    List<ItemDto> getForeignBuyTop();
    List<InvestDto> getInvestList();

    List<BannerDto> getBannerList();

}
