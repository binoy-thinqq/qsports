package com.thinqq.qsports.shared.validation;

import java.util.List;

public interface IValidator {
	List<ErrorVo> getAllReportedErrors();
	List<ErrorVo> validate(Object value);
}
