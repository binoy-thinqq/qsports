package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class SexValidator implements IValidator {
	private static SexValidator validator = new SexValidator();
	private SexValidator(){}
	public static SexValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String sex = (String)value;
			if(!sex.trim().equals("")){		

			}else{
				errorVoList.add(ErrorRepository.sexNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.sexNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.sexNotGiven);
		return errorVoList;
	}

}
