package com.example.cok.service;

import com.example.cok.dao.ContentServiceDao;
import com.example.cok.dto.GnbDto;
import com.example.cok.dto.service.ServiceDto;
import com.example.cok.dto.customer.SubContentDto;
import com.example.cok.dto.service.stock.BannerDto;
import com.example.cok.dto.service.stock.InvestDto;
import com.example.cok.dto.service.stock.ItemDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{
    private final SqlSession sqlSession;

    @Override
    public List<ServiceDto> getServiceList() {
        return sqlSession.getMapper(ContentServiceDao.class).getServiceList();
    }

    @Override
    public List<GnbDto> getGnbList() {
        return sqlSession.getMapper(ContentServiceDao.class).getGnbList();
    }

    @Override
    public List<SubContentDto> getSubContentList() {
        List<SubContentDto> subContentList = new ArrayList<>();
        subContentList.add(sqlSession.getMapper(ContentServiceDao.class).getSubContentItem());
        subContentList.add(sqlSession.getMapper(ContentServiceDao.class).getSubContentInvest());
        subContentList.add(sqlSession.getMapper(ContentServiceDao.class).getSubContentStock());

        return subContentList;
    }

    @Override
    public List<ItemDto> getForeignBuyTop() {
        return sqlSession.getMapper(ContentServiceDao.class).getForeignBuyTop();
    }

    @Override
    public List<InvestDto> getInvestList() {
        return sqlSession.getMapper(ContentServiceDao.class).getInvestList();
    }

    @Override
    public List<String> getStockHeadList() {
        return sqlSession.getMapper(ContentServiceDao.class).getStockHeadList();
    }

    @Override
    public List<BannerDto> getBannerList() {
        return sqlSession.getMapper(ContentServiceDao.class).getBannerList();
    }
}
