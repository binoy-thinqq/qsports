package com.thinqq.qsports.client.cricket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.helper.ClientUtil;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.InningsModel;
import com.thinqq.qsports.shared.OutTypeEnum;
import com.thinqq.qsports.shared.ScoreCardBattingModel;
import com.thinqq.qsports.shared.ScoreCardBowlingModel;
import com.thinqq.qsports.shared.TeamMemberModel;
import com.thinqq.qsports.shared.TeamModel;

public class EnterScoreCardDetails extends CricketMatchTab {

	private static EnterScoreCardDetailsUiBinder uiBinder = GWT
			.create(EnterScoreCardDetailsUiBinder.class);

	interface EnterScoreCardDetailsUiBinder extends
			UiBinder<Widget, EnterScoreCardDetails> {
	}

	@UiField
	TabPanel scorecardtabs;

	List<HTMLPanel> scoreCardPanels;
	List<InningsDetails> innings = new ArrayList<InningsDetails>();

	HTMLPanel matchSummaryPanel;

	TextArea matchDescription;

	boolean initialized = false;

	List<String> battingErrors;

	List<String> bowlingErrors;

	// This fields are used for validation
	int runsConceieved;
	int runsScored;

	CricketMatchMessages messages = (CricketMatchMessages) GWT
			.create(CricketMatchMessages.class);

	public EnterScoreCardDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void initialize() {
		if(initialized) {
			return;
		}
		createScorecardPanel();
		populateFromModel();
	}

	/**
	 * Populate from the model
	 */
	private void populateFromModel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		if (matchModel.getInnings() != null) {
			List<InningsModel> inningsPlayed = matchModel.getInnings();
			int i = 0;
			for (InningsModel inningsModel : inningsPlayed) {
				innings.get(i).setBattingModelList(
						inningsModel.getBattingScoreCard());
				innings.get(i).setBowlingModelList(
						inningsModel.getBowlingScoreCard());
				i++;
			}

		}
	}

	/**
	 * 
	 */
	public void createScorecardPanel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		scorecardtabs.clear();
		scoreCardPanels = new ArrayList<HTMLPanel>();
		Map<String, String> team1Names = new HashMap<String, String>();
		Map<String, String> team2Names = new HashMap<String, String>();
		boolean isTeam1 = true;
		for (TeamModel teamModel : matchModel.getTeams()) {
			Map<String, String> teamItr = team1Names;
			if (!isTeam1) {
				teamItr = team2Names;
			}
			int index = 0;
			for (TeamMemberModel teamMember : teamModel.getTeamList()) {
				String memberProfileId = teamMember.getPlayerProfileId();
				if (memberProfileId == null || "".equals(memberProfileId)) {
					memberProfileId = teamMember.getPlayerName() + "#NOPROFILE#" + index;
					index = index + 1;
				}
				if (teamMember.getPlayerName() != null
						&& !"".equals(teamMember.getPlayerName().trim())) {
					teamItr.put(memberProfileId, teamMember.getPlayerName());
				}

			}
			isTeam1 = false;
		}
		boolean isTeam1InningsOne = false;
		if (matchModel.getTeamIndex() == 0
				&& matchModel.getChooseTo().equals(messages.batting())) {
			isTeam1InningsOne = true;
		}
		if (matchModel.getTeamIndex() == 1
				&& matchModel.getChooseTo().equals(messages.bowling())) {
			isTeam1InningsOne = true;
		}
		for (int i = 0; i < matchModel.getNoOfInnings(); i++) {
			InningsDetails inningsDetails = new InningsDetails();
			this.innings.add(inningsDetails);
			scorecardtabs.add(inningsDetails, "Innings" + (i + 1));
			if (isTeam1InningsOne) {
				inningsDetails.initializeInnings(team1Names, team2Names);
			} else {
				inningsDetails.initializeInnings(team2Names, team1Names);
			}
			isTeam1InningsOne = !isTeam1InningsOne;
		}
		matchSummaryPanel = new HTMLPanel("");
		matchSummaryPanel.setWidth("1000px");
		matchSummaryPanel.setHeight("1000px");
		matchDescription = new TextArea();
		matchDescription.setWidth("100%");
		matchSummaryPanel.add(new Label("Match Description"));
		matchSummaryPanel.add(matchDescription);
		scorecardtabs.add(matchSummaryPanel, "Match Summary");
		scorecardtabs.selectTab(0);

	}

	public void fillScorecardModel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		boolean isTeam1InningsOne = false;
		if (matchModel.getTeamIndex() == 0
				&& matchModel.getChooseTo().equals("Batting")) {
			isTeam1InningsOne = true;
		}
		if (matchModel.getTeamIndex() == 1
				&& matchModel.getChooseTo().equals("Bowling")) {
			isTeam1InningsOne = true;
		}
		List<InningsModel> inningsPlayed = new ArrayList<InningsModel>();
		boolean isValid = true;
		for (InningsDetails inningsDetails : innings) {
			InningsModel innings = new InningsModel();
			Map<String, Integer> bowlerWicketMap = new HashMap<String, Integer>();
			// Batting Model Filtering
			List<ScoreCardBattingModel> battingScoreCard = inningsDetails
					.getBattingModelList();
			List<ScoreCardBattingModel> battingScoreCardFiltered = new ArrayList<ScoreCardBattingModel>();
			boolean isValidBattingEntry = validateBattingScoreCard(battingScoreCard, battingScoreCardFiltered, bowlerWicketMap);
			innings.setBattingScoreCard(battingScoreCardFiltered);
			inningsDetails.displayBattingErrors(battingScoreCard);
			runsScored = runsScored + inningsDetails.getBye() + inningsDetails.getLegBye();
			// Bowling Model filtering
			List<ScoreCardBowlingModel> bowlingScoreCard = inningsDetails
					.getBowlingModelList();
			List<ScoreCardBowlingModel> bowlingScoreCardFiltered = new ArrayList<ScoreCardBowlingModel>();
			boolean isValidBowlingEnry = validateBowlingScoreCard(bowlingScoreCard, bowlingScoreCardFiltered, bowlerWicketMap);
			innings.setBowlingScoreCard(bowlingScoreCardFiltered);
			isValid = isValid && isValidBattingEntry && isValidBowlingEnry;
			if (isTeam1InningsOne) {
				innings.setTeamName(matchModel.getTeams().get(0).getTeamName());
			} else {
				innings.setTeamName(matchModel.getTeams().get(1).getTeamName());
			}
			isTeam1InningsOne = !isTeam1InningsOne;
			inningsPlayed.add(innings);
		}
		matchModel.setInnings(inningsPlayed);
		matchModel.setMatchSummary(matchDescription.getText());
		//matchModel.setStatus(MatchStatus.SCORE_CARD_ENTERED.getId());
	}

	@Override
	public boolean validateTab() {
		
		return false;
	}

	@Override
	public void populateMatch() {
		// If not initialized dont populate anything
		if (!isInitialized()) {
			return;
		}
		fillScorecardModel();
	}

	/**
	 * Validate each entry in the scoreCardList and return the valid entry for
	 * save
	 * 
	 * @param battingScoreCard
	 * @param battingModelList
	 * @return
	 */
	private boolean validateBattingScoreCard(List<ScoreCardBattingModel> battingScoreCard,
			List<ScoreCardBattingModel> battingModelList, Map<String, Integer> bowlerWicketMap) {
		int notOutCount = 0;
		boolean isValidScoreCard = true;
		runsScored = 0;
		for (ScoreCardBattingModel battingModel : battingScoreCard) {
			//Check the entry if a batsman is selected
			if (!ClientUtil.isEmpty(battingModel.getBatsman())) {
				if (battingModel.getOutType() > 0) {
					notOutCount++;
				}
				boolean isValidEntry = validateBattingScoreCard(battingModel);
				isValidScoreCard = isValidScoreCard && isValidEntry;
				if (isValidEntry) {
					battingModelList.add(battingModel);
					runsScored = runsScored + battingModel.getRunsScored();
				}
				if (!ClientUtil.isEmpty(battingModel.getBowledBy())) {
					int wickets = bowlerWicketMap.get(battingModel.getBowledByProfileID()) == null ? 0
							: bowlerWicketMap.get(battingModel.getBowledByProfileID());
					wickets++;
					bowlerWicketMap.put(battingModel.getBowledByProfileID(), wickets);
				}
			}
		}
		isValidScoreCard = isValidScoreCard && (notOutCount > 2);
		return isValidScoreCard;
	}

	/**
	 * Validate a scoreCard entry
	 * 
	 * @param battingModel
	 * @return
	 */
	private boolean validateBattingScoreCard(ScoreCardBattingModel battingModel) {

		battingErrors = new ArrayList<String>();
		if (battingModel.getOutType() > 0 && ClientUtil.isEmpty(battingModel.getBowledBy())) {
			if ((OutTypeEnum.BOWLED.getOutTypeId() == battingModel.getOutType()
					|| OutTypeEnum.CAUGHT.getOutTypeId() == battingModel.getOutType()
					|| OutTypeEnum.HIT_WICKET.getOutTypeId() == battingModel.getOutType()
					|| OutTypeEnum.LEG_BEFORE_WICKET.getOutTypeId() == battingModel.getOutType()
					|| OutTypeEnum.RUN_OUT.getOutTypeId() == battingModel.getOutType()
					|| OutTypeEnum.STUMPTED.getOutTypeId() == battingModel.getOutType())
					&& ClientUtil.isEmpty(battingModel.getBowledBy())) {
				battingErrors.add("Select a bowler from the list");
			}

			if ((
					OutTypeEnum.CAUGHT.getOutTypeId() == battingModel.getOutType()
							|| OutTypeEnum.RUN_OUT.getOutTypeId() == battingModel.getOutType()
							|| OutTypeEnum.STUMPTED.getOutTypeId() == battingModel.getOutType())
					&& ClientUtil.isEmpty(battingModel.getCaughtBy())) {
				battingErrors.add("Select a Fielder from caught by column from the list");
			}
			int runsBoundary = battingModel.getFours() * 4 + battingModel.getSixes() * 6;
			if (runsBoundary > battingModel.getRunsScored()) {
				battingErrors.add("Runs scored through boundaries cannot be greater than runs scored");
			}
		}
		battingModel.setErrors(battingErrors);
		return battingErrors.isEmpty();
	}

	/**
	 * Validate bowling Entries
	 * @param bowligEntries
	 * @param bowlingValidModelList
	 * @param bowlerWicketMap
	 * @return
	 */
	private boolean validateBowlingScoreCard(List<ScoreCardBowlingModel> bowligEntries,
			List<ScoreCardBowlingModel> bowlingValidModelList, Map<String, Integer> bowlerWicketMap) {
		boolean isValidScoreCard = true;
		runsConceieved = 0;
		
		for (ScoreCardBowlingModel bowlingEntry : bowlingValidModelList) {
			bowlingErrors = new ArrayList<String>();
			if (!ClientUtil.isEmpty(bowlingEntry.getBowlerName())) {
				int wickets = bowlerWicketMap.get(bowlingEntry.getBowlerProfileId()) == null ? 0 : bowlerWicketMap
						.get(bowlingEntry.getBowlerProfileId());
				if (wickets > 0 && wickets != bowlingEntry.getWickets()) {
					bowlingErrors.add("Please check the wickets taken by the bowler");
					isValidScoreCard = false;
				}
				boolean isValid = validateBowlingEntry(bowlingEntry);
				isValidScoreCard = isValid && isValidScoreCard;
				if(isValid) {
					bowlingValidModelList.add(bowlingEntry);
				}
				runsConceieved = bowlingEntry.getRunsConcieved() + runsConceieved; 
				bowlingEntry.setErrors(bowlingErrors);
			}
		}
		return isValidScoreCard;
	}

	/**
	 * Validate bowling Entry
	 * @param bowlingEntry
	 * @return
	 */
	private boolean validateBowlingEntry(ScoreCardBowlingModel bowlingEntry) {
		if (runsConceieved < (bowlingEntry.getNoBalls() + bowlingEntry.getWides())) {
			bowlingErrors.add("Extras cannot be more than runsConceieved");
		}
		return bowlingErrors.isEmpty();
	}
}
