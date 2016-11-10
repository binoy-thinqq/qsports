package com.thinqq.qsports.shared.validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateValidator implements IValidator {
	public static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	
	private static DateValidator validator = new DateValidator();
	private DateValidator(){}
	public static DateValidator getInstance(){
		return validator;
	}
	@Override
	public List<ErrorVo> getAllReportedErrors() {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		errors.add(ErrorRepository.dobFormatError);
		errors.add(ErrorRepository.dobNotGiven);
		return errors;
	}

	@Override
	public List<ErrorVo> validate(Object value) {
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		if(value!=null && value!=null ){
			String date = (String)value;
			if(!date.trim().equals("")){
				try{
					Date dob = dateTimeFormat.parse(date);
					if(dob==null){
						errors.add(ErrorRepository.dobFormatError);
					}
				}catch(IllegalArgumentException iae){
					errors.add(ErrorRepository.dobFormatError);
				}
			}else{
				errors.add(ErrorRepository.dobNotGiven);
			}
		}else{
			errors.add(ErrorRepository.dobNotGiven);
		}
		return errors;
	}

}
