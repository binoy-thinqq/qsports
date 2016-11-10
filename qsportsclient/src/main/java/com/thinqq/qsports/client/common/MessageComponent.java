/**
 * 
 */
package com.thinqq.qsports.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author subramaniam
 *
 */
public class MessageComponent extends Composite implements HasText {
	public static interface Presenter{
		void onHide(MessageComponent message);
	}

	private static MessageComponentUiBinder uiBinder = GWT
			.create(MessageComponentUiBinder.class);
	private Presenter presenter;
	interface MessageComponentUiBinder extends
			UiBinder<Widget, MessageComponent> {
	}


	public MessageComponent(Presenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button close;
	
	@UiField
	Label message;

	@UiHandler("close")
	void onClick(ClickEvent e) {
		hide();
	}

	public void hide() {
		setVisible(false);
		removeFromParent();
		if(presenter!=null){
			presenter.onHide(this);
		}
	}

	public void setText(String text) {
		message.setText(text);
	}

	/**
	 * Gets invoked when the default constructor is called
	 * and a string is provided in the ui.xml file.
	 */
	public String getText() {
		return message.getText();
	}

}
