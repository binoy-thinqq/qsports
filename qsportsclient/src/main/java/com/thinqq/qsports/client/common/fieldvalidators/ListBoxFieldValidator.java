package com.thinqq.qsports.client.common.fieldvalidators;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.thinqq.qsports.shared.validation.IValidator;

public class ListBoxFieldValidator extends AbstractInputFieldValidator{

	public ListBoxFieldValidator(FocusWidget inputWidet, IValidator validator,
			Image image) {
		super(inputWidet, validator, image);
	}

	public ListBoxFieldValidator(FocusWidget inputWidet, IValidator validator,
			Image image, String errorStyleName, String dialogBoxStyleName) {
		super(inputWidet, validator, image, errorStyleName, dialogBoxStyleName);
	}

	@Override
	protected String getStringValue() {
		ListBox listBox = ((ListBox)getWidget());
		return listBox.getValue(listBox.getSelectedIndex());
	}
}
