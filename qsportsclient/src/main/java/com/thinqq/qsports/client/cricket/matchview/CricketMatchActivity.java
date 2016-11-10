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
package com.thinqq.qsports.client.cricket.matchview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.cricket.PlayerProfileMinData;
import com.thinqq.qsports.client.cricket.TeamProfileMinData;
import com.thinqq.qsports.client.cricket.matchview.battingcard.BattingCardModel;
import com.thinqq.qsports.client.cricket.matchview.battingentry.BattingEntryModel;
import com.thinqq.qsports.client.cricket.matchview.bowlingcard.BowlingCardModel;
import com.thinqq.qsports.client.cricket.matchview.bowlingentry.BowlingEntryModel;
import com.thinqq.qsports.client.cricket.matchview.innings.InningsComponentModel;
import com.thinqq.qsports.shared.OutTypeEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class CricketMatchActivity extends AbstractActivity implements CricketMatchView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;

	public CricketMatchActivity(CricketMatchPlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		CricketMatchView view = clientFactory.getCricketMatchView();
		createSampleCricketMatchData();
		
		view.setName(name);
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}

	private CricketMatchModel createSampleCricketMatchData() {
		CricketMatchModel model = new CricketMatchModel();
		List<InningsComponentModel> innings = new ArrayList<InningsComponentModel>(4);
		model.setFormat(MatchFormatEnum.OD.getScreenName());
		model.setLocation("Venkateswara College, Chennai, Tamil Nadu, India");
		model.setStatus("COMPLETED");
		TeamProfileMinData team1 = new TeamProfileMinData();
		team1.setTeamName("Weekend Warriors");
		team1.setTeamProfileToken("TeamProfilePlace:34");
		TeamProfileMinData team2 = new TeamProfileMinData();
		team2.setTeamName("Good Team");
		team2.setTeamProfileToken("TeamProfilePlace:44");
		model.setTeam1(team1);
		model.setTeam2(team2);
		model.setWonByString("Weekend Warriors won by 34 runs");
		innings.add(createInnings1());
		innings.add(createInnings2());
		model.setInningsList(innings);
		return model;
	}

	private InningsComponentModel createInnings1() {
		InningsComponentModel innings1 = new InningsComponentModel();
		innings1.setBattingCard(createBattingCard1());
		innings1.setBowlingCard(createBowlingCard1());
		innings1.setInningsName("Weekend Warriors innings");
		innings1.setScoreSummary("137 for 5 in 25 overs");
		innings1.setInProgress(false);
		return innings1;
	}

	private BowlingCardModel createBowlingCard1() {
		BowlingCardModel model = new BowlingCardModel();
		BowlingEntryModel bowlingRow1 = new BowlingEntryModel(1l,new PlayerProfileMinData("Bala B","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow2 = new BowlingEntryModel(2l,new PlayerProfileMinData("Natarajan N","DisplayToken34",1l),4,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow3 = new BowlingEntryModel(3l,new PlayerProfileMinData("Prashanth P","DisplayToken34",1l),2,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow4 = new BowlingEntryModel(4l,new PlayerProfileMinData("Velmurugan V","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow5 = new BowlingEntryModel(5l,new PlayerProfileMinData("Hemanth H","DisplayToken34",1l),2,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow6 = new BowlingEntryModel(6l,new PlayerProfileMinData("Uthaman U","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow7 = new BowlingEntryModel(7l,new PlayerProfileMinData("Ashwath A","DisplayToken34",1l),3,0,0,24,1,4.30f,2,2);
		Map<Long, BowlingEntryModel> map = new HashMap<Long, BowlingEntryModel>();
		map.put(bowlingRow1.getEntryId(), bowlingRow1);
		map.put(bowlingRow2.getEntryId(), bowlingRow2);
		map.put(bowlingRow3.getEntryId(), bowlingRow3);
		map.put(bowlingRow4.getEntryId(), bowlingRow4);
		map.put(bowlingRow5.getEntryId(), bowlingRow5);
		map.put(bowlingRow6.getEntryId(), bowlingRow6);
		map.put(bowlingRow7.getEntryId(), bowlingRow7);
		model.setBattingEntries(map);
		model.setTeamName("Good Team Innings");
		return model;
	}

	/**
	 * @return
	 */
	private BattingCardModel createBattingCard1() {
		BattingCardModel model = new BattingCardModel();
		model.setTeamName("Weekend Warriors");
		BattingEntryModel batsman1 = new BattingEntryModel();
		PlayerProfileMinData minData = new PlayerProfileMinData();
		minData.setDisplayName("Parthasarathy S");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman1.setBatsman(minData);
		//4s
		batsman1.setFours(3);
		//6s
		batsman1.setSixes(0);
		//Balls Faced
		batsman1.setBallsFaced(45);
		//OutType
		batsman1.setOutType(OutTypeEnum.CAUGHT);
		//Set Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Uthamachozhan C");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman1.setBowlerOrFielder2(minData);
		//Set Is C & B
		batsman1.setCaughtAndBowled(true);
		//Runs
		batsman1.setRuns(31);
		batsman1.setEntryId(1l);
		batsman1.setSequence(1);
		batsman1.setStrikeRate(31/45*100);

		BattingEntryModel batsman2 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Neela Mohan P");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman2.setBatsman(minData);
		//4s
		batsman2.setFours(1);
		//6s
		batsman2.setSixes(0);
		//Balls Faced
		batsman2.setBallsFaced(14);
		//OutType
		batsman2.setOutType(OutTypeEnum.RUN_OUT);
		//Set Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Velmurugan V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman2.setFielder1(minData);
		//Runs
		batsman2.setRuns(11);
		batsman2.setEntryId(2l);
		batsman2.setSequence(3);
		batsman2.setStrikeRate(11/14*100);
		
		BattingEntryModel batsman3 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Lakshminarayan D");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman3.setBatsman(minData);
		//4s
		batsman3.setFours(1);
		//6s
		batsman3.setSixes(0);
		//Balls Faced
		batsman3.setBallsFaced(20);
		//OutType
		batsman3.setOutType(OutTypeEnum.CAUGHT);
		//Set fielder and Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Velmurugan V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman3.setFielder1(minData);
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Uthamachozhan C");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman3.setBowlerOrFielder2(minData);
		//Runs
		batsman3.setRuns(13);
		batsman3.setEntryId(3l);
		batsman3.setSequence(3);
		batsman3.setStrikeRate(13/20*100);
		
		BattingEntryModel batsman4 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Malaisamy M");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman4.setBatsman(minData);
		//4s
		batsman4.setFours(0);
		//6s
		batsman4.setSixes(0);
		//Balls Faced
		batsman4.setBallsFaced(42);
		//OutType
		batsman4.setOutType(null);
		//Runs
		batsman4.setRuns(32);
		batsman4.setEntryId(4l);
		batsman4.setSequence(4);
		batsman4.setStrikeRate(32/42*100);
		
		BattingEntryModel batsman5 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Saravanan S");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman5.setBatsman(minData);
		//4s
		batsman5.setFours(0);
		//6s
		batsman5.setSixes(0);
		//Balls Faced
		batsman5.setBallsFaced(6);
		//OutType
		batsman5.setOutType(OutTypeEnum.CAUGHT);
		//Set fielder and Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Prasanna P");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman5.setFielder1(minData);
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Ashwath A");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman5.setBowlerOrFielder2(minData);
		//Runs
		batsman5.setRuns(3);
		batsman5.setEntryId(5l);
		batsman5.setSequence(5);
		batsman5.setStrikeRate(3/6*100);
		
		BattingEntryModel batsman6 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Rakesh J");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman6.setBatsman(minData);
		//4s
		batsman6.setFours(0);
		//6s
		batsman6.setSixes(1);
		//Balls Faced
		batsman6.setBallsFaced(19);
		//OutType
		batsman6.setOutType(OutTypeEnum.RUN_OUT);
		//Set fielder and Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Velmurugan V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman6.setFielder1(minData);
		//Runs
		batsman6.setRuns(20);
		batsman6.setEntryId(6l);
		batsman6.setSequence(6);
		batsman6.setStrikeRate(20/19*100);
		
		BattingEntryModel batsman7 = new BattingEntryModel();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Vignesh V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman7.setBatsman(minData);
		//4s
		batsman7.setFours(0);
		//6s
		batsman7.setSixes(0);
		//Balls Faced
		batsman7.setBallsFaced(2);
		//OutType
		batsman7.setOutType(OutTypeEnum.RUN_OUT);
		//Set fielder and Bowler
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Velmurugan V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		batsman7.setFielder1(minData);
		//Runs
		batsman7.setRuns(1);
		batsman7.setEntryId(8l);
		batsman7.setSequence(7);
		batsman7.setStrikeRate(1/2*100);
		
		Map<Long,BattingEntryModel> battingEntries = new TreeMap<Long, BattingEntryModel>();
		battingEntries.put(batsman1.getEntryId(), batsman1);
		battingEntries.put(batsman2.getEntryId(), batsman2);
		battingEntries.put(batsman3.getEntryId(), batsman3);
		battingEntries.put(batsman4.getEntryId(), batsman4);
		battingEntries.put(batsman5.getEntryId(), batsman5);
		battingEntries.put(batsman6.getEntryId(), batsman6);
		battingEntries.put(batsman7.getEntryId(), batsman7);
		model.setBattingEntries(battingEntries);
		model.seteWides(18);
		model.seteNoBalls(2);
		model.seteByes(1);
		model.seteLegByes(3);
		model.setePenaltyRuns(0);
		model.seteTotal(24);
		model.setOvers(25);
		model.setBalls(0);
		model.setTotal(137);
		model.setWickets(5);
		//Batsman To Bat
		List<PlayerProfileMinData> list = new ArrayList<PlayerProfileMinData>();
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Balaji B");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		list.add(minData);
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Kurmaragurubaran K");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		list.add(minData);
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Kumaresan K");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		list.add(minData);
		minData = new PlayerProfileMinData();
		minData.setDisplayName("Subramaniam V");
		minData.setProfileToken("UserProfile:34");
		minData.setUserId(34l);
		list.add(minData);
		model.setBatsmenToBat(list);
		return model;
	
	}

	private InningsComponentModel createInnings2() {
		InningsComponentModel innings2 = new InningsComponentModel();
		innings2.setBattingCard(createBattingCard2());
		innings2.setBowlingCard(createBowlingCard2());
		innings2.setInningsName("Good Team innings");
		innings2.setScoreSummary("162 in 26.2 overs");
		innings2.setInProgress(false);
		return innings2;
	}

	private BowlingCardModel createBowlingCard2() {
		BowlingCardModel model = new BowlingCardModel();
		BowlingEntryModel bowlingRow1 = new BowlingEntryModel(1l,new PlayerProfileMinData("Bala B","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow2 = new BowlingEntryModel(2l,new PlayerProfileMinData("Natarajan N","DisplayToken34",1l),4,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow3 = new BowlingEntryModel(3l,new PlayerProfileMinData("Prashanth P","DisplayToken34",1l),2,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow4 = new BowlingEntryModel(4l,new PlayerProfileMinData("Velmurugan V","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow5 = new BowlingEntryModel(5l,new PlayerProfileMinData("Hemanth H","DisplayToken34",1l),2,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow6 = new BowlingEntryModel(6l,new PlayerProfileMinData("Uthaman U","DisplayToken34",1l),5,0,0,24,1,4.30f,2,2);
		BowlingEntryModel bowlingRow7 = new BowlingEntryModel(7l,new PlayerProfileMinData("Ashwath A","DisplayToken34",1l),3,0,0,24,1,4.30f,2,2);
		Map<Long, BowlingEntryModel> map = new HashMap<Long, BowlingEntryModel>();
		map.put(bowlingRow1.getEntryId(), bowlingRow1);
		map.put(bowlingRow2.getEntryId(), bowlingRow2);
		map.put(bowlingRow3.getEntryId(), bowlingRow3);
		map.put(bowlingRow4.getEntryId(), bowlingRow4);
		map.put(bowlingRow5.getEntryId(), bowlingRow5);
		map.put(bowlingRow6.getEntryId(), bowlingRow6);
		map.put(bowlingRow7.getEntryId(), bowlingRow7);
		model.setBattingEntries(map);
		model.setTeamName("Good Team Innings");
		return model;
	}

	private BattingCardModel createBattingCard2() {
		BattingCardModel model = new BattingCardModel();
		model.setTeamName("Weekend Warriors");
		
		BattingEntryModel batsman1 = new BattingEntryModel(21l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman2 = new BattingEntryModel(22l, 2, new PlayerProfileMinData("Uthama U","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				new PlayerProfileMinData("Arun A","DisplayToken34",1l), 
				true, OutTypeEnum.CAUGHT, 2, 8, 2/8*100, 0, 0);
		BattingEntryModel batsman3 = new BattingEntryModel(23l, 3, new PlayerProfileMinData("Hemanth H","DisplayToken34",1l), 
				null, 
				new PlayerProfileMinData("Arun A","DisplayToken34",1l),
			false, OutTypeEnum.BOWLED, 2, 7, 2/7*100, 0, 0);
		BattingEntryModel batsman4 = new BattingEntryModel(24l, 4, new PlayerProfileMinData("Bala B","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman5 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman6 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman7 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman8 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman9 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman10 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		BattingEntryModel batsman11 = new BattingEntryModel(1l, 1, new PlayerProfileMinData("Ashwath A","DisplayToken34",1l), 
				new PlayerProfileMinData("Balaji B","DisplayToken34",1l), 
				null,
				false, OutTypeEnum.RUN_OUT, 47, 60, 47/60*100, 3, 0);
		Map<Long,BattingEntryModel> battingEntries = new TreeMap<Long, BattingEntryModel>();
		battingEntries.put(batsman1.getEntryId(), batsman1);
		battingEntries.put(batsman2.getEntryId(), batsman2);
		battingEntries.put(batsman3.getEntryId(), batsman3);
		battingEntries.put(batsman4.getEntryId(), batsman4);
		battingEntries.put(batsman5.getEntryId(), batsman5);
		battingEntries.put(batsman6.getEntryId(), batsman6);
		battingEntries.put(batsman7.getEntryId(), batsman7);
		battingEntries.put(batsman8.getEntryId(), batsman8);
		battingEntries.put(batsman9.getEntryId(), batsman9);
		battingEntries.put(batsman10.getEntryId(), batsman10);
		battingEntries.put(batsman11.getEntryId(), batsman11);
		model.setBattingEntries(battingEntries);
		model.seteWides(18);
		model.seteNoBalls(2);
		model.seteByes(1);
		model.seteLegByes(3);
		model.setePenaltyRuns(0);
		model.seteTotal(24);
		model.setOvers(23);
		model.setBalls(4);
		model.setTotal(126);
		model.setWickets(10);
		
		return model;
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see CricketMatchView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
