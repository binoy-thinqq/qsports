package com.thinqq.qsports.client.cricket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.shared.CricketMatchModel;

public class CricketViewImpl extends Composite implements CricketView {

	private static CricketViewImplUiBinder uiBinder = GWT
			.create(CricketViewImplUiBinder.class);

	private Presenter listener;

	@UiField
	Button completeMatch;

	@UiField
	Button saveButton;

	@UiField
	TabPanel scorecardTabPanel;

	@UiField
	EnterMatchDetails enterMatchDetails;

	@UiField
	EnterTeamDetails enterTeamDetails;

	@UiField
	EnterScoreCardDetails enterScorecardDetails;

	@UiField
	Label errorMsg;

	String matchid;

	interface CricketViewImplUiBinder extends UiBinder<Widget, CricketViewImpl> {
	}

	public CricketViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		completeMatch.setText("Complete");
		saveButton.setText("Save");
		completeMatch.setEnabled(false);
		errorMsg.setVisible(false);
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveMatch();
				System.out.println("test");
			}
		});
	}

	public void initializeView(CricketMatchModel matchModel) {
		enterMatchDetails.initialize();
		enterMatchDetails.setInitialized(true);
		enableTabs(0, true);
		scorecardTabPanel.getTabBar().selectTab(0);
		validateMatch();
	}

	/**
	 * Enable tabs based on the validation on the previous tab
	 * @param tabIndex
	 * @param enable
	 */
	private void enableTabs(int tabIndex,boolean enable) {
		scorecardTabPanel.getTabBar().setTabEnabled(tabIndex, enable);
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		enterMatchDetails.setPresenter(listener);
		enterTeamDetails.setPresenter(listener);
		enterScorecardDetails.setPresenter(listener);
	}

	public void setMatchId(String matchId) {
		this.matchid = matchId;
	}


	public CricketMatchModel getMatchModel() {
		return listener.getCricketMatch();
	}

	public void showErrorMsg(String message) {
		this.errorMsg.setText(message);
		this.errorMsg.setVisible(true);
	}
	

	@Override
	public boolean validateMatch() {
		boolean isValidMatchDetails = enterMatchDetails.validateTab();
		//Initialize Enter Team Tab if the validate is successful for Tab1
		if (isValidMatchDetails) {
			enterTeamDetails.initialize();
		}
		enterTeamDetails.setInitialized(isValidMatchDetails);
		
		boolean isValidTeamDetails = enterTeamDetails.validateTab();
		if (isValidTeamDetails) {
			enterScorecardDetails.initialize();
		}
		enterScorecardDetails.setInitialized(isValidTeamDetails);
		
		boolean isValidScorecardDetails = enterScorecardDetails.validateTab();
		enableTabs(1,isValidMatchDetails);
		enableTabs(2,isValidTeamDetails);
		return isValidMatchDetails && isValidTeamDetails && isValidScorecardDetails;
	}

	@Override
	public void saveMatch() {
		enterMatchDetails.populateMatch();
		enterTeamDetails.populateMatch();
		enterScorecardDetails.populateMatch();
		if (validateMatch()) {
			//Enable Complete Match Button
		} 
		listener.saveMatch(listener.getCricketMatch());
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
