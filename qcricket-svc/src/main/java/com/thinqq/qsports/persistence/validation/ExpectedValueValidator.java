package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thinqq.qsports.persistence.validation.annotation.ExpectedValue;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;

public class ExpectedValueValidator implements Validator {

	@Override
	public List<ErrorInfo> validate(Object validateObject, String fieldName,
			Annotation annotation) {
		boolean isValid = false;
		List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();
		if (validateObject == null) {
			return errorInfoList;
		}
		ExpectedValue expectedValue = (ExpectedValue)annotation;
		if (validateObject instanceof String) {
				List<String> values = Arrays.asList(expectedValue.values());
				if (values != null && !values.isEmpty() && values.contains(validateObject)) {
					isValid = true;
				}
		}
		 
		if (!isValid) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.NOT_ALLOWED_VALUE);
			StringBuilder builder = new StringBuilder();
			for(String value : expectedValue.values()) {
				builder.append(value);
			}
			errorInfo.setErrorDesc("Allowed values for field " + fieldName + " are " + builder.toString());
			errorInfoList.add(errorInfo);
		}
		return errorInfoList;
	}

}
