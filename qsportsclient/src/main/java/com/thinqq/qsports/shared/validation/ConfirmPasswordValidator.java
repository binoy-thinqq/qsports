package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPasswordValidator implements IValidator {
	
	private String password;

	private ConfirmPasswordValidator(String password){
		this.password = password;
	}
	public static ConfirmPasswordValidator getInstance(String password){
		return new ConfirmPasswordValidator(password);
	}
	public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,30}$";
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		errors.add(ErrorRepository.passwordMisMatch);
		errors.add(ErrorRepository.confirmPasswordNotGiven);
		return errors;
	}

	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		if(value!=null && value!=null ){
			String password = (String)value;
			if(!password.trim().equals("")){
				if(!password.equals(this.password)){
					errors.add(ErrorRepository.passwordMisMatch);
				}
			}else{
				errors.add(ErrorRepository.confirmPasswordNotGiven);
			}
		}else{
			errors.add(ErrorRepository.confirmPasswordNotGiven);
		}
		return errors;
	}

}
