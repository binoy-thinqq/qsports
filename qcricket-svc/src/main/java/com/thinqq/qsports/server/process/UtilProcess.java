package com.thinqq.qsports.server.process;

import java.util.List;

import com.thinqq.qsports.persistence.dto.CountryTimeZoneVo;
import com.thinqq.qsports.persistence.dto.CountryVo;
import com.thinqq.qsports.server.process.util.CountryList;
import com.thinqq.qsports.server.process.util.TimeZoneOffset;
import com.thinqq.qsports.server.process.util.TimeZoneOffset.TimeZoneInfo;

public class UtilProcess {

	public List<CountryVo> getAllCountries() {
		List<CountryVo> listCountry = CountryList.getListCountry();
		return listCountry;
	}
	
	public CountryTimeZoneVo getTimezoneForCountry(String countryCode) {
		CountryTimeZoneVo countryTimezoneVo = new CountryTimeZoneVo();
		List<TimeZoneInfo> listTimezone = TimeZoneOffset.getTimeZoneList(countryCode);
		countryTimezoneVo.setCountry(countryCode);
		countryTimezoneVo.setTimeZoneList(listTimezone);
		return countryTimezoneVo;
	}

}
