package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.shared.cricket.BowlingStyle;

public class BowlingMethodValidator implements IValidator {
	private static BowlingMethodValidator validator = new BowlingMethodValidator();
	private BowlingMethodValidator(){}
	public static BowlingMethodValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			Integer b_style = Integer.parseInt((String)value);
			boolean valid = false;
			for(BowlingStyle style :BowlingStyle.values()){		
				if(style.getId()==b_style){
					valid = true;
					break;
				}
			}
			if(valid){
				//NO-OP
			}else{
				errorVoList.add(ErrorRepository.bowlingMethodNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.bowlingMethodNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.bowlingMethodNotGiven);
		return errorVoList;
	}


}
