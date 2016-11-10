package com.thinqq.qsports.client.teamprofile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.fieldvalidators.DateFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.ListBoxFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;
import com.thinqq.qsports.shared.validation.CityValidator;
import com.thinqq.qsports.shared.validation.CountryValidator;
import com.thinqq.qsports.shared.validation.DateValidator;
import com.thinqq.qsports.shared.validation.DescriptionValidator;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.NameValidator;
import com.thinqq.qsports.shared.validation.StateValidator;

public class EditTeamProfile extends PopupPanel implements ICleanable {
public 	interface Presenter{
	void onTeamSave(SaveTeamProfileRequestVo request);
}
	private Presenter listener;
	private static final Binder binder = GWT.create(Binder.class);
	private String teamProfileKey;
	interface Binder extends UiBinder<Widget, EditTeamProfile> {
	}
	@UiField Button btnProfileSave ;
	@UiField Button btnCancel ;

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
	
	@UiField DateBox doe;
	@UiField Image doeErrorMarker;
	@UiField FocusPanel doeFocus;
	DateFieldValidator doevalidator;
	
	@UiField TextArea taAbout;
	@UiField Image taAboutErrorMarker;
	TextFieldValidator taAboutvalidator;
	
	public static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	public static final CommonResources commonPics = GWT.create(CommonResources.class);
	
	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
	
	public EditTeamProfile() {
		setWidget(binder.createAndBindUi(this));
		setGlassEnabled(true);
		setAnimationEnabled(true);
		addActionHandlers();
		initialiseCountry();
		doe.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
		initialiseValidators();
	}
	public void hide(){
		super.hide();
	}
	

	private void initialiseValidators() {
		txtNamevalidator = new TextFieldValidator(txtName, NameValidator.getInstance(), nameErrorMarker);
		doevalidator = new DateFieldValidator(doe, DateValidator.getInstance(), doeErrorMarker);
		txtCityvalidator = new TextFieldValidator(txtCity, CityValidator.getInstance(), txtCityErrorMarker);
		txtStatevalidator = new TextFieldValidator(txtState, StateValidator.getInstance(), txtStateErrorMarker);
		listCountryvalidator = new ListBoxFieldValidator(listCountry, CountryValidator.getInstance(), listCountryErrorMarker);
		taAboutvalidator = new TextFieldValidator(taAbout, DescriptionValidator.getInstance(), taAboutErrorMarker);
	}
	private void addActionHandlers(){

		txtName.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
		doeFocus.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
		doe.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> changedValue) {
				errorVoList.addAll(doevalidator.valdiate());
				
			}
		});
		txtCity.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(doevalidator.valdiate());
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
		btnCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnProfileSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				errorVoList.clear();
				errorVoList.addAll(txtNamevalidator.valdiate());
				errorVoList.addAll(doevalidator.valdiate());
				errorVoList.addAll(txtCityvalidator.valdiate());
				errorVoList.addAll(txtStatevalidator.valdiate());
				if(errorVoList.size()>0){
					//Cannot Register - force user to correct errors
					return;
				}
				SaveTeamProfileRequestVo request = new SaveTeamProfileRequestVo();
				request.setName(txtName.getText());
				request.setCity(txtCity.getText());
				request.setState(txtState.getText());
				request.setCountry(listCountry.getValue(listCountry.getSelectedIndex()));
				request.setDateOfEstd(doe.getValue());
				request.setDescription(taAbout.getValue());
				if(teamProfileKey == null){
					request.setNewTeam(true);
				} else {
					request.setNewTeam(false);
				}
				listener.onTeamSave(request);
				hide();
			}
		});
	}
	private void initialiseCountry() {
		Map<String,String> countryMap = CountryList.getListCountry();
		for(String key:countryMap.keySet()){
			listCountry.addItem(countryMap.get(key), key);
		}

	}
	public void setListener(Presenter listener) {
		this.listener = listener;
	}
	final String getTeamProfileKey() {
		return teamProfileKey;
	}
	final void setTeamProfileKey(String teamProfileKey) {
		this.teamProfileKey = teamProfileKey;
	}

	public void setTeamDetails(TeamProfileResponseVo details){
		btnProfileSave.setText("Save Team");
		txtName.setText(details.getName());
		doe.setValue(details.getDateOfEstd());
		txtCity.setText(details.getCity());
		txtState.setText(details.getState());
		for(int i=0;i<listCountry.getItemCount();i++){
			if(details.getCountry()!=null && details.getCountry().equals(listCountry.getValue(i))){
				listCountry.setSelectedIndex(i);
				break;
			}
		}
	}
	@Override
	public void clean() {
		txtName.setText("");
		doe.setValue(null);
		txtCity.setText("");
		txtState.setText("");
		listCountry.setSelectedIndex(-1);
		teamProfileKey = null;
		btnProfileSave.setText("Create Team");
	}
}
