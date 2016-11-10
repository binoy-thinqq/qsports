package com.thinqq.qsports.client.common.fieldvalidators;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.thinqq.qsports.shared.validation.IValidator;

public class DateFieldValidator extends AbstractInputFieldValidator{
	public static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	
	public DateFieldValidator(Widget inputWidet, IValidator validator,
			Image image) {
		super(inputWidet, validator, image);
	}

	public DateFieldValidator(Widget inputWidet, IValidator validator,
			Image image, String errorStyleName, String dialogBoxStyleName) {
		super(inputWidet, validator, image, errorStyleName, dialogBoxStyleName);
	}
	@Override
	protected String getStringValue() {
		Date date = ((DateBox)getWidget()).getValue();
		if(date==null){
			return "";
		}
		return dateTimeFormat.format(date);
	}
}
