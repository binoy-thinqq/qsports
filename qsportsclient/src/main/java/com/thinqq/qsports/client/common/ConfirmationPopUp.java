package com.thinqq.qsports.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


public class ConfirmationPopUp extends PopupPanel {
	@UiField
	Label title;
	@UiField
	Label message;
	@UiField
	Button ok;
	@UiField
	Button cancel;
	interface Binder extends UiBinder<Widget, ConfirmationPopUp> {
	}
	private static final Binder binder = GWT.create(Binder.class);
	public ConfirmationPopUp(String caption, String message, ClickHandler okButtonHandler) {
		super(false, true);
		setWidget(binder.createAndBindUi(this));
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setPixelSize(400, 100);
		title.setText(caption);
		this.message.setText(message);
		if(okButtonHandler != null){
			ok.addClickHandler(okButtonHandler);
		}
		ClickHandler hidePopUp = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		};
		ok.addClickHandler(hidePopUp);
		cancel.addClickHandler(hidePopUp);
	}

}
