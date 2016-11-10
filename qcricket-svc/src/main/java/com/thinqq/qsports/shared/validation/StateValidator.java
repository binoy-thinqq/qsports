package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class StateValidator implements IValidator {
	private static StateValidator validator = new StateValidator();
	private StateValidator(){}
	public static StateValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			String state = (String)value;
			if(!state.trim().equals("")){		
				if(state.length()<2 || state.length()>100){
					errorVoList.add(ErrorRepository.stateFormatError);
				}
			}else{
				errorVoList.add(ErrorRepository.stateNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.stateNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.stateNotGiven);
		errorVoList.add(ErrorRepository.stateFormatError);
		return errorVoList;
	}

}
