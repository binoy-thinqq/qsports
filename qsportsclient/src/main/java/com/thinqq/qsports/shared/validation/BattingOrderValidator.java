package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.shared.cricket.BattingOrder;

public class BattingOrderValidator implements IValidator {
	private static BattingOrderValidator validator = new BattingOrderValidator();
	private BattingOrderValidator(){}
	public static BattingOrderValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		if(value!=null && value!=null){
			Integer b_order = Integer.parseInt((String)value);
			boolean valid = false;
			for(BattingOrder order :BattingOrder.values()){		
				if(order.getId()==b_order){
					valid = true;
					break;
				}
			}
			if(valid){
				//NO-OP
			}else{
				errorVoList.add(ErrorRepository.battingOrderNotGiven);
			}
		}else{
			errorVoList.add(ErrorRepository.battingOrderNotGiven);
		}
		return errorVoList;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
		errorVoList.add(ErrorRepository.battingOrderNotGiven);
		return errorVoList;
	}


}
