package com.thinqq.qsports.persistence.dto;

import java.util.List;

import com.thinqq.qsports.server.process.util.TimeZoneOffset.TimeZoneInfo;

public class CountryTimeZoneVo extends BaseVo {

	private static final long serialVersionUID = 1869409647855747839L;

	private String country;
	
	private List<TimeZoneInfo> timeZoneList;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<TimeZoneInfo> getTimeZoneList() {
		return timeZoneList;
	}

	public void setTimeZoneList(List<TimeZoneInfo> timeZoneList) {
		this.timeZoneList = timeZoneList;
	}
	
	
}
