package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.CompanySiteDto;
import com.example.demo.dto.RegSiteDto;
import com.example.demo.dto.SearchDto;

public interface SiteService {
	// 사이트 전체 목록
	List<CompanySiteDto> getSiteList(int offset);
	int getTotalCnt();
	int registerSite(RegSiteDto regSiteDto);
	int newSite(String companynm, int feerate);
	List<String> getCompanynm();
	RegSiteDto getDetail(int id);
	int modifySite(RegSiteDto regSiteDto);
	int deleteSite(int id);
	List<CompanySiteDto> getSearchData(SearchDto searchDto);
}

