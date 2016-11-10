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
package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.common.NameAndKeyListComponent;
import com.thinqq.qsports.client.cricket.widgets.CricketCell;
import com.thinqq.qsports.client.teamprofile.TeamProfilePlace;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.IdUtil;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.match.GetMatchDetailsResponseVo;

/**
 * Sample implementation of {@link CricketMatchEditView}.
 */
public class CricketMatchEditViewImpl extends Composite implements CricketMatchEditView {

	interface Binder extends UiBinder<Widget, CricketMatchEditViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	
	CricketCell cricketCell = GWT.create(CricketCell.class);
	private Presenter listener;
	
	@UiField HTMLPanel actionPanel;
	
	@UiField Label status;
	@UiField Label instructions;
	@UiField Button stateChangeButton;
	HandlerRegistration stateButtonReg;
	@UiField HTMLPanel name;
	
	@UiField Label dateGround;
	@UiField Label conditions;
	@UiField Label umpires;
	
	@UiField Label team1Label;	
	@UiField Label team2Label;
	
	
	@UiField Button team1ChoosePlayers;
	HandlerRegistration team1PlayerChooseHandler;
	@UiField Button team2ChoosePlayers;
	HandlerRegistration team2PlayerChooseHandler;
	@UiField Button editMatch;
	
	@UiField FlowPanel team1PlayersPanel;
	@UiField FlowPanel team2PlayersPanel;
	NameAndKeyListComponent team1PlayersList;
	NameAndKeyListComponent team2PlayersList;

	CommonResources RESOURCES = GWT.create(CommonResources.class);
	
	private List<NameAndKey> team1Players;
	private List<NameAndKey> team2Players;
	private String matchKey;
	private NameAndKey team1Key;
	private NameAndKey team2Key;
	
	public CricketMatchEditViewImpl() {
		initWidget(binder.createAndBindUi(this));
		RESOURCES.getStyle().ensureInjected();
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void clean() {
		matchKey = null;
		team1Key = null;
		team2Key = null;
		if(team1PlayersList != null){
			team1PlayersList.clean();
		}
		if(team2PlayersList != null){
			team2PlayersList.clean();
		}
		if(team1PlayerChooseHandler != null){
			team1PlayerChooseHandler.removeHandler();
		}
		if(team2PlayerChooseHandler != null){
			team2PlayerChooseHandler.removeHandler();
		}
		team1ChoosePlayers.setVisible(false);
		team2ChoosePlayers.setVisible(false);
		editMatch.setVisible(false);
		actionPanel.setVisible(false);
		name.clear();
		name.add(new HTML("Loading Match..."));
		dateGround.setText("Date and Venue - Yet to be decided");
		conditions.setText("Weather details not available");
		umpires.setText("Match Officals details not available");
		stateChangeButton.setText("");
		stateChangeButton.setVisible(false);
		if(stateButtonReg != null){
			stateButtonReg.removeHandler();
			stateButtonReg = null;
		}
	}


	@Override
	public void setMatchDetails(GetMatchDetailsResponseVo details) {
		this.matchKey = details.getMatchKey();

		NameAndKey team1 = details.getTeam1();
		NameAndKey team2 = details.getTeam2();
		
		String html1Str = "";
		String html2Str = "";
		String team1Name = "";
		String team2Name = "";
		this.team1Key = team1;
		if(team1.getKey()!=null){
			html1Str = cricketCell.link(team1.getDisplayName(), 
				Genie.getLocationURLString()+"#"+TeamProfilePlace.TOKENIZER.getToken(new TeamProfilePlace(team1.getKey()))).asString();
			team1Name = team1.getDisplayName();

			
		} else {
			html1Str = IdUtil.removeNamePrefix(team1.getDisplayName());
			team1Name = html1Str;
		}
		this.team2Key = team2;
		if(team2.getKey()!=null){
			html2Str = cricketCell.link(team2.getDisplayName(), 
				Genie.getLocationURLString()+"#"+TeamProfilePlace.TOKENIZER.getToken(new TeamProfilePlace(team2.getKey()))).asString();
			team2Name = team2.getDisplayName();

		} else {
			html2Str = IdUtil.removeNamePrefix(team2.getDisplayName());
			team2Name = html2Str;
		}
		String format = MatchFormatEnum.getEnumById(details.getMatchFormat()).getScreenName();
		name.clear();
		name.add(new HTML("<table>"
				+"<tr>"
				+"<td class=\"subtitle\">"
				+format + " - "
				+"</td>"
				+"<td class=\"subtitle\">"
				+html1Str
				+"</td>"
				+"<td>"				
				+" vs "
				+"</td>"
				+"<td class=\"subtitle\">"	
				+html2Str
				+"</td>"
				+"</tr>"
				+"</table>"));
		
		String dateGroundStr = "";
		String conditionsStr = "";
		if(details.getDate()!= null){
			dateGroundStr = dateGroundStr + details.getDate();
		}
		if(details.getGround()!=null){
			dateGroundStr = dateGroundStr+ClientConstants.COMMA_SEPERATOR + details.getGround();
		}
		if(details.getCity()!=null){
			dateGroundStr = dateGroundStr+ClientConstants.COMMA_SEPERATOR +details.getCity();
		}
		if(details.getState()!=null){
			dateGroundStr = dateGroundStr+ClientConstants.COMMA_SEPERATOR +details.getState();
		}
		if(details.getCountry()!=null){
			String countryName = CountryList.getListCountry().get(details.getCountry());
			if(countryName != null){
				dateGroundStr = dateGroundStr+ClientConstants.COMMA_SEPERATOR +countryName;
			}
		}
		dateGround.setText(dateGroundStr);
		if(details.getPitch()!=null){
			conditionsStr = conditionsStr + details.getPitch();
		}
		if(details.getWeather() != null){
			conditionsStr = conditionsStr +ClientConstants.COMMA_SEPERATOR+ details.getWeather();
		}
		if(!conditionsStr.isEmpty()){
			conditions.setText(conditionsStr);
		}
		if(details.getUmpires()!=null && details.getUmpires().size()>0){
			String umpiresStr = "";
			for (String name : details.getUmpires()) {
				umpiresStr = umpiresStr+name+ClientConstants.COMMA_SEPERATOR;
			}
			umpires.setText(umpiresStr);
		}
		actionPanel.setVisible(details.isMatchEditAllowed());
		editMatch.setVisible(details.isMatchEditAllowed());
		
		team1Label.setText(team1Name+ClientConstants.TEAM_PLAYERS_LABEL_SUFFIX);
		team2Label.setText(team2Name+ClientConstants.TEAM_PLAYERS_LABEL_SUFFIX);
		
		team1Players = details.getTeam1Players();
		team2Players = details.getTeam2Players();
		if(details.isMatchEditAllowed()|| details.isTeam1EditAllowed()){
			team1ChoosePlayers.setVisible(true);
			SelectTeamClickHandler handler = new SelectTeamClickHandler();
			handler.setTeamId(1);
			team1PlayerChooseHandler = team1ChoosePlayers.addClickHandler(handler);
		}
		if(details.isMatchEditAllowed()|| details.isTeam2EditAllowed()){
			team2ChoosePlayers.setVisible(true);
			SelectTeamClickHandler handler = new SelectTeamClickHandler();
			handler.setTeamId(2);
			team2PlayerChooseHandler = team2ChoosePlayers.addClickHandler(handler);
		}
		if(team1PlayersList == null){
			team1PlayersList = NameAndKeyListComponent.getInstance(team1Players, false);
			team1PlayersPanel.add(team1PlayersList);
		} else {
			team1PlayersList.clean();
			team1PlayersList.setData(team1Players);
		}

		if(team2PlayersList == null){
			team2PlayersList = NameAndKeyListComponent.getInstance(team2Players, false);
			team2PlayersPanel.add(team2PlayersList);
		} else {
			team2PlayersList.clean();
			team2PlayersList.setData(team2Players);
		}
		
		//State Button decision
		if(details.isEligibleToStart()){
			stateChangeButton.setText("Start Match");
			stateChangeButton.setVisible(true);
			stateButtonReg = stateChangeButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					listener.startMatch();
				}
			});
		} else {
			stateChangeButton.setText("");
			stateChangeButton.setVisible(false);
			if(stateButtonReg != null){
				stateButtonReg.removeHandler();
				stateButtonReg = null;
			}
		}
	}
	
	private class SelectTeamClickHandler implements ClickHandler{
		private int teamId;
		@Override
		public void onClick(ClickEvent event) {
			if(teamId != 0){
				List<NameAndKey> playersList = null;
				String matchKey = CricketMatchEditViewImpl.this.matchKey;
				NameAndKey teamKey = null;
						
				if(teamId == 1){
					playersList = team1Players;
					teamKey = team1Key;
				} else if(teamId == 2){
					playersList = team2Players;
					teamKey = team2Key;
				}
				SelectTeamPopUp teamPopUp = Genie.getClientfactory().getSelectTeamPopUp();
				teamPopUp.setListener((SelectTeamPopUp.Presenter)CricketMatchEditViewImpl.this.listener);
				teamPopUp.setTeamPlayerList(matchKey, teamKey, playersList);
				teamPopUp.show();
				teamPopUp.center();
			}
		}

		public void setTeamId(int teamId) {
			this.teamId = teamId;
		}
		
	}
}
