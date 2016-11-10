package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.shared.cricket.FieldingPosition;

public class FieldingPositionValidator implements IValidator {
	private static FieldingPositionValidator validator = new FieldingPositionValidator();
	private FieldingPositionValidator(){}
	public static FieldingPositionValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			Integer f_pos = Integer.parseInt((String)value);
			boolean valid = false;
			for(FieldingPosition pos :FieldingPosition.values()){		
				if(pos.getId()==f_pos){
					valid = true;
					break;
				}
			}
			if(valid){
				//NO-OP
			}else{
				errorVoList.add(ErrorRepository.fieldingPositionNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.fieldingPositionNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.fieldingPositionNotGiven);
		return errorVoList;
	}


}
