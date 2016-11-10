package com.thinqq.qsports.client.common.fieldvalidators;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.thinqq.qsports.shared.validation.IValidator;

public class TextFieldValidator extends AbstractInputFieldValidator{

	public TextFieldValidator(FocusWidget inputWidet, IValidator validator,
			Image image) {
		super(inputWidet, validator, image);
	}

	public TextFieldValidator(FocusWidget inputWidet, IValidator validator,
			Image image, String errorStyleName, String dialogBoxStyleName) {
		super(inputWidet, validator, image, errorStyleName, dialogBoxStyleName);
	}
	@Override
	protected String getStringValue() {
		return ((TextBoxBase)getWidget()).getValue();
	}
}
