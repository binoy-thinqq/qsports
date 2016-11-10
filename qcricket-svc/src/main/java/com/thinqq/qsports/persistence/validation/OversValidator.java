package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class OversValidator implements Validator {

	@Override
	public List<ErrorInfo> validate(Object validateObject, String fieldName,
			Annotation annotation) {
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		if (validateObject == null) {
			return errorInfoList;
		}
		Double overs = (Double)validateObject;
		int balls = (int)(overs*10)%10;
		int oversInt = overs.intValue();
		if (balls >= 6) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.INVALID_NUMBER_OF_OVERS);
			errorInfo.setErrorDesc("Number of balls cannot exceed 5");
			errorInfoList.add(errorInfo);
		}
		if(oversInt > 500 || oversInt <0) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.INVALID_NUMBER_OF_OVERS);
			errorInfo.setErrorDesc("Number of overs should be between 0 and 500");
			errorInfoList.add(errorInfo);
		}
		return errorInfoList;
	}

}
