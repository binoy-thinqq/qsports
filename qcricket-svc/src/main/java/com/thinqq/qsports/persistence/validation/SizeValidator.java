package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.persistence.validation.annotation.Size;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class SizeValidator implements Validator {

	@Override
	public List<ErrorInfo> validate(Object validateObject, String fieldName,
			Annotation annotation) {
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		if (validateObject == null) {
			return errorInfoList;
		}
		Size size= (Size)annotation;
		int length = size.length();
		int minLength = size.minLength();
		if (validateObject.toString().length() > length) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.MAX_SIZE_EXCEEDED);
			errorInfo.setErrorDesc(fieldName + " has exceeded maximum allowed size of " + length);
			errorInfoList.add(errorInfo);
		}
		if (validateObject.toString().length() < minLength) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.MAX_SIZE_EXCEEDED);
			errorInfo.setErrorDesc(fieldName + " should be more than " + length + "in size");
			errorInfoList.add(errorInfo);
		}
		return errorInfoList;
	}

}
