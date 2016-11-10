package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.regexp.shared.RegExp;

public class EmailValidator implements IValidator {
	
	private static EmailValidator validator = new EmailValidator();
	private EmailValidator(){}
	public static EmailValidator getInstance(){
		return validator;
	}
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		errors.add(ErrorRepository.invalidEmailFormat);
		errors.add(ErrorRepository.emailNotGiven);
		return errors;
	}

	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		if(value!=null && value!=null ){
			String email = (String)value;
			if(!email.trim().equals("")){
				RegExp expression = RegExp.compile(EMAIL_REGEX);
				if(!expression.test(email.toString())){
					errors.add(ErrorRepository.invalidEmailFormat);
				}
			}else{
				errors.add(ErrorRepository.emailNotGiven);
			}
		}else{
			errors.add(ErrorRepository.emailNotGiven);
		}
		return errors;
	}

}
