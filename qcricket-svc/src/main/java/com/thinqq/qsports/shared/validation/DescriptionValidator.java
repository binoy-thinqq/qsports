package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class DescriptionValidator implements IValidator {
	private static DescriptionValidator validator = new DescriptionValidator();
	private DescriptionValidator(){}
	public static DescriptionValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String name = (String)value;
			if(!name.trim().equals("")){		
				if(name.length()>1000){
					errorVoList.add(ErrorRepository.descExceedsChars);
				}
			}
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.descExceedsChars);
		return errorVoList;
	}

}
