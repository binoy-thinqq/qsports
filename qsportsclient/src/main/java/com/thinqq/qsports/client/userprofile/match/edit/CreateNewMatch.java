package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.thinqq.qsports.client.common.fieldvalidators.DateFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.ListBoxFieldValidator;
import com.thinqq.qsports.client.common.fieldvalidators.TextFieldValidator;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.Constants;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.match.SaveMatchRequestVo;
import com.thinqq.qsports.shared.validation.CityValidator;
import com.thinqq.qsports.shared.validation.CountryValidator;
import com.thinqq.qsports.shared.validation.DateValidator;
import com.thinqq.qsports.shared.validation.ErrorVo;
import com.thinqq.qsports.shared.validation.NameValidator;
import com.thinqq.qsports.shared.validation.SelectionValidator;
import com.thinqq.qsports.shared.validation.StateValidator;


public class CreateNewMatch extends PopupPanel implements IsWidget, ICleanable{

	private static CreateNewMatchUiBinder uiBinder = GWT
			.create(CreateNewMatchUiBinder.class);
	public interface Presenter{
		void setView(CreateNewMatch view);
		CreateNewMatch getView();
		void onSaveMatch(SaveMatchRequestVo request);
	}
	interface CreateNewMatchUiBinder extends UiBinder<Widget, CreateNewMatch> {
	}
	private Presenter listener;
	private String matchKey;
	
	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();
	@UiField TextBox suggestOtherTeam; 
	@UiField Image suggestOtherTeamErrorMarker;
	TextFieldValidator suggestOtherTeamValidator;
	
	@UiField
	Button btnCancel;
	@UiField
	Button btnSaveMatch;
	
	@UiField
	FocusPanel focusOpponentTeamPanel;
	
	SuggestBox sugesstionBox;
	
	@UiField
	ListBox listMyTeam;
	@UiField Image listMyTeamErrorMarker;
	ListBoxFieldValidator listMyTeamvalidator;
	
	@UiField
	DateBox matDate;
	@UiField Image matDateErrorMarker;
	@UiField FocusPanel matDateFocus;
	DateFieldValidator matDatevalidator;
	
	@UiField
	ListBox formatList;
	@UiField Image formatListErrorMarker;
	ListBoxFieldValidator formatListvalidator;
	
	@UiField
	TextBox txtGround;
	@UiField Image txtGroundErrorMarker;
	TextFieldValidator txtGroundvalidator;
	
	@UiField TextBox txtCity;
	@UiField Image txtCityErrorMarker;
	TextFieldValidator txtCityvalidator;
	
	@UiField TextBox txtState;
	@UiField Image txtStateErrorMarker;
	TextFieldValidator txtStatevalidator;
	
	@UiField ListBox listCountry;
	@UiField Image listCountryErrorMarker;
	ListBoxFieldValidator listCountryvalidator;
	
	private static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd MMM yyyy");
	
	private String opponentTeamKey;
	private String myTeamKey;
	
	public CreateNewMatch() {
		setWidget(uiBinder.createAndBindUi(this));
		sugesstionBox = new SuggestBox(TeamSuggestOracle.getInstance(), suggestOtherTeam);
		sugesstionBox.setAutoSelectEnabled(false);
		focusOpponentTeamPanel.add(sugesstionBox);
		addHandlers();
		setGlassEnabled(true);
		setAnimationEnabled(true);
		initialiseCountry();
		initialiseFormat();
		matDate.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
		initialiseValidators();
	}
	
	private void addHandlers() {
		btnCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		sugesstionBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				TeamSuggestion suggestion = (TeamSuggestion)event.getSelectedItem();
				if(suggestion.getTeamKey() != null){
					opponentTeamKey = suggestion.getTeamKey();
				}
			}
		});
		btnSaveMatch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				errorVoList.clear();
				errorVoList.addAll(listMyTeamvalidator.valdiate());
				errorVoList.addAll(suggestOtherTeamValidator.valdiate());
				errorVoList.addAll(matDatevalidator.valdiate());
				errorVoList.addAll(formatListvalidator.valdiate());
				errorVoList.addAll(txtGroundvalidator.valdiate());
				errorVoList.addAll(txtCityvalidator.valdiate());
				errorVoList.addAll(txtStatevalidator.valdiate());
				errorVoList.addAll(listCountryvalidator.valdiate());
				
				if(errorVoList.size()>0){
					return;
				}
				hide();
				
				SaveMatchRequestVo request = new SaveMatchRequestVo();
				request.setOwnTeamKey(listMyTeam.getValue(listMyTeam.getSelectedIndex()));
				if(opponentTeamKey != null){
					request.setOpponentTeamKey(opponentTeamKey);
				} else {
						opponentTeamKey = Constants.NAME_PREFIX+sugesstionBox.getText();
				}
				request.setOpponentTeamKey(opponentTeamKey);
				request.setMatchDate(matDate.getValue());
				request.setMatchFormat(Integer.parseInt(formatList.getValue(formatList.getSelectedIndex())));
				request.setGroundName(txtGround.getText());
				request.setCity(txtCity.getText());
				request.setState(txtState.getText());
				request.setCountryCode(listCountry.getValue(listCountry.getSelectedIndex()));
				if(listener != null){
					listener.onSaveMatch(request);
				}
			}
		});
	}

	public Presenter getListener() {
		return listener;
	}

	public void setListener(Presenter listener) {
		this.listener = listener;
		listener.setView(this);
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	@Override
	public void clean() {
	opponentTeamKey = null;
	}
	
	private void initialiseFormat() {
		for(MatchFormatEnum format: MatchFormatEnum.values()){
			formatList.addItem(format.getScreenName(), Integer.toString(format.getId()));
		}
	}
	
	public void setMyTeams(List<NameAndKey> list) {
			listMyTeam.addItem("Select your Team","");
			for(NameAndKey team: list){
				listMyTeam.addItem(team.getDisplayName(), team.getKey());
			}
	}
	
	
	private void initialiseCountry() {
		listCountry.addItem("Select a Country","");
		Map<String,String> countryMap = CountryList.getListCountry();
		for(String key:countryMap.keySet()){
			listCountry.addItem(countryMap.get(key), key);
		}

	}
	
	private void initialiseValidators() {
		listMyTeamvalidator =  new ListBoxFieldValidator(listMyTeam, SelectionValidator.getInstance(), listMyTeamErrorMarker);
		suggestOtherTeamValidator = new TextFieldValidator(suggestOtherTeam, NameValidator.getInstance(), suggestOtherTeamErrorMarker);
		matDatevalidator = new DateFieldValidator(matDate, DateValidator.getInstance(), matDateErrorMarker);
		formatListvalidator = new ListBoxFieldValidator(formatList, SelectionValidator.getInstance(), formatListErrorMarker);
		txtGroundvalidator = new TextFieldValidator(txtGround, CityValidator.getInstance(), txtGroundErrorMarker);
		txtCityvalidator = new TextFieldValidator(txtCity, CityValidator.getInstance(), txtCityErrorMarker);
		txtStatevalidator = new TextFieldValidator(txtState, StateValidator.getInstance(), txtStateErrorMarker);
		listCountryvalidator = new ListBoxFieldValidator(listCountry, CountryValidator.getInstance(), listCountryErrorMarker);
	}
}
