package com.thinqq.qsports.client.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.thinqq.qsports.client.Genie;

public class MessageUtil {
	public static final int SUCCESS=0;
	public static final int FAILURE=1;
	public static final int WARNING=2;
	public static final int MESSAGE=3;
	
	private static FlowPanel messagePanel;
	private static MessagePopUp messagePopupPanel;
	private static List<MessageComponent> unUsedMessages = new ArrayList<MessageComponent>(5);
	private static List<MessageComponent> messagesInUse = new ArrayList<MessageComponent>(5);
	private static CommonResources resource = GWT.create(CommonResources.class);
	private static CommonResources.CommonCss style = resource.getStyle();
	private static MessageComponent.Presenter presenter;
	
	private static int openCount = 0;
	
	public static void initialise(){
		messagePopupPanel = Genie.getClientfactory().getMessagePopUp();
		messagePanel = messagePopupPanel.getMessagePanel();
		presenter = new MessageComponent.Presenter() {
			
			@Override
			public void onHide(MessageComponent message) {
				messagesInUse.remove(message);
				unUsedMessages.add(message);
				if(messagePanel.getWidgetCount()<=0){
					messagePanel.setVisible(false);
					messagePanel.clear();
					messagePopupPanel.hide();
				}
			}
		};
		unUsedMessages.add(new MessageComponent(presenter));
		unUsedMessages.add(new MessageComponent(presenter));
		unUsedMessages.add(new MessageComponent(presenter));
		unUsedMessages.add(new MessageComponent(presenter));
		unUsedMessages.add(new MessageComponent(presenter));
	}
	
	public static void showMessage(int messageType, String text){
		
		if(unUsedMessages.isEmpty()){
			recoverMessage();
		}
		if(unUsedMessages.isEmpty()){
			unUsedMessages.add(new MessageComponent(presenter));
		}
		MessageComponent message = unUsedMessages.get(0);
		message.setText(text);
		if(messageType == 0){
			message.setStylePrimaryName(style.successMessage());
		}else if(messageType == 1){
			message.setStylePrimaryName(style.failureMessage());
		}else if(messageType == 2){
			message.setStylePrimaryName(style.warningMessage());
		}else {
			message.setStylePrimaryName(style.message());
		}
		message.setVisible(true);
		messagePanel.add(message);
		openCount++;
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				openCount--;
				if(openCount<=0){
					messagePanel.setVisible(false);
					messagePanel.clear();
					messagePopupPanel.hide();
				}
			}
		};
		timer.schedule(3000);
		messagesInUse.add(unUsedMessages.remove(0));
		if(!messagePanel.isVisible() || !messagePopupPanel.isShowing()){
			messagePanel.setVisible(true);
			messagePopupPanel.show();
			messagePopupPanel.setPopupPosition(Window.getScrollLeft()+200, Window.getScrollTop()+20);
			
		}
	}

	private static void recoverMessage() {
		if(messagesInUse.isEmpty()){
			//NO-OP
		}else{
			MessageComponent message = messagesInUse.get(0);
			message.hide();
			unUsedMessages.add(messagesInUse.remove(0));
		}
	}

	
	
}
