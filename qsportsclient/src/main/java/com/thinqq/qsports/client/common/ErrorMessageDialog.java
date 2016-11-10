package com.thinqq.qsports.client.common;

import java.util.List;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.thinqq.qsports.shared.validation.ErrorVo;

public class ErrorMessageDialog extends DialogBox {
	public ErrorMessageDialog(List<ErrorVo> errors, String styleName){
		setStyleName(styleName);
		setGlassEnabled(false);
		setAnimationEnabled(true);
		setModal(false);
		setAutoHideEnabled(true);
	    HorizontalPanel dialogContents = new HorizontalPanel();
	    for(ErrorVo error : errors){
	    	dialogContents.add(new Label(error.getErrorMessage()));
	    }
	    dialogContents.setVisible(true);
	    setWidget(dialogContents);
	}
	
}
