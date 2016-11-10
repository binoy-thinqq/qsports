package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class CityValidator implements IValidator {
	private static CityValidator validator = new CityValidator();
	private CityValidator(){}
	public static CityValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String city = (String)value;
			if(!city.trim().equals("")){		
				if(city.length()<2 || city.length()>100){
					errorVoList.add(ErrorRepository.cityFormatError);
				}
			}else{
				errorVoList.add(ErrorRepository.cityNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.cityNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.cityNotGiven);
		errorVoList.add(ErrorRepository.cityFormatError);
		return errorVoList;
	}

}
