package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.CompanySiteDto;
import com.example.demo.dto.RegSiteDto;
import com.example.demo.dto.SearchDto;

@Mapper
public interface SiteDao {
	// 전체 사이트 반환
	List<CompanySiteDto> getSiteList(int offset);
	// 사이트 등록
	int registerSite(RegSiteDto regSiteDto);
	// 제휴사 등록
	int newSite(@Param("companynm") String companynm, @Param("feerate") int feerate);
	// 제휴사 리스트 반환
	List<String> getCompanynm();
	// 사이트 상세 정보 반환
	RegSiteDto getDetail(@Param("id") int id);
	// 사이트 정보 수정
	int modifySite(RegSiteDto regSiteDto);
	// 사이트 삭제
	int deleteSite(@Param("siteId") int id);
	
	int getTotalCnt();
	List<CompanySiteDto> getSearchData(SearchDto searchDto);
}
