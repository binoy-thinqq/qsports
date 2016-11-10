package com.thinqq.qsports.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessagePopUp extends PopupPanel {

	private static MessagePopUpUiBinder uiBinder = GWT
			.create(MessagePopUpUiBinder.class);

	interface MessagePopUpUiBinder extends UiBinder<Widget, MessagePopUp> {
	}

	public MessagePopUp() {
		setWidget(uiBinder.createAndBindUi(this));
		setModal(false);
		setGlassEnabled(false);
		setAnimationEnabled(true);
		//set
		setWidth("600px");
	}

	public FlowPanel getMessagePanel() {
		return messagePanel;
	}


	@UiField FlowPanel messagePanel;

}
