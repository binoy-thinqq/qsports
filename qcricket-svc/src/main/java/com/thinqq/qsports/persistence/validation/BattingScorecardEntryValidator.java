package com.thinqq.qsports.persistence.validation;

import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.persistence.dto.BattingScorecardEntryVo;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.shared.OutTypeEnum;

public class BattingScorecardEntryValidator implements ValidatorAdapter<BattingScorecardEntryVo> {

	@Override
	public List<ErrorInfo> validate(BattingScorecardEntryVo validateObject) {
		
		List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
			
		if(validateObject.getOutType() != null){
			if(OutTypeEnum.BOWLED.getOutTypeId() == validateObject.getOutType()
					|| OutTypeEnum.LEG_BEFORE_WICKET.getOutTypeId() == validateObject.getOutType()
					|| OutTypeEnum.HIT_WICKET.getOutTypeId() == validateObject.getOutType()){
				if(validateObject.getBowlerFielder() != null 
						&& validateObject.getFielder() == null){
					errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_OUT_DECLARATION, "For the chosen out type, Bowler name should be declared, "
																							+ "No fielder should be declared"));
				}
			} else if(OutTypeEnum.TIMED_OUT.getOutTypeId() == validateObject.getOutType()
						|| OutTypeEnum.HANDLED_THE_BALL.getOutTypeId() == validateObject.getOutType()
						|| OutTypeEnum.HIT_THE_BALL_TWICE.getOutTypeId() == validateObject.getOutType()
						|| OutTypeEnum.OBSTRUCTING_THE_FIELD.getOutTypeId() == validateObject.getOutType()
						|| OutTypeEnum.RETIRED_HURT.getOutTypeId() == validateObject.getOutType()){
				if(validateObject.getBowlerFielder() == null 
						&& validateObject.getFielder() == null){
					errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_OUT_DECLARATION, "For the chosen out type, Neither bowler nor fielder should be declared"));
				}
			} else if(OutTypeEnum.CAUGHT.getOutTypeId() == validateObject.getOutType()
					|| OutTypeEnum.STUMPTED.getOutTypeId() == validateObject.getOutType()){
				if(validateObject.getBowlerFielder() != null 
					&& validateObject.getFielder() != null){
					errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_OUT_DECLARATION, "For the chosen out type, Both bowler and fielder should be declared"));
				}
			} else if(OutTypeEnum.RUN_OUT.getOutTypeId() == validateObject.getOutType()){
				if(validateObject.getBowlerFielder() != null 
					&& validateObject.getFielder() != null){
					errorInfos.add(new ErrorInfo(ErrorConstants.INVALID_OUT_DECLARATION, "For the chosen out type, No one or two fielder(s) should be declared"));
				}
			}
		}
			
	return errorInfos;
	}

}
