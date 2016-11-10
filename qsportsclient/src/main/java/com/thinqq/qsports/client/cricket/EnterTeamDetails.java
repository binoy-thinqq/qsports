package com.thinqq.qsports.client.cricket;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.helper.ClientUtil;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.MatchStatus;
import com.thinqq.qsports.shared.TeamMemberModel;
import com.thinqq.qsports.shared.TeamModel;

/**
 * Creates Enter Team UI Tab for CricketMatch Page
 * 
 * @author Binoy
 * 
 */
public class EnterTeamDetails extends CricketMatchTab {

	private static EnterTeamDetailsUiBinder uiBinder = GWT
			.create(EnterTeamDetailsUiBinder.class);

	@UiField
	HTMLPanel team1;
	@UiField
	HTMLPanel team2;

	TextBox[] team1Names;
	TextBox[] team2Names;
	@UiField
	ListBox tossWonBy;

	@UiField
	ListBox chooseTo;

	@UiField
	Label tossWonByLabel;

	@UiField
	Label chooseToLabel;

	@UiField
	Label teamPanelHeaderLabel;

	boolean initialized = false;

	interface EnterTeamDetailsUiBinder extends
			UiBinder<Widget, EnterTeamDetails> {
	}

	public EnterTeamDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void initialize() {
		initialized = true;
		createTeam();
	}

	/**
	 * Create Team Tab
	 */
	public void createTeam() {
		CricketMatchModel matchModel = getCricketMatchModel();
		team1.clear();
		team2.clear();
		createEnterTeam(matchModel.getTeams().get(0).getTeamName(), true);
		createEnterTeam(matchModel.getTeams().get(1).getTeamName(), false);
		createMatchDetailsPanel();
		populateFromModel();
	}

	/**
	 * Create the Component to enter the value
	 * @param teamName
	 * @param isTeam1
	 */
	public void createEnterTeam(String teamName, boolean isTeam1) {
		Label teamNameLabel = new Label(teamName);
		if (isTeam1) {
			team1.add(teamNameLabel);
			team1Names = new TextBox[15];
		} else {
			team2.add(teamNameLabel);
			team2Names = new TextBox[15];
		}
		for (int i = 0; i < 15; i++) {
			TextBox name = new TextBox();
			name.setWidth("300px");
			name.setStyleName("teamNameTextBox");
			if (isTeam1) {
				team1Names[i] = name;
				team1.add(name);
			} else {
				team2Names[i] = name;
				team2.add(name);
			}
		}
	}

	/**
	 * Populate the existing values from the model
	 */
	private void populateFromModel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		List<TeamMemberModel> team1List = matchModel.getTeams().get(0)
				.getTeamList();
		List<TeamMemberModel> team2List = matchModel.getTeams().get(1)
				.getTeamList();
		//boolean isTeamEditPossible = !(matchModel.getStatus() == MatchStatus.SCORE_CARD_ENTERED.getId());
		//populateMember(team1List, team1Names, isTeamEditPossible);
		//populateMember(team2List, team2Names, isTeamEditPossible);

	}

	private void populateMember(List<TeamMemberModel> teamList,
			TextBox[] teamNameTxt,  boolean isTeamEditPossible) {
		
		int index = 0;
		if (teamList == null) {
			return;
		}
		for (TeamMemberModel player : teamList) {
			if (!ClientUtil.isEmpty(player.getPlayerName())) {
				teamNameTxt[index].setText(player.getPlayerName());
				teamNameTxt[index].setEnabled(isTeamEditPossible);
				index++;
			}
		}
	}

	private void createMatchDetailsPanel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		tossWonBy.addItem(matchModel.getTeams().get(0).getTeamName());
		tossWonBy.addItem(matchModel.getTeams().get(1).getTeamName());
		chooseTo.addItem("Batting");
		chooseTo.addItem("Bowling");
		tossWonByLabel.setText("Toss Won By:");
		chooseToLabel.setText("Elected To: ");
		teamPanelHeaderLabel.setText("Enter Teams: ");
	}

	/**
	 * Populates Team Model
	 */
	private void fillTeamModel() {
		CricketMatchModel matchModel = getCricketMatchModel();
		if (matchModel != null) {
			List<TeamModel> teams = matchModel.getTeams();
			TeamModel team = teams.get(0);
			List<TeamMemberModel> memberList = new ArrayList<TeamMemberModel>();
			for (TextBox memberName : team1Names) {
				if (memberName == null)
					continue;
				if (memberName.getText() != null
						&& !"".equals(memberName.getText())) {
					TeamMemberModel memberModel = new TeamMemberModel();
					memberModel.setPlayerName(memberName.getText());
					memberList.add(memberModel);
				}
			}
			team.setTeamList(memberList);
			TeamModel team2 = teams.get(1);
			List<TeamMemberModel> memberList2 = new ArrayList<TeamMemberModel>();
			for (TextBox memberName : team2Names) {
				if (memberName == null)
					continue;
				if (memberName.getText() != null
						&& !"".equals(memberName.getText())) {
					TeamMemberModel memberModel = new TeamMemberModel();
					memberModel.setPlayerName(memberName.getText());
					memberList2.add(memberModel);
				}
			}
			team2.setTeamList(memberList2);
		}
		matchModel
				.setTossWonBy(tossWonBy.getValue(tossWonBy.getSelectedIndex()));
		matchModel.setChooseTo(chooseTo.getValue(chooseTo.getSelectedIndex()));
		matchModel.setTeamIndex(tossWonBy.getSelectedIndex());
		//matchModel.setStatus(MatchStatus.ENTERED_TEAM.getId());

	}

	@Override
	public boolean validateTab() {
		if(!isInitialized) {
			return false;
		}
		CricketMatchModel matchModel = getCricketMatchModel();
		boolean isValidated = false;
		if (matchModel.getTeams().get(0).getTeamList() != null
				&& matchModel.getTeams().get(0).getTeamList().size() >= 11
				&& matchModel.getTeams().get(0).getTeamList() != null
				&& matchModel.getTeams().get(0).getTeamList().size() >= 11) {
			isValidated = true;
		}
		return isValidated;
	}

	@Override
	public void populateMatch() {
		// If not initialized don't populate anything
		if (!isInitialized()) {
			return;
		}
		fillTeamModel();
	}
	

}
