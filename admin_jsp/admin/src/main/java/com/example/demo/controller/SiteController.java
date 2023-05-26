package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.CompanySiteDto;
import com.example.demo.dto.RegSiteDto;
import com.example.demo.dto.SearchDto;
import com.example.demo.service.SiteServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SiteController {

	private final SiteServiceImpl siteServiceImpl;
	
	// 사이트 전체 목록 반환
	@GetMapping("/")
	public String getSiteList(Model model, @RequestParam int offset) {
		model.addAttribute("siteList", siteServiceImpl.getSiteList(offset*7));
		model.addAttribute("totalCnt", siteServiceImpl.getTotalCnt());
		model.addAttribute("companyList", siteServiceImpl.getCompanynm());
		
		return "index";
	}
	
	// 제휴사 목록 반환
	@GetMapping("/register")
	public String toRegister(Model model){
		List<String> companynmList = siteServiceImpl.getCompanynm();
		model.addAttribute("companyList", companynmList);
		
		return "register";
	}
	
	// 사이트 등록
	@PostMapping("/")
	@ResponseBody
	public int registerSite(RegSiteDto regDto) {
		return siteServiceImpl.registerSite(regDto);
	}
	
	// 제휴사 등록
  @PostMapping("/newSite") 
  @ResponseBody
  public int registerSite(@RequestParam("companyName") String companynm, @RequestParam("feerate") int feerate) {
	  return siteServiceImpl.newSite(companynm, feerate); 
  }
	 
  // 사이트 상세 정보 반환
  @GetMapping("/detail")
  public String detail(Model model, @RequestParam("id") int id) {
	  RegSiteDto regSiteDto = siteServiceImpl.getDetail(id);
	  model.addAttribute("siteDetail", regSiteDto);
	  
	  return "companyDetail";
  }
  
  // 사이트 정보 수정
  @PostMapping("/modify")
  @ResponseBody
  public int modifySite(RegSiteDto regSiteDto) {
	  return siteServiceImpl.modifySite(regSiteDto);
  }
  
  // 사이트 삭제
  @PostMapping("/delete")
  @ResponseBody
  public int deleteSite(@RequestParam("siteId") int id) {
	  return siteServiceImpl.deleteSite(id);
  }
  
  @GetMapping("/search")
  @ResponseBody
  public List<CompanySiteDto> search(Model model,SearchDto searchDto) {
	  List<CompanySiteDto> searchData = siteServiceImpl.getSearchData(searchDto);
	  
	  // 검색 결과에 해당하는 데이터
	  model.addAttribute("siteList", searchData);
	  // 페이지 번호 만들 총 데이터 개수 => 검색 결과 길이
	  model.addAttribute("totalCnt", searchData.size());
	  
	  return searchData;
  }
	 
}
