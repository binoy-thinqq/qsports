package com.thinqq.qsports.client.cricket;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.thinqq.qsports.client.comp.suggestbox.TSuggestRequestOracle;
import com.thinqq.qsports.client.helper.ClientUtil;
import com.thinqq.qsports.shared.Constants;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.TeamModel;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;

/**
 * 
 * Creates Match details section
 */
public class EnterMatchDetails extends CricketMatchTab {

	private static EnterMatchDetailsUiBinder uiBinder = GWT
			.create(EnterMatchDetailsUiBinder.class);

	interface EnterMatchDetailsUiBinder extends
			UiBinder<Widget, EnterMatchDetails> {
	}

	@UiField
	Button pickdate;

	@UiField
	HTMLPanel myteampanel;
	SuggestBox myteam;

	@UiField
	TextBox oppteam;

	@UiField
	TextBox venue;

	@UiField
	TextBox location;

	@UiField
	TextBox matchdate;

	@UiField
	TextBox email;

	@UiField
	TextBox contact;

	@UiField
	ListBox format;

	
	public EnterMatchDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void initialize() {
		TSuggestRequestOracle suggestOracle = new TSuggestRequestOracle(1, 2);
		myteam = new SuggestBox(suggestOracle);
		addActionHandlers();
		setFormatSelection();
		myteampanel.add(myteam);
		matchdate.setEnabled(false);
		setStyles();
	}

	private void addActionHandlers() {
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
		simplePopup.setWidth("150px");
		DatePicker datePicker = new DatePicker();
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				String dateString = DateTimeFormat.getMediumDateTimeFormat()
						.format(date);
				matchdate.setText(dateString);
				simplePopup.hide();
			}
		});
		simplePopup.setWidget(datePicker);
		pickdate.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 10;
				simplePopup.setPopupPosition(left, top);
				// Show the popup
				simplePopup.show();

			}
		});
	}

	private void setStyles() {
		oppteam.setStyleName("matchCreate_textbox");
		myteam.setStyleName("matchCreate_textbox");
		venue.setStyleName("matchCreate_textbox");
		contact.setStyleName("matchCreate_textbox");
		location.setStyleName("matchCreate_textbox");
		email.setStyleName("matchCreate_textbox");
	}

	public EnterMatchDetails(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void setFormatSelection() {
		for (MatchFormatEnum formatEnum : MatchFormatEnum.values()) {
			format.addItem(formatEnum.getName(), formatEnum.getId().toString());
		}
		format.setSelectedIndex(0);
	}


	@Override
	public boolean validateTab() {
		if(!isInitialized) {
			return false;
		}
		boolean isValidated = true;
		if (ClientUtil.isEmpty(oppteam.getText())
				|| ClientUtil.isEmpty(myteam.getText())
				|| ClientUtil.isEmpty(venue.getText())
				|| ClientUtil.isEmpty(location.getText())
				|| ClientUtil.isEmpty(matchdate.getText())
				) {
			return false;
		}
		return isValidated;
	}

	@Override
	public void populateMatch() {

		//If not initialized dont populate anything
		if(!isInitialized()) {
			return;
		}
		CricketMatchModel matchModel = getCricketMatchModel();
		if (matchModel.getTeams() == null) {
			TeamModel team1 = new TeamModel();
			team1.setTeamName(myteam.getText());
			TeamModel team2 = new TeamModel();
			team2.setTeamName(oppteam.getText());
			List<TeamModel> teams = new ArrayList<TeamModel>();
			teams.add(team1);
			teams.add(team2);
			matchModel.setTeams(teams);
		}
		matchModel.setDate(new Date(matchdate.getText()));
		matchModel.setVenue(venue.getText());
		matchModel.setLocation(location.getText());
		matchModel.setFormat(format.getItemText(format.getSelectedIndex()));
		matchModel.setNoOfInnings(Constants.formatInningsMap.get(MatchFormatEnum
				.getEnum(matchModel.getFormat())));
		//matchModel.setStatus(MatchStatus.MATCH_CREATED.getId());
	}

}
