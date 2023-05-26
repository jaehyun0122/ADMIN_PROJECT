package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegSiteDto {
	private Long siteId;
	private String company;
	private String siteName;
	private String siteStatus;
	private String siteCalculate;
	private String siteUrl;
	private String siteManagerName;
	private String siteManagerNumber;
	private String siteManagerEmail;
	private String siteRegDTTM;
	private String siteProtocol;
	private String siteUrlDetail;
	private String siteType;
	private String siteGearingUrl;
	
}
