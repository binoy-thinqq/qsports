package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;

import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class EmailPatternValidator implements Validator {
	
	EmailValidator  emailValidator = EmailValidator.getInstance();

	@Override
	public List<ErrorInfo> validate(Object validateObject, String fieldName, Annotation annotation) {
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		if (validateObject == null) {
			return errorInfoList;
		}
		boolean isValid = false;
		if (validateObject instanceof String) {
			isValid = emailValidator.isValid((String)validateObject);
		} 
		if (!isValid) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.EMAIL_INVALID);
			errorInfo.setErrorDesc(fieldName + " is not in correct expected email format");
			errorInfoList.add(errorInfo);
		}
		return errorInfoList;
	}

}
