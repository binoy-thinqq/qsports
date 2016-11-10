package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class SelectionValidator implements IValidator {

	private static SelectionValidator validator = new SelectionValidator();
	private SelectionValidator(){}
	public static SelectionValidator getInstance(){
		return validator;
	}
	
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.selectAValueError);
		return errorVoList;
	}

	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null){
			String country = (String)value;
			if(country.trim().equals("")){
				errorVoList.add(ErrorRepository.selectAValueError);
			}
		}else{
			errorVoList.add(ErrorRepository.selectAValueError);
		}
		return errorVoList;
	}

}
