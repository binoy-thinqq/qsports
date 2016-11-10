package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class NameValidator implements IValidator {
	private static NameValidator validator = new NameValidator();
	private NameValidator(){}
	public static NameValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String name = (String)value;
			if(!name.trim().equals("")){		
				if(name.length()<2 || name.length()>100){
					errorVoList.add(ErrorRepository.nameFormatError);
				}
			}else{
				errorVoList.add(ErrorRepository.nameNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.nameNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.nameNotGiven);
		errorVoList.add(ErrorRepository.nameFormatError);
		return errorVoList;
	}

}
