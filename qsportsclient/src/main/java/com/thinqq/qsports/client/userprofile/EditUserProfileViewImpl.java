package com.thinqq.qsports.client.userprofile;

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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.thinqq.qsports.client.common.CommonCell;
import com.thinqq.qsports.client.common.fieldvalidators.DateFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.ListBoxFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.client.userprofile.UserProfileView.Presenter;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.SexList;
import com.thinqq.qsports.shared.TimeZoneOffset;
import com.thinqq.qsports.shared.TimeZoneOffset.TimeZoneInfo;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.FieldingPosition;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;
import com.thinqq.qsports.shared.validation.BattingOrderValidator;
import com.thinqq.qsports.shared.validation.BowlingMethodValidator;
import com.thinqq.qsports.shared.validation.CityValidator;
import com.thinqq.qsports.shared.validation.CountryValidator;
import com.thinqq.qsports.shared.validation.DateValidator;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.FieldingPositionValidator;
import com.thinqq.qsports.shared.validation.HandTypeValidator;
import com.thinqq.qsports.shared.validation.NameValidator;
import com.thinqq.qsports.shared.validation.SexValidator;
import com.thinqq.qsports.shared.validation.StateValidator;

public class EditUserProfileViewImpl extends PopupPanel implements EditUserProfileView{


	private static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");


	@UiField Button btnProfileSave ;
	@UiField Button btnCancel ;

	@UiField ListBox listSex;
	@UiField Image listSexErrorMarker;
	ListBoxFieldValidator listSexvalidator;
	
	@UiField DateBox dob;
	@UiField Image dobErrorMarker;
	@UiField FocusPanel dobFocus;
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
	
	@UiField ListBox listTimeZone;
	@UiField Image listTimeZoneErrorMarker;
	ListBoxFieldValidator listTimeZonevalidator;
		
	@UiField TextBox txtName; 
	@UiField Image nameErrorMarker;
	TextFieldValidator txtNamevalidator;
	
	@UiField Label lblEmail; 
	
	@UiField ListBox listBattingHand;
	@UiField Image battingHandErrorMarker;
	ListBoxFieldValidator listBattingHandvalidator;
	
	@UiField ListBox listBattingStyle;
	@UiField Image listBattingStyleErrorMarker;
	ListBoxFieldValidator listBattingStylevalidator;
	
	@UiField ListBox listBowlingHand;
	@UiField Image bowlingHandErrorMarker;
	ListBoxFieldValidator listBowlingHandvalidator;
	
	@UiField ListBox listBowlingStyle;
	@UiField Image listBowlingStyleErrorMarker;
	ListBoxFieldValidator listBowlingStylevalidator;
	
	@UiField ListBox listFieldPosition;
	@UiField Image listFieldPositionErrorMarker;
	ListBoxFieldValidator listFieldPositionvalidator;
	
	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
	private ListDataProvider<ErrorVo> errorDataProvider = new ListDataProvider<ErrorVo>(errorVoList);

	interface Binder extends UiBinder<Widget, EditUserProfileViewImpl> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private Presenter listener;

	private static CommonCell commonCellTemplates = GWT.create(CommonCell.class);
	@UiField(provided=true) CellList<ErrorVo> errorList = new CellList<ErrorVo>(new AbstractCell<ErrorVo>(){
		@Override
		public void render(Context context, ErrorVo error, SafeHtmlBuilder sb) {
			sb.append(commonCellTemplates.error(30, "errorcell", error.getErrorMessage()));
		}
	});

	public EditUserProfileViewImpl() {
		setWidget(binder.createAndBindUi(this));
		setModal(true);
		setGlassEnabled(true);
		setAnimationEnabled(true);
		initialiseValidators();
		initaliseCountry();
		initialiseSex();
		initialiseCricketProfileDropdowns();
		initialiseDateFormat();
		addActionHandlers();
	}

	private void initialiseCricketProfileDropdowns() {
		for(HandTypeEnum hand: HandTypeEnum.values()){
			listBattingHand.addItem(hand.getScreenName(),hand.getId().toString());
			listBowlingHand.addItem(hand.getScreenName(),hand.getId().toString());
		}
		for(BattingOrder order : BattingOrder.values()){
			listBattingStyle.addItem(order.getScreenName(),order.getId().toString());
		}
		for(BowlingStyle style : BowlingStyle.values()){
			listBowlingStyle.addItem(style.getScreenName(),style.getId().toString());
		}
		for(FieldingPosition pos : FieldingPosition.values()){
			listFieldPosition.addItem(pos.getScreenName(),pos.getId().toString());
		}
		
	}

	private void initialiseValidators() {
		txtNamevalidator = new TextFieldValidator(txtName, NameValidator.getInstance(), nameErrorMarker);
		listSexvalidator = new ListBoxFieldValidator(listSex, SexValidator.getInstance(), listSexErrorMarker);
		dobvalidator = new DateFieldValidator(dob, DateValidator.getInstance(), dobErrorMarker);
		txtCityvalidator = new TextFieldValidator(txtCity, CityValidator.getInstance(), txtCityErrorMarker);
		txtStatevalidator = new TextFieldValidator(txtState, StateValidator.getInstance(), txtStateErrorMarker);
		listCountryvalidator = new ListBoxFieldValidator(listCountry, CountryValidator.getInstance(), listCountryErrorMarker);
		listBattingHandvalidator = new ListBoxFieldValidator(listBattingHand, HandTypeValidator.getInstance(), battingHandErrorMarker);
		listBattingStylevalidator = new ListBoxFieldValidator(listBattingStyle, BattingOrderValidator.getInstance(), listBattingStyleErrorMarker);
		listBowlingHandvalidator = new ListBoxFieldValidator(listBowlingHand, HandTypeValidator.getInstance(), bowlingHandErrorMarker);
		listBowlingStylevalidator = new ListBoxFieldValidator(listBowlingStyle, BowlingMethodValidator.getInstance(), listBowlingStyleErrorMarker);
		listFieldPositionvalidator = new ListBoxFieldValidator(listFieldPosition, FieldingPositionValidator.getInstance(), listFieldPositionErrorMarker);
	}


	private void initialiseDateFormat() {
		dob.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
	}

	
	private void initialiseSex(){
		Map<String,String> sexMap = SexList.getListSex();
		for(String key:sexMap.keySet()){
			listSex.addItem(key, sexMap.get(key));
		}
	}

	private void initaliseCountry() {
		Map<String,String> countryMap = CountryList.getListCountry();
		for(String key:countryMap.keySet()){
			listCountry.addItem(countryMap.get(key),key);
		}

	}
	public void initialiseFields(){
		errorVoList.clear();
		refreshErrorList();
		txtName.setText("");
		lblEmail.setText("");
		txtCity.setText("");
		txtState.setText("");
		listCountry.setSelectedIndex(-1);
		listSex.setSelectedIndex(-1);
		listTimeZone.setSelectedIndex(-1);
		dob.setValue(null);
	}


	private void addActionHandlers() {
		txtName.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
		listSex.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
				errorVoList.addAll(txtNamevalidator.valdiate());
			}
		});
		listSex.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent changedValue) {
				errorVoList.addAll(listSexvalidator.valdiate());
			}
		});
		dobFocus.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent arg0) {
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
				reloadTimeZoneDropdown();
			}

			
		});
		
listBattingHand.addFocusHandler(new FocusHandler() {
	
	@Override
	public void onFocus(FocusEvent arg0) {
		errorVoList.addAll(listCountryvalidator.valdiate());
	}
});
listBattingHand.addChangeHandler(new ChangeHandler() {
	
	@Override
	public void onChange(ChangeEvent arg0) {
		errorVoList.addAll(listBattingHandvalidator.valdiate());
		
	}
});
listBattingStyle.addFocusHandler(new FocusHandler() {
	
	@Override
	public void onFocus(FocusEvent arg0) {
		errorVoList.addAll(listBattingHandvalidator.valdiate());
	}
});
listBattingStyle.addChangeHandler(new ChangeHandler() {
	
	@Override
	public void onChange(ChangeEvent arg0) {
		errorVoList.addAll(listBattingStylevalidator.valdiate());
		
	}
});

listBowlingHand.addFocusHandler(new FocusHandler() {
	
	@Override
	public void onFocus(FocusEvent arg0) {
		errorVoList.addAll(listBattingStylevalidator.valdiate());
	}
});
listBowlingHand.addChangeHandler(new ChangeHandler() {
	
	@Override
	public void onChange(ChangeEvent arg0) {
		errorVoList.addAll(listBowlingHandvalidator.valdiate());
		
	}
});
listBowlingStyle.addFocusHandler(new FocusHandler() {
	
	@Override
	public void onFocus(FocusEvent arg0) {
		errorVoList.addAll(listBowlingHandvalidator.valdiate());
	}
});
listBowlingStyle.addChangeHandler(new ChangeHandler() {
	
	@Override
	public void onChange(ChangeEvent arg0) {
		errorVoList.addAll(listBowlingStylevalidator.valdiate());
		
	}
});
listFieldPosition.addFocusHandler(new FocusHandler() {
	
	@Override
	public void onFocus(FocusEvent arg0) {
		errorVoList.addAll(listBowlingStylevalidator.valdiate());
	}
});
listFieldPosition.addChangeHandler(new ChangeHandler() {
	
	@Override
	public void onChange(ChangeEvent arg0) {
		errorVoList.addAll(listFieldPositionvalidator.valdiate());
		
	}
});
		
;
btnCancel.addClickHandler(new ClickHandler() {
	@Override
	public void onClick(ClickEvent arg0) {
		hide();
		
	}
});
btnProfileSave.addClickHandler(new ClickHandler() {
	
	@Override
	public void onClick(ClickEvent arg0) {
		errorVoList.clear();
		errorVoList.addAll(txtNamevalidator.valdiate());
		errorVoList.addAll(listSexvalidator.valdiate());
		errorVoList.addAll(dobvalidator.valdiate());
		errorVoList.addAll(txtCityvalidator.valdiate());
		errorVoList.addAll(txtStatevalidator.valdiate());
		errorVoList.addAll(listCountryvalidator.valdiate());
		errorVoList.addAll(listBattingHandvalidator.valdiate());
		errorVoList.addAll(listBattingStylevalidator.valdiate());
		errorVoList.addAll(listBowlingHandvalidator.valdiate());
		errorVoList.addAll(listBowlingStylevalidator.valdiate());
		errorVoList.addAll(listFieldPositionvalidator.valdiate());
		if(errorVoList.size()>0){
			//Cannot Register - force user to correct errors
			return;
		}
		SaveUserProfileRequestVo request = new SaveUserProfileRequestVo();
		request.setName(txtName.getText());
		request.setSex(listSex.getValue(listSex.getSelectedIndex()));
		request.setDob(dob.getValue());
		request.setCity(txtCity.getText());
		request.setState(txtState.getText());
		request.setCountry(listCountry.getValue(listCountry.getSelectedIndex()));
		request.setBattingHandType(Integer.parseInt(listBattingHand.getValue(listBattingHand.getSelectedIndex())));
		request.setBattingOrder(Integer.parseInt(listBattingStyle.getValue(listBattingStyle.getSelectedIndex())));
		request.setBowlingHandType(Integer.parseInt(listBowlingHand.getValue(listBowlingHand.getSelectedIndex())));
		request.setBowlingMethod(Integer.parseInt(listBowlingStyle.getValue(listBowlingStyle.getSelectedIndex())));
		request.setFieldPosition(Integer.parseInt(listFieldPosition.getValue(listFieldPosition.getSelectedIndex())));
		request.setTimeZoneStr(listTimeZone.getValue(listTimeZone.getSelectedIndex()));
		listener.onEditProfileSave(request);
		
	}
});
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}


	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub

	}

	private void reloadTimeZoneDropdown() {
		listTimeZone.clear();
		if(listCountry.getSelectedIndex()>=0){
			List<TimeZoneInfo> list = TimeZoneOffset.getTimeZoneList(listCountry.getValue(listCountry.getSelectedIndex()));
			for(TimeZoneInfo timeZone: list){
				listTimeZone.addItem(timeZone.getZoneId());
			}
		}

	}
	
	private void refreshErrorList() {
		Collections.sort(errorVoList);
		errorDataProvider.setList(errorVoList);
	}

	@Override
	public void populatePersonalDetails(
			UserProfileResponseVo userPersonalDetails) {
		//userId = userPersonalDetails.getSignedInUserId();
		txtName.setText(userPersonalDetails.getName());
		lblEmail.setText(userPersonalDetails.getEmail());
		txtCity.setText(userPersonalDetails.getCity());
		txtState.setText(userPersonalDetails.getState());
		for(int i=0;i<listCountry.getItemCount();i++){
			if(userPersonalDetails.getIsoCountryCode()!=null && userPersonalDetails.getIsoCountryCode().equals(listCountry.getValue(i))){
				listCountry.setSelectedIndex(i);
				break;
			}
		}
		reloadTimeZoneDropdown();
		for(int i=0;i<listTimeZone.getItemCount();i++){
			if(userPersonalDetails.getTimeZone()!=null && userPersonalDetails.getTimeZone().equals(listTimeZone.getValue(i))){
				listTimeZone.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<listSex.getItemCount();i++){
			if(userPersonalDetails.getSex()!=null && userPersonalDetails.getSex().equals(listSex.getValue(i))){
				listSex.setSelectedIndex(i);
				break;
			}
		}
		dob.setValue(userPersonalDetails.getDob());
		for(int i=0;i<listBattingHand.getItemCount();i++){
			if(userPersonalDetails.getBattingHandType()!=null && userPersonalDetails.getBattingHandType().toString().equals(listBattingHand.getValue(i))){
				listBattingHand.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<listBattingStyle.getItemCount();i++){
			if(userPersonalDetails.getBattingOrder()!=null && userPersonalDetails.getBattingOrder().toString().equals(listBattingStyle.getValue(i))){
				listBattingStyle.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<listBowlingHand.getItemCount();i++){
			if(userPersonalDetails.getBowlingHandType()!=null && userPersonalDetails.getBowlingHandType().toString().equals(listBowlingHand.getValue(i))){
				listBowlingHand.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<listBowlingStyle.getItemCount();i++){
			if(userPersonalDetails.getBowlingMethod()!=null && userPersonalDetails.getBowlingMethod().toString().equals(listBowlingStyle.getValue(i))){
				listBowlingStyle.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<listFieldPosition.getItemCount();i++){
			if(userPersonalDetails.getFieldPosition()!=null && userPersonalDetails.getFieldPosition().toString().equals(listFieldPosition.getValue(i))){
				listFieldPosition.setSelectedIndex(i);
				break;
			}
		}
	}

	@Override
	public void clean() {
		errorVoList.clear();
		refreshErrorList();
		txtName.setText("");
		lblEmail.setText("");
		txtCity.setText("");
		txtState.setText("");
		listCountry.setSelectedIndex(-1);
		listSex.setSelectedIndex(-1);
		dob.setValue(null);
		listBowlingHand.setSelectedIndex(-1);
		listBowlingStyle.setSelectedIndex(-1);
		listBattingHand.setSelectedIndex(-1);
		listBattingStyle.setSelectedIndex(-1);
		listFieldPosition.setSelectedIndex(-1);

		
	}
}
