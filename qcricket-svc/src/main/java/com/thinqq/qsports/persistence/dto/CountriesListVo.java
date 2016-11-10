package com.thinqq.qsports.persistence.dto;

import java.util.List;

public class CountriesListVo extends BaseResponseVo {
	

	private static final long serialVersionUID = 994022109264938070L;
	private List<CountryVo> countriesList;
	
	public List<CountryVo> getCountriesList() {
		return countriesList;
	}

	public CountriesListVo(final List<CountryVo> countriesList) {
		this.countriesList = countriesList;
		
	}

}
