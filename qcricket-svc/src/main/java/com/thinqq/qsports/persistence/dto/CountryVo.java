package com.thinqq.qsports.persistence.dto;

public class CountryVo{

	private String countryName;
	
	private String countryCode;
	
	private int code;
	
	public CountryVo () {
	}
	
	
	public CountryVo(String countryName, String countryCode) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
	}


	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
