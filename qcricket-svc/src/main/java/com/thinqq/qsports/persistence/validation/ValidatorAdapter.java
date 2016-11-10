package com.thinqq.qsports.persistence.validation;

import java.util.List;

import com.thinqq.qsports.server.error.ErrorInfo;

public interface ValidatorAdapter<T extends Object> {
	
	List<ErrorInfo> validate(T validateObject);

}
