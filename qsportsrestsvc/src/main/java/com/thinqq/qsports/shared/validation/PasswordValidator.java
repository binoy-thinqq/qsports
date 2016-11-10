package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements IValidator {
	
	private static PasswordValidator validator = new PasswordValidator();
	private PasswordValidator(){}
	public static PasswordValidator getInstance(){
		return validator;
	}
	public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,30}$";
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		errors.add(ErrorRepository.invalidPasswordFormat);
		errors.add(ErrorRepository.passwordNotGiven);
		return errors;
	}

	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		if(value!=null && value!=null ){
			String password = (String)value;
			if(!password.trim().equals("")){
				Pattern expression = Pattern.compile(PASSWORD_REGEX);
				Matcher m = expression.matcher(password.toString());
				if(!m.matches()){
					errors.add(ErrorRepository.invalidPasswordFormat);
				}
			}else{
				errors.add(ErrorRepository.passwordNotGiven);
			}
		}else{
			errors.add(ErrorRepository.passwordNotGiven);
		}
		return errors;
	}

}
