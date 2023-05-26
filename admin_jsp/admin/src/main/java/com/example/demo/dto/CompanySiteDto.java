package com.example.demo.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanySiteDto {
	private Long siteId;
	private String siteName;
	private String companynm; // company-companyId와 조인 
	private String siteStatus; 	// 00 - 정상 01 - 중지 02 - 준비중
	private String siteCalculate;
	private String siteRegDTTM;
	private String siteRegUser;
	
	
	public void setSiteRegDTTM(String siteRegDTTM) {
		this.siteRegDTTM = siteRegDTTM;
	}
	
	
}
