package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class CountryValidator implements IValidator {
	private static CountryValidator validator = new CountryValidator();
	private CountryValidator(){}
	public static CountryValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String country = (String)value;
			/*if(country.trim().equals("")|| !CountryList.getListCountry().containsKey(country)){
				errorVoList.add(ErrorRepository.countryNotGiven);
			}*/
		}else{
			errorVoList.add(ErrorRepository.countryNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.countryNotGiven);
		return errorVoList;
	}

}
