package com.thinqq.qsports.persistence.validation;

import java.lang.annotation.Annotation;
import java.util.List;

import com.thinqq.qsports.server.error.ErrorInfo;

public interface Validator {
	
	List<ErrorInfo> validate(Object validateObject, String fieldName, Annotation annotation);

}
