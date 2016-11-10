/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinqq.qsports.client.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.thinqq.qsports.client.common.CommonCell;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.client.message.MessageTypeEnum;
import com.thinqq.qsports.client.message.MessageVo;
import com.thinqq.qsports.shared.validation.EmailValidator;
import com.thinqq.qsports.shared.validation.ErrorRepository;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.PasswordValidator;

/**
 * Sample implementation of {@link LoginView}.
 */
public class LoginViewImpl extends Composite implements LoginView {

	interface Binder extends UiBinder<Widget, LoginViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	public static final ErrorVo invalidUserNamePassword = ErrorRepository.invalidUserNamePassword;
	public static final ErrorVo unableToProceed = ErrorRepository.unableToProceed;
	public static final ErrorVo accountLocked = ErrorRepository.accountLocked;
	
	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
	private ListDataProvider<ErrorVo> errorDataProvider = new ListDataProvider<ErrorVo>(errorVoList);
	private Presenter listener;
	
	public static final CommonResources commonPics = GWT.create(CommonResources.class);
	@UiField 
	Button btnSubmit;
	
	@UiField TextBox txtPassword;
	@UiField Image txtPasswordErrorMarker;
	TextFieldValidator txtPasswordvalidator;
	
	@UiField TextBox txtEmail; 
	@UiField Image txtEmailErrorMarker;
	TextFieldValidator txtEmailvalidator; 
	
	
	private static CommonCell commonCellTemplates = GWT.create(CommonCell.class);
	
	@UiField(provided=true) CellList<ErrorVo> errorList = new CellList<ErrorVo>(new AbstractCell<ErrorVo>(){
		@Override
		public void render(Context context, ErrorVo error, SafeHtmlBuilder sb) {
			sb.append(commonCellTemplates.error(30, "errorcell", error.getErrorMessage()));
		}
	});
	@UiField(provided=true) CellList<MessageVo> messageList = new CellList<MessageVo>(new AbstractCell<MessageVo>(){
		@Override
		public void render(Context context, MessageVo message, SafeHtmlBuilder sb) {
			String style = null;
			if(MessageTypeEnum.INFO.equals(message.getMessageType())){
				style = commonPics.getStyle().informationcell();
				sb.append(commonCellTemplates.message(30, style, message.getMessage()));
			}else if(MessageTypeEnum.WARNING.equals(message.getMessageType())) {
				style = commonPics.getStyle().warningcell();
				sb.append(commonCellTemplates.warning(30, style, message.getMessage()));
			}else if(MessageTypeEnum.ERROR.equals(message.getMessageType())) {
				style = commonPics.getStyle().errorcell();
				sb.append(commonCellTemplates.error(30, style, message.getMessage()));
			}else{
				style = commonPics.getStyle().informationcell();
				sb.append(commonCellTemplates.message(30, style, message.getMessage()));
			}

		}
	});
	private List<MessageVo> messageVoList = new ArrayList<MessageVo>();
	private ListDataProvider<MessageVo> messageDataProvider = new ListDataProvider<MessageVo>(messageVoList);
	private MessageVo confirmationMailSentVo = new MessageVo(1, MessageTypeEnum.INFO, "An email has been sent to you. You can start using ThinqQ Cricket once you confirm using the link given in your email", "", "");
	public LoginViewImpl() {
		initWidget(binder.createAndBindUi(this));
		commonPics.getStyle().ensureInjected();
		addActionHandlers();
		initialiseValidators();
		initialiseMessages();
		errorDataProvider.addDataDisplay(errorList);
	}



	private void initialiseValidators() {
		txtEmailvalidator = new TextFieldValidator(txtEmail, EmailValidator.getInstance(), txtEmailErrorMarker);
		txtPasswordvalidator = new TextFieldValidator(txtPassword, PasswordValidator.getInstance(), txtPasswordErrorMarker);
		
	}



	private void initialiseMessages() {
		messageDataProvider.addDataDisplay(messageList);
	}



	private void addActionHandlers() {
		txtEmail.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtEmailvalidator.valdiate());
			}
		});
		txtPassword.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtEmailvalidator.valdiate());
				
			}
		});
		txtPassword.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtPasswordvalidator.valdiate());	
			}
		});
		btnSubmit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				errorVoList.clear();
				errorVoList.addAll(txtEmailvalidator.valdiate());
				errorVoList.addAll(txtPasswordvalidator.valdiate());	
				if(errorVoList!=null && errorVoList.size()==0){
					listener.onSubmitButtonClick(txtEmail.getValue(), txtPassword.getText());
				}
				
			}
		});
		
	}
	private void refreshMessageList() {
		Collections.sort(messageVoList);
		messageDataProvider.setList(messageVoList);
	}
	private void refreshErrorList() {
		Collections.sort(errorVoList);
		errorDataProvider.setList(errorVoList);
	}
	public void showEmailPasswordMismatchError(){
		errorVoList.clear();
		errorVoList.add(invalidUserNamePassword);
		refreshErrorList();
	}
	
	public void showUnableToProceedError(){
		errorVoList.clear();
		errorVoList.add(unableToProceed);
		refreshErrorList();
	}
	
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showConfirmationEmailMessage() {
		messageVoList.add(confirmationMailSentVo);
		refreshMessageList();
	}



	@Override
	public void showUserDeactivatedError() {
		errorVoList.clear();
		errorVoList.add(accountLocked);
		refreshErrorList();
	}



	@Override
	public void clean() {
		//Nothing to clean
	}
}
