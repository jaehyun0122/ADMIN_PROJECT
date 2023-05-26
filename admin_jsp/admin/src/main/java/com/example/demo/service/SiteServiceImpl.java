package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SiteDao;
import com.example.demo.dto.CompanySiteDto;
import com.example.demo.dto.RegSiteDto;
import com.example.demo.dto.SearchDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService{
	
	private final SqlSession sqlSession;


	// 전체 사이트 목록 반환
	@Override
	public List<CompanySiteDto> getSiteList(int offset) {
		List<CompanySiteDto> result = sqlSession.getMapper(SiteDao.class).getSiteList(offset);
		for(CompanySiteDto company : result) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			try {
				Date date = sdf.parse(company.getSiteRegDTTM());
				SimpleDateFormat fDate = new SimpleDateFormat("yyyy.MM.dd");
				company.setSiteRegDTTM(fDate.format(date));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}

	
	
	
	// 사이트 등록
	@Override
	public int registerSite(RegSiteDto regSiteDto) {
		// siteStatus 변경 
		// 00 => 정상 01 => 중지 02 => 준비중
		String status = regSiteDto.getSiteStatus();
		
		regSiteDto.setSiteStatus(convertStatus(status));
		
		// 사이트 타입
		String type = regSiteDto.getSiteType();
		if("마케팅 제휴사".equals(type)) {
			regSiteDto.setSiteType("00");
		}else if("서비스 제휴사".equals(type)) {
			regSiteDto.setSiteType("01");
		}else regSiteDto.setSiteType("02");
		
		// 날짜 형식 변환
		String now = transDate();
		regSiteDto.setSiteRegDTTM(now);
		
		String[] url = splitUrl(regSiteDto.getSiteUrl());
		
		// siteProtocol 설정
		regSiteDto.setSiteProtocol(url[0]);
		// siteUrlDetail 설정
		regSiteDto.setSiteUrlDetail(url[1]);
		
		return sqlSession.getMapper(SiteDao.class).registerSite(regSiteDto);
		
	}

	
	// 제휴사 등록
	@Override
	public int newSite(String companynm, int feerate) {
		return sqlSession.getMapper(SiteDao.class).newSite(companynm, feerate);
	}


	// 제휴사 목록 반환
	@Override
	public List<String> getCompanynm() {
		return sqlSession.getMapper(SiteDao.class).getCompanynm();
	}


	// 사이트 상세정보 반환
	@Override
	public RegSiteDto getDetail(int id) {
		return sqlSession.getMapper(SiteDao.class).getDetail(id);
	}
	
	// 사이트 정보 수정
	@Override
	public int modifySite(RegSiteDto regSiteDto) {

		String status = regSiteDto.getSiteStatus();
		regSiteDto.setSiteStatus(convertStatus(status));
		
		String[] url = splitUrl(regSiteDto.getSiteUrl());
		// siteProtocol 설정
		regSiteDto.setSiteProtocol(url[0]);
		// siteUrlDetail 설정
		regSiteDto.setSiteUrlDetail(url[1]);
		
		return sqlSession.getMapper(SiteDao.class).modifySite(regSiteDto);
	}


	@Override
	public int deleteSite(int id) {
		return sqlSession.getMapper(SiteDao.class).deleteSite(id);
	}
	
	
	private String transDate() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		return formatter.format(today);
	}
	
	private String[] splitUrl(String url) {
		return url.split("://");
	}




	@Override
	public int getTotalCnt() {
		return sqlSession.getMapper(SiteDao.class).getTotalCnt();
	}




	public List<CompanySiteDto> getSearchData(SearchDto searchDto) {
		String val = searchDto.getSiteStatus();
		switch(val) {
			case "정상": 
				searchDto.setSiteStatus("00");
				break;
			case "중지":
				searchDto.setSiteStatus("01");
				break;
			case "준비중":
				searchDto.setSiteStatus("02");
				break;
		}
		System.out.println("trans dto : "+searchDto.toString());
		return sqlSession.getMapper(SiteDao.class).getSearchData(searchDto);
	}
	
	private String convertStatus(String status) {
		String modStatus = "";
			switch(status) {
			case "정상":
				modStatus = "00";
				break;
			case "중지":
				modStatus = "01";
				break;
			case "준비중":
				modStatus = "02";
				break;
		}
			return modStatus;
	}
	
}
