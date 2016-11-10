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
package com.thinqq.qsports.client.userprofile;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.CommonResources;
import com.thinqq.qsports.client.cricket.widgets.CricketCell;
import com.thinqq.qsports.client.teamprofile.EditTeamProfile;
import com.thinqq.qsports.client.teamprofile.TeamProfilePlace;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.FieldingPosition;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo.TeamProfileMinDetails;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;


/**
 * Sample implementation of {@link UserProfileView}.
 */
public class UserProfileViewImpl extends Composite implements UserProfileView {
	
	//@UiField
	//Image loadingProfileImage;
/*	@UiField
	Image loadingBowlingImage;*/
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
	Anchor editProfile;
	
	private static CricketCell cricketCellTemplates = GWT.create(CricketCell.class);
	
	@UiField(provided=true) CellList<TeamProfileMinDetails> teamsList = new CellList<TeamProfileMinDetails>(new AbstractCell<TeamProfileMinDetails>(){
		@Override
		public void render(Context context, TeamProfileMinDetails teamData, SafeHtmlBuilder sb) {
			String teamName = teamData.getName();
			if(teamData.getPictureURL() == null){
				teamData.setPictureURL("");
			}
			if(teamData.getTeamKey() == null){
				sb.append(cricketCellTemplates.teamHeaderCell());
			}else{
				sb.append(cricketCellTemplates.teamCell(teamData.getPictureURL(), teamName,
					Genie.getLocationURLString()+"#"+TeamProfilePlace.TOKENIZER.getToken(new TeamProfilePlace(teamData.getTeamKey())),
					teamData.getCityState(), CountryList.getListCountry().get(teamData.getCountry()), teamData.getMatches(), teamData.getWon(),
					teamData.getLost(),teamData.getWinPercent()));
			}
		}
	});
	@UiField
	SimplePager teamsListPager;

	/*@UiField
	Image loadingTeams;*/

	CommonResources RESOURCES = GWT.create(CommonResources.class);
	
	
	interface Binder extends UiBinder<Widget, UserProfileViewImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	private Presenter listener;
	
	public UserProfileViewImpl() {
		initWidget(binder.createAndBindUi(this));
		//loadingProfileImage.setResource(RESOURCES.loadingImageSmall());
		//loadingBowlingImage.setResource(RESOURCES.loadingImageSmall());
		profileImage.setPixelSize(100, 100);
		addActionHandlers();
		
		teamsListPager.setDisplay(teamsList);
		teamsList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
	}


	private void addActionHandlers() {

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
	public void setUserDetails(UserProfileResponseVo profileResponse) {
		if(profileResponse.getPictureURL()!=null){
			profileImage.setUrl(profileResponse.getPictureURL());
			profileImage.setPixelSize(100, 100);
		}
    	if(profileResponse.getName()!=null){
			name.setText(profileResponse.getName());
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
    	//loadingProfileImage.setVisible(false);
    }


	@Override
	public void showCreateTeamView(EditTeamProfile newTeamView) {
		newTeamView.setPopupPosition(Window.getScrollLeft()+200, Window.getScrollTop()+200);
		newTeamView.show();
		
	}


	@Override
	public void showFatalError() {
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	    simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
	    simplePopup.setWidth("150px");
	    simplePopup.setWidget(
	        new HTML("FATAL ERROR"));
	    simplePopup.show();
	}

	@UiField
	Label matchesT20;

	@UiField
	Label inningsT20;

	@UiField
	Label notoutT20;

	@UiField
	Label runsT20;

	@UiField
	Label strikeRateT20;

	@UiField
	Label averBatT20;

	@UiField
	Label highScoreT20;

	@UiField
	Label hundredsT20;

	@UiField
	Label fiftiesT20;

	@UiField
	Label thirtiesT20;

	@UiField
	Label sixesT20;

	@UiField
	Label foursT20;

	@UiField
	Label matchesOD;

	@UiField
	Label inningsOD;

	@UiField
	Label notoutOD;

	@UiField
	Label runsOD;

	@UiField
	Label strikeRateOD;

	@UiField
	Label averBatOD;

	@UiField
	Label highScoreOD;

	@UiField
	Label hundredsOD;

	@UiField
	Label fiftiesOD;

	@UiField
	Label thirtiesOD;

	@UiField
	Label sixesOD;

	@UiField
	Label foursOD;

	@UiField
	Label matchesTst;

	@UiField
	Label inningsTst;

	@UiField
	Label notoutTst;

	@UiField
	Label runsTst;

	@UiField
	Label strikeRateTst;

	@UiField
	Label averBatTst;

	@UiField
	Label highScoreTst;

	@UiField
	Label hundredsTst;

	@UiField
	Label fiftiesTst;

	@UiField
	Label thirtiesTst;

	@UiField
	Label sixesTst;

	@UiField
	Label foursTst;


	@Override
	public void setBattingStatistics(List<String> t20Batting,
			List<String> oDBatting, List<String> testBatting) {
		matchesT20.setText(t20Batting.get(0));
		inningsT20.setText(t20Batting.get(1));
		notoutT20.setText(t20Batting.get(2));
		runsT20.setText(t20Batting.get(3));
		strikeRateT20.setText(t20Batting.get(4));
		averBatT20.setText(t20Batting.get(5));
		highScoreT20.setText(t20Batting.get(6));
		hundredsT20.setText(t20Batting.get(7));
		fiftiesT20.setText(t20Batting.get(8));
		thirtiesT20.setText(t20Batting.get(9));
		sixesT20.setText(t20Batting.get(10));
		foursT20.setText(t20Batting.get(11));
		
		matchesOD.setText(oDBatting.get(0));
		inningsOD.setText(oDBatting.get(1));
		notoutOD.setText(oDBatting.get(2));
		runsOD.setText(oDBatting.get(3));
		strikeRateOD.setText(oDBatting.get(4));
		averBatOD.setText(oDBatting.get(5));
		highScoreOD.setText(oDBatting.get(6));
		hundredsOD.setText(oDBatting.get(7));
		fiftiesOD.setText(oDBatting.get(8));
		thirtiesOD.setText(oDBatting.get(9));
		sixesOD.setText(oDBatting.get(10));
		foursOD.setText(oDBatting.get(11));
		
		matchesTst.setText(testBatting.get(0));
		inningsTst.setText(testBatting.get(1));
		notoutTst.setText(testBatting.get(2));
		runsTst.setText(testBatting.get(3));
		strikeRateTst.setText(testBatting.get(4));
		averBatTst.setText(testBatting.get(5));
		highScoreTst.setText(testBatting.get(6));
		hundredsTst.setText(testBatting.get(7));
		fiftiesTst.setText(testBatting.get(8));
		thirtiesTst.setText(testBatting.get(9));
		sixesTst.setText(testBatting.get(10));
		foursTst.setText(testBatting.get(11));
	}

	@UiField
	Label b_matchesT20;
	@UiField
	Label b_inningsT20;
	@UiField
	Label b_ballsT20;
	@UiField
	Label b_wicketsT20;
	@UiField
	Label b_avgT20;
	@UiField
	Label b_econT20;
	@UiField
	Label b_runsT20;
	@UiField
	Label b_3WicketsT20;
	@UiField
	Label b_5WicketsT20;
	@UiField
	Label b_bestBowlingT20;
	@UiField
	Label b_CatchesT20;
	@UiField
	Label b_StumpingsT20;
	
	@UiField
	Label b_matchesOD;
	@UiField
	Label b_inningsOD;
	@UiField
	Label b_ballsOD;
	@UiField
	Label b_wicketsOD;
	@UiField
	Label b_avgOD;
	@UiField
	Label b_econOD;
	@UiField
	Label b_runsOD;
	@UiField
	Label b_3WicketsOD;
	@UiField
	Label b_5WicketsOD;
	@UiField
	Label b_bestBowlingOD;
	@UiField
	Label b_CatchesOD;
	@UiField
	Label b_StumpingsOD;
	
	@UiField
	Label b_matchesTest;
	@UiField
	Label b_inningsTest;
	@UiField
	Label b_ballsTest;
	@UiField
	Label b_wicketsTest;
	@UiField
	Label b_avgTest;
	@UiField
	Label b_econTest;
	@UiField
	Label b_runsTest;
	@UiField
	Label b_3WicketsTest;
	@UiField
	Label b_5WicketsTest;
	@UiField
	Label b_bestBowlingTest;
	@UiField
	Label b_CatchesTest;
	@UiField
	Label b_StumpingsTest;
	
	@Override
	public void setBowlingStatistics(List<String> t20Bowling,
			List<String> oDBowling, List<String> testBowling) {
		b_matchesT20.setText(t20Bowling.get(0));
		b_inningsT20.setText(t20Bowling.get(1));
		b_ballsT20.setText(t20Bowling.get(2));
		b_wicketsT20.setText(t20Bowling.get(3));
		b_avgT20.setText(t20Bowling.get(4));
		b_econT20.setText(t20Bowling.get(5));
		b_runsT20.setText(t20Bowling.get(6));
		b_3WicketsT20.setText(t20Bowling.get(7));
		b_5WicketsT20.setText(t20Bowling.get(8));
		b_bestBowlingT20.setText(t20Bowling.get(9));
		b_CatchesT20.setText(t20Bowling.get(10));
		b_StumpingsT20.setText(t20Bowling.get(11));
		
		b_matchesOD.setText(oDBowling.get(0));
		b_inningsOD.setText(oDBowling.get(1));
		b_ballsOD.setText(oDBowling.get(2));
		b_wicketsOD.setText(oDBowling.get(3));
		b_avgOD.setText(oDBowling.get(4));
		b_econOD.setText(oDBowling.get(5));
		b_runsOD.setText(oDBowling.get(6));
		b_3WicketsOD.setText(oDBowling.get(7));
		b_5WicketsOD.setText(oDBowling.get(8));
		b_bestBowlingOD.setText(oDBowling.get(9));
		b_CatchesOD.setText(oDBowling.get(10));
		b_StumpingsOD.setText(oDBowling.get(11));
		
		b_matchesTest.setText(testBowling.get(0));
		b_inningsTest.setText(testBowling.get(1));
		b_ballsTest.setText(testBowling.get(2));
		b_wicketsTest.setText(testBowling.get(3));
		b_avgTest.setText(testBowling.get(4));
		b_econTest.setText(testBowling.get(5));
		b_runsTest.setText(testBowling.get(6));
		b_3WicketsTest.setText(testBowling.get(7));
		b_5WicketsTest.setText(testBowling.get(8));
		b_bestBowlingTest.setText(testBowling.get(9));
		b_CatchesTest.setText(testBowling.get(10));
		b_StumpingsTest.setText(testBowling.get(11));
		//loadingBowlingImage.setVisible(false);
		
	}


	@Override
	public void showEditPofileButton(boolean show) {
		editProfile.setVisible(show);
		if(show){
			editProfile.addClickHandler(new ClickHandler() {
			
				@Override
				public void onClick(ClickEvent event) {
					listener.onEditProfileShow();
				}
			});
		}
	}


	@Override
	public void setTeamsList(List<TeamProfileMinDetails> teamsListData) {
		//teamsListData.add(0, new TeamProfileMinDetails());
		ListDataProvider<TeamProfileMinDetails> dataProvider = new ListDataProvider<TeamProfileMinDetails>(teamsListData);
		dataProvider.addDataDisplay(teamsList);
		teamsList.setVisible(true);
		//loadingTeams.setVisible(false);
	}


	@Override
	public void clean() {
		//Profile fields initialization
		//loadingProfileImage.setVisible(true);
		//loadingBowlingImage.setVisible(true);
		profileImage.setResource(RESOURCES.profileImage());	
		profileImage.setPixelSize(64, 64);
		email.setText("");	
		dateOfBirth.setText("");	
		gender.setText("");	
		city.setText("");	
		state.setText("");	
		country.setText("");	
		batting.setText("");	
		bowling.setText("");	
		fielding.setText("");	
		editProfile.setVisible(false);
		//Batting statistics initialization
		matchesT20.setText("-");
		inningsT20.setText("-");
		notoutT20.setText("-");
		runsT20.setText("-");
		strikeRateT20.setText("-");
		averBatT20.setText("-");
		highScoreT20.setText("-");
		hundredsT20.setText("-");
		fiftiesT20.setText("-");
		thirtiesT20.setText("-");
		sixesT20.setText("-");
		foursT20.setText("-");
		
		matchesOD.setText("-");
		inningsOD.setText("-");
		notoutOD.setText("-");
		runsOD.setText("-");
		strikeRateOD.setText("-");
		averBatOD.setText("-");
		highScoreOD.setText("-");
		hundredsOD.setText("-");
		fiftiesOD.setText("-");
		thirtiesOD.setText("-");
		sixesOD.setText("-");
		foursOD.setText("-");
		
		matchesTst.setText("-");
		inningsTst.setText("-");
		notoutTst.setText("-");
		runsTst.setText("-");
		strikeRateTst.setText("-");
		averBatTst.setText("-");
		highScoreTst.setText("-");
		hundredsTst.setText("-");
		fiftiesTst.setText("-");
		thirtiesTst.setText("-");
		sixesTst.setText("-");
		foursTst.setText("-");
		
		//Bowling statistics initialization
		b_matchesT20.setText("-");
		b_inningsT20.setText("-");
		b_ballsT20.setText("-");
		b_wicketsT20.setText("-");
		b_avgT20.setText("-");
		b_econT20.setText("-");
		b_runsT20.setText("-");
		b_3WicketsT20.setText("-");
		b_5WicketsT20.setText("-");
		b_bestBowlingT20.setText("-");
		b_CatchesT20.setText("-");
		b_StumpingsT20.setText("-");
		
		b_matchesOD.setText("-");
		b_inningsOD.setText("-");
		b_ballsOD.setText("-");
		b_wicketsOD.setText("-");
		b_avgOD.setText("-");
		b_econOD.setText("-");
		b_runsOD.setText("-");
		b_3WicketsOD.setText("-");
		b_5WicketsOD.setText("-");
		b_bestBowlingOD.setText("-");
		b_CatchesOD.setText("-");
		b_StumpingsOD.setText("-");
		
		b_matchesTest.setText("-");
		b_inningsTest.setText("-");
		b_ballsTest.setText("-");
		b_wicketsTest.setText("-");
		b_avgTest.setText("-");
		b_econTest.setText("-");
		b_runsTest.setText("-");
		b_3WicketsTest.setText("-");
		b_5WicketsTest.setText("-");
		b_bestBowlingTest.setText("-");
		b_CatchesTest.setText("-");
		b_StumpingsTest.setText("-");
		//loadingBowlingImage.setVisible(true);
		
		teamsList.setVisible(false);
		//loadingTeams.setVisible(true);
	}

}
