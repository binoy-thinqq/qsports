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
package com.thinqq.qsports.client.registration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.thinqq.qsports.client.common.CommonCell;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.fieldvalidators.DateFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.ListBoxFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.SexList;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.validation.CityValidator;
import com.thinqq.qsports.shared.validation.ConfirmPasswordValidator;
import com.thinqq.qsports.shared.validation.CountryValidator;
import com.thinqq.qsports.shared.validation.DateValidator;
import com.thinqq.qsports.shared.validation.EmailValidator;
import com.thinqq.qsports.shared.validation.ErrorRepository;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.NameValidator;
import com.thinqq.qsports.shared.validation.PasswordValidator;
import com.thinqq.qsports.shared.validation.SexValidator;
import com.thinqq.qsports.shared.validation.StateValidator;

/**
 * Sample implementation of {@link RegistrationView}.
 */
public class RegistrationViewImpl extends Composite implements RegistrationView {
	public static final ErrorVo unableToRegisterError = ErrorRepository.unableToRegisterError;
	public static final ErrorVo emailAlreadyRegistered = ErrorRepository.emailAlreadyRegistered;
	public static final CommonResources commonPics = GWT.create(CommonResources.class);
	public static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	


	
	@UiField 
	Button btnRegister ;
	
	@UiField TextBox txtPassword;
	@UiField Image txtPasswordErrorMarker;
	TextFieldValidator txtPasswordvalidator;
	
	@UiField TextBox txtEmail; 
	@UiField Image txtEmailErrorMarker;
	TextFieldValidator txtEmailvalidator;
	
	@UiField TextBox txtConfirmPassword;
	@UiField Image txtConfirmPasswordErrorMarker;
	TextFieldValidator txtConfirmPasswordvalidator;
	
	@UiField ListBox listSex;
	@UiField Image listSexErrorMarker;
	ListBoxFieldValidator listSexvalidator;
	
	@UiField DateBox dob;
	@UiField Image dobErrorMarker;
	DateFieldValidator dobvalidator;
	
	@UiField TextBox txtCity;
	@UiField Image txtCityErrorMarker;
	TextFieldValidator txtCityvalidator;
	
	@UiField TextBox txtState;
	@UiField Image txtStateErrorMarker;
	TextFieldValidator txtStatevalidator;
	
	@UiField ListBox listCountry;
	@UiField Image listCountryErrorMarker;
	ListBoxFieldValidator listCountryvalidator;
		
	@UiField TextBox txtName; 
	@UiField Image nameErrorMarker;
	TextFieldValidator txtNamevalidator;
	
	private static CommonCell commonCellTemplates = GWT.create(CommonCell.class);
	@UiField(provided=true) CellList<ErrorVo> errorList = new CellList<ErrorVo>(new AbstractCell<ErrorVo>(){
		@Override
		public void render(Context context, ErrorVo error, SafeHtmlBuilder sb) {
			sb.append(commonCellTemplates.error(30, "errorcell", error.getErrorMessage()));
		}
	});

	
	DialogBox registeringMessage = new DialogBox(false,true);
	
	interface Binder extends UiBinder<Widget, RegistrationViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
	private ListDataProvider<ErrorVo> errorDataProvider = new ListDataProvider<ErrorVo>(errorVoList);

	public RegistrationViewImpl() {
		initWidget(binder.createAndBindUi(this));
		//initialiseErrorMessages();
		initialiseValidators();
		initaliseCountry();
		initialiseSex();
		initialiseDateFormat();
		initialiseDialgoBoxes();
		addActionHandlers();
		errorDataProvider.addDataDisplay(errorList);
		
	}

	private void initialiseValidators() {
		txtNamevalidator = new TextFieldValidator(txtName, NameValidator.getInstance(), nameErrorMarker);
		txtEmailvalidator = new TextFieldValidator(txtEmail, EmailValidator.getInstance(), txtEmailErrorMarker);
		txtPasswordvalidator = new TextFieldValidator(txtPassword, PasswordValidator.getInstance(), txtPasswordErrorMarker);
		txtConfirmPasswordvalidator = new TextFieldValidator(txtConfirmPassword, ConfirmPasswordValidator.getInstance(""), txtConfirmPasswordErrorMarker);
		listSexvalidator = new ListBoxFieldValidator(listSex, SexValidator.getInstance(), listSexErrorMarker);
		dobvalidator = new DateFieldValidator(dob, DateValidator.getInstance(), dobErrorMarker);
		txtCityvalidator = new TextFieldValidator(txtCity, CityValidator.getInstance(), txtCityErrorMarker);
		txtStatevalidator = new TextFieldValidator(txtState, StateValidator.getInstance(), txtStateErrorMarker);
		listCountryvalidator = new ListBoxFieldValidator(listCountry, CountryValidator.getInstance(), listCountryErrorMarker);
	
	}

	private void initialiseDateFormat() {
		dob.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
	}

	private void initialiseDialgoBoxes() {
		registeringMessage.setGlassEnabled(true);
		registeringMessage.setAnimationEnabled(true);
	    HorizontalPanel dialogContents = new HorizontalPanel();
	    dialogContents.add(new Image(commonPics.loadingImage()));
	    dialogContents.add(new Label("Registering..."));
	    dialogContents.setVisible(true);
	    registeringMessage.setWidget(dialogContents);

	}
	private void initialiseSex(){
		listSex.addItem("-Select-");
		Map<String,String> sexMap = SexList.getListSex();
		for(String key:sexMap.keySet()){
			listSex.addItem(key, sexMap.get(key));
		}
	}

	private void initaliseCountry() {
		listCountry.addItem("-Select-");
		Map<String,String> countryMap = CountryList.getListCountry();
		for(String key:countryMap.keySet()){
			listCountry.addItem(key, countryMap.get(key));
		}

	}
	public void initialiseFields(){
		errorVoList.clear();
		refreshErrorList();
		txtEmail.setText("");
		txtPassword.setText("");
		txtConfirmPassword.setText("");
		txtCity.setText("");
		txtState.setText("");
		listCountry.setSelectedIndex(-1);
		listSex.setSelectedIndex(-1);
		dob.setValue(null);
	}

	private void addActionHandlers() {
		txtName.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
		txtEmail.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
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
		txtConfirmPassword.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtPasswordvalidator.valdiate());
			}
		});
		txtConfirmPassword.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				txtConfirmPasswordvalidator = new TextFieldValidator(txtConfirmPassword, ConfirmPasswordValidator.getInstance(txtPassword.getValue()), txtConfirmPasswordErrorMarker);
				errorVoList.addAll(txtConfirmPasswordvalidator.valdiate());
			}
		});
		listSex.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				txtConfirmPasswordvalidator = new TextFieldValidator(txtConfirmPassword, ConfirmPasswordValidator.getInstance(txtPassword.getValue()), txtConfirmPasswordErrorMarker);
				errorVoList.addAll(txtConfirmPasswordvalidator.valdiate());
			}
		});
		listSex.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent changedValue) {
				errorVoList.addAll(listSexvalidator.valdiate());
			}
		});
		dob.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> changedValue) {
				errorVoList.addAll(dobvalidator.valdiate());
				
			}
		});
		txtCity.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(dobvalidator.valdiate());
			}
		});
		txtCity.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtCityvalidator.valdiate());
			}
		});
		txtState.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtCityvalidator.valdiate());
			}
		});
		txtState.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtStatevalidator.valdiate());
			}
		});
		listCountry.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtStatevalidator.valdiate());
			}
		});
		listCountry.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent changedValue) {
				errorVoList.addAll(listCountryvalidator.valdiate());
			}
		});
		btnRegister.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				errorVoList.remove(unableToRegisterError);
				errorVoList.clear();
				errorVoList.addAll(txtNamevalidator.valdiate());
				errorVoList.addAll(txtEmailvalidator.valdiate());
				errorVoList.addAll(txtPasswordvalidator.valdiate());
				txtConfirmPasswordvalidator = new TextFieldValidator(txtConfirmPassword, ConfirmPasswordValidator.getInstance(txtPassword.getValue()), txtConfirmPasswordErrorMarker);
				errorVoList.addAll(txtConfirmPasswordvalidator.valdiate());
				errorVoList.addAll(listSexvalidator.valdiate());
				errorVoList.addAll(dobvalidator.valdiate());
				errorVoList.addAll(txtCityvalidator.valdiate());
				errorVoList.addAll(txtStatevalidator.valdiate());
				errorVoList.addAll(listCountryvalidator.valdiate());
				if(errorVoList.size()>0){
					//Cannot Register - force user to correct errors
					return;
				}

				RegistrationRequestVo registrationRequest = new RegistrationRequestVo();
				registrationRequest.setName(txtName.getValue());
				registrationRequest.setEmail(txtEmail.getValue());
				registrationRequest.setPassword(txtPassword.getValue());
				registrationRequest.setDob(dob.getValue());
				registrationRequest.setSex(listSex.getValue(listSex.getSelectedIndex()));
				registrationRequest.setCity(txtCity.getValue());
				registrationRequest.setState(txtState.getValue());
				registrationRequest.setIsoCountryCode(listCountry.getValue(listCountry.getSelectedIndex()));
				listener.onRegisterSubmit(registrationRequest);
			}
		});
	}
	private void refreshErrorList() {
		Collections.sort(errorVoList);
		errorDataProvider.setList(errorVoList);
	}
	
	@Override
	public void setName(String name) {

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}


	void onButtonClick(ClickEvent event) {
		Place newPlace = null;
		// TODO
		listener.goTo(newPlace);
	}
	

	@UiHandler("btnRegister")
	void onBtnRegisterClick(ClickEvent event) {
	}

	@Override
	public void showEmailAlreadyExistsError() {
		errorVoList.add(emailAlreadyRegistered);
		refreshErrorList();
	}

	@Override
	public void showUnableToRegisterError() {
		errorVoList.add(unableToRegisterError);
		refreshErrorList();		
	}

	@Override
	public void showRegisteringMessage() {
		registeringMessage.center();
		registeringMessage.show();
	}

	@Override
	public void hideRegisteringMessage() {
		registeringMessage.hide();
	}

	@Override
	public void clean() {
		txtEmail.setText("");
		txtPassword.setText("");
		txtConfirmPassword.setText("");
		txtCity.setText("");
		txtState.setText("");
		listCountry.setSelectedIndex(-1);
		listSex.setSelectedIndex(-1);
		dob.setValue(null);
	}
}
