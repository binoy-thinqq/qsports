package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class NotNullValidator implements Validator {

	@Override
	public List<ErrorInfo> validate(Object validateObject, String fieldName, Annotation annotation) {
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		if (validateObject == null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.NOT_NULL);
			errorInfo.setErrorDesc(fieldName + " value cannot be null");
			errorInfoList.add(errorInfo);
		} 
		
		if (validateObject != null && validateObject instanceof String && ((String) validateObject).isEmpty()) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.NOT_NULL);
			errorInfo.setErrorDesc(fieldName + " value cannot be null");
			errorInfoList.add(errorInfo);
		}
		return errorInfoList;
	}

}
