package com.thinqq.qsports.client.teamprofile;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.FieldingPosition;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;
import com.thinqq.qsports.shared.validation.EmailValidator;
import com.thinqq.qsports.shared.validation.ErrorVo;

public class AddTeamPlayer extends PopupPanel implements ICleanable {
	public 	interface Presenter{
		void onAddTeamPlayer(String userKey);
		void onSearchPlayer(String email);
	}
	private Presenter listener;
	private static final Binder binder = GWT.create(Binder.class);
	private String teamProfileKey;
	private String cricketProfileKey;
	CommonResources RESOURCES = GWT.create(CommonResources.class);
	interface Binder extends UiBinder<Widget, AddTeamPlayer> {
	}
	@UiField Button btnSearchPlayer ;
	@UiField Button btnCancel ;

	@UiField Button btnConfirm ;
	@UiField Button btndiscardSearch ;

	@UiField TextBox txtEmail; 
	@UiField Image emailErrorMarker;
	TextFieldValidator txtEmailvalidator;


	@UiField
	Image loadingProfileImage;
	@UiField
	Image profileImage;
	@UiField
	Label name;
	@UiField
	Label email;
	@UiField
	Label dateOfBirth;
	@UiField
	Label gender;
	@UiField
	Label city;
	@UiField
	Label state;
	@UiField
	Label country;
	@UiField
	Label batting;
	@UiField
	Label bowling;
	@UiField
	Label fielding;
	@UiField
	HTMLPanel playerDetails;
	@UiField
	HorizontalPanel confirmButtonPanel;

	public static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	public static final CommonResources commonPics = GWT.create(CommonResources.class);

	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();

	public AddTeamPlayer() {
		setWidget(binder.createAndBindUi(this));
		setGlassEnabled(true);
		setAnimationEnabled(true);
		addActionHandlers();
		initialiseValidators();
	}
	public void hide(){
		super.hide();
	}


	private void initialiseValidators() {
		txtEmailvalidator = new TextFieldValidator(txtEmail, EmailValidator.getInstance(), emailErrorMarker);
	}
	private void addActionHandlers(){

		txtEmail.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> changedValue) {
				errorVoList.addAll(txtEmailvalidator.valdiate());
			}
		});
		btnCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnSearchPlayer.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				errorVoList.clear();
				errorVoList.addAll(txtEmailvalidator.valdiate());
				if(errorVoList.size()>0){
					//Cannot Register - force user to correct errors
					return;
				}
				listener.onSearchPlayer(txtEmail.getText());
			}
		});
		btndiscardSearch.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadingProfileImage.setVisible(true);
				playerDetails.setVisible(false);
				confirmButtonPanel.setVisible(false);
				txtEmail.setText("");
			}
		});
		btnConfirm.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(cricketProfileKey!=null){
					listener.onAddTeamPlayer(cricketProfileKey);
				}
			}
		});
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

	public void setUserDetails(UserProfileResponseVo profileResponse) {
		cricketProfileKey = profileResponse.getCricketProfileKey();
		if(profileResponse.getPictureURL()!=null){
			profileImage.setUrl(profileResponse.getPictureURL());
		}
		if(profileResponse.getName()!=null){
			name.setText(profileResponse.getName()+ClientConstants.PROFILE_SUFFIX);
		}else{
			name.setText("Not Available");
		}
		if(profileResponse.getDobString()!=null){
			dateOfBirth.setText(ClientConstants.DOB_LABEL+profileResponse.getDobString());
		}else{
			dateOfBirth.setText(ClientConstants.DOB_LABEL+"Not Available");
		}
		if(profileResponse.getSex()!=null){
			gender.setText(ClientConstants.GENDER_LABEL+profileResponse.getSex());
		}else{
			gender.setText(ClientConstants.GENDER_LABEL+"Not Available");
		}
		if(profileResponse.getEmail()!=null){
			email.setText(profileResponse.getEmail());
		}else{
			email.setText("Not Available");
		}
		batting.setText(HandTypeEnum.getById(profileResponse.getBattingHandType()).getScreenName()+" "+BattingOrder.getById(profileResponse.getBattingOrder()).getScreenName());
		bowling.setText(HandTypeEnum.getById(profileResponse.getBowlingHandType()).getScreenName()+" "+BowlingStyle.getById(profileResponse.getBowlingMethod()).getScreenName());
		fielding.setText(FieldingPosition.getById(profileResponse.getFieldPosition()).getScreenName());
		if(profileResponse.getCity()!=null){
			city.setText(ClientConstants.CITY_LABEL+profileResponse.getCity());
		}else{
			city.setText(ClientConstants.CITY_LABEL+"Not Available");
		}
		if(profileResponse.getState()!=null){
			state.setText(ClientConstants.STATE_LABEL+profileResponse.getState());
		}else{
			state.setText(ClientConstants.STATE_LABEL+"Not Available");
		}
		if(profileResponse.getIsoCountryCode()!=null){
			country.setText(ClientConstants.COUNTRY_LABEL+CountryList.getListCountry().get(profileResponse.getIsoCountryCode()));
		}else{
			country.setText(ClientConstants.COUNTRY_LABEL+"Not Available");
		}
		loadingProfileImage.setVisible(false);
		playerDetails.setVisible(true);
		confirmButtonPanel.setVisible(true);
		center();
	}
	@Override
	public void clean() {
		txtEmail.setText(""); 
		emailErrorMarker.setVisible(false);
		loadingProfileImage.setVisible(true);
		profileImage.setResource(RESOURCES.profileImage());	
		profileImage.setPixelSize(64, 64);
		name.setText("");
		email.setText("");
		dateOfBirth.setText("");
		gender.setText("");
		city.setText("");
		state.setText("");
		country.setText("");
		batting.setText("");
		bowling.setText("");
		fielding.setText("");
		playerDetails.setVisible(false);
		confirmButtonPanel.setVisible(false);
	}
}
