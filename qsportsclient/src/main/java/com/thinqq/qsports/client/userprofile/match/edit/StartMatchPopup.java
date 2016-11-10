package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.IdUtil;
import com.thinqq.qsports.shared.MatchStatus;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.cricket.TossChoiceEnum;
import com.thinqq.qsports.shared.match.MatchStateChangeRequestVo;
import com.thinqq.qsports.shared.validation.ErrorVo;


public class StartMatchPopup extends PopupPanel implements IsWidget, ICleanable{

	private static StartMatchPopupUiBinder uiBinder = GWT
			.create(StartMatchPopupUiBinder.class);
	public interface Presenter{
		void onStartMatch(MatchStateChangeRequestVo request);
	}
	interface StartMatchPopupUiBinder extends UiBinder<Widget, StartMatchPopup> {
	}
	private Presenter listener;
	private String matchKey;
	
	@UiField ListBox tossWonList;
	@UiField ListBox tossChoice;
	@UiField TextBox officials;
	@UiField TextBox weatherDetails;
	@UiField TextBox pitchDetails;
	
	private List<ErrorVo> errorVoList = new ArrayList<ErrorVo>();

	
	@UiField
	Button btnCancel;
	@UiField
	Button btnStartMatch;
	
	public StartMatchPopup() {
		setWidget(uiBinder.createAndBindUi(this));
		addHandlers();
		setGlassEnabled(true);
		setAnimationEnabled(true);
		initialiseTossChoice();
		initialiseValidators();
	}
	
	private void initialiseTossChoice() {
		this.tossChoice.addItem("Please Choose ...", "");
		for(TossChoiceEnum tossChoiceEntry : TossChoiceEnum.values()){
			this.tossChoice.addItem(tossChoiceEntry.getScreenName(), Integer.toString(tossChoiceEntry.getId()));
		}
	}

	private void addHandlers() {
		btnCancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnStartMatch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//TODO : Validation of Toss team and toss choice
				if(listener != null){
					MatchStateChangeRequestVo request = new MatchStateChangeRequestVo(matchKey, MatchStatus.STARTED.getId());
					
					request.setTossWonTeamKey(tossWonList.getValue(tossWonList.getSelectedIndex()));
					request.setTossChoiceTaken(Integer.parseInt(tossChoice.getValue(tossChoice.getSelectedIndex())));
					String officialsStr = officials.getText();
					if(officialsStr != null && !officialsStr.isEmpty()){
						request.setOfficials(officialsStr);
					}
					
					String weatherDetailsStr = weatherDetails.getText();
					if(weatherDetailsStr != null && !weatherDetailsStr.isEmpty()){
						request.setWeatherDetails(weatherDetailsStr);
					}
					
					String pitchDetailsStr = pitchDetails.getText();
					if(pitchDetailsStr != null && !pitchDetailsStr.isEmpty()){
						request.setPitchCondition(pitchDetailsStr);
					}
					
					listener.onStartMatch(request);
				}
			}
		});
	}

	public Presenter getListener() {
		return listener;
	}

	public void setListener(Presenter listener) {
		this.listener = listener;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	
	public void setTeams(NameAndKey team1, NameAndKey team2){
		this.tossWonList.addItem("Please Choose ...", "");
		if(team1.getKey()!=null){
			tossWonList.addItem(team1.getDisplayName(),team1.getKey());
		} else {
			tossWonList.addItem(IdUtil.removeNamePrefix(team1.getDisplayName()),team1.getDisplayName());
		}
		if(team2.getKey()!=null){
			tossWonList.addItem(team2.getDisplayName(),team2.getKey());
		} else {
			tossWonList.addItem(IdUtil.removeNamePrefix(team2.getDisplayName()),team2.getDisplayName());
		}
	}

	@Override
	public void clean() {
		matchKey = null;
		tossWonList.clear();
	}
	


	
	private void initialiseValidators() {

	}
}
