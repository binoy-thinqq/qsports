package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.shared.cricket.HandTypeEnum;

public class HandTypeValidator implements IValidator {
	private static HandTypeValidator validator = new HandTypeValidator();
	private HandTypeValidator(){}
	public static HandTypeValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			Integer handType = Integer.parseInt((String)value);
			boolean valid = false;
			for(HandTypeEnum hand :HandTypeEnum.values()){		
				if(hand.getId()==handType){
					valid = true;
					break;
				}
			}
			if(valid){
				//NO-OP
			}else{
				errorVoList.add(ErrorRepository.handTypeNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.handTypeNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.handTypeNotGiven);
		return errorVoList;
	}


}
