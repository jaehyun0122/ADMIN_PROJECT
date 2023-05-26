package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SearchDto {
	private String company;
	private String siteName;
	private String siteStatus;
	private String siteCalculate;
	private String siteUrl;
	private String startDate;
	private String endDate;
	
	public void setSiteStatus(String siteStatus) {
		this.siteStatus = siteStatus;
	}
	
	
}
