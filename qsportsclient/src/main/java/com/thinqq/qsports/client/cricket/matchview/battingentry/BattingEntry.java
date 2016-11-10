package com.thinqq.qsports.client.cricket.matchview.battingentry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.IComponent;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.shared.OutTypeEnum;

public class BattingEntry extends Composite implements
		IComponent<BattingEntryModel, BattingEntryResource> {
	private BattingEntryResource resource = GWT.create(BattingEntryResource.class);
	private BattingEntryResource.Style style = resource.style();
	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	Label captain;
	@UiField
	Label wicketKeeper;
	@UiField
	Label strikeMarker;
	@UiField
	Hyperlink batsman;
	@UiField
	Label fielder1Label;
	@UiField
	Hyperlink fielder1;
	@UiField
	Label bowlerOrFielder2Label;
	@UiField
	Hyperlink bowlerOrFielder2;
	@UiField
	Label runs;
	@UiField
	Label ballsFaced;
	@UiField
	Label strikeRate;
	@UiField
	Label fours;
	@UiField
	Label sixes;
	@UiField
	HorizontalPanel battingEntry;
	
	interface Binder extends UiBinder<Widget, BattingEntry> {
	}

	public BattingEntry() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setData(BattingEntryModel model) {
		if (model.getBatsman().isCaptain()) {
			captain.setVisible(true);
		}
		if (model.getBatsman().isWicketKeeper()) {
			wicketKeeper.setVisible(true);
		}
		if (model.getBatsman().isInStrike()) {
			strikeMarker.setVisible(true);
		}
		batsman.setText(model.getBatsman().getDisplayName());
		batsman.setTargetHistoryToken(UserProfilePlace.TOKENIZER
				.getToken(new UserProfilePlace(model.getBatsman()
						.getUserId().toString())));
		OutTypeEnum outType = model.getOutType();
		if (outType != null) {
			if (outType.equals(OutTypeEnum.OBSTRUCTING_THE_FIELD)
					|| outType.equals(OutTypeEnum.TIMED_OUT)
					|| outType.equals(OutTypeEnum.HANDLED_THE_BALL)
					|| outType.equals(OutTypeEnum.HIT_THE_BALL_TWICE)
					|| outType.equals(OutTypeEnum.BOWLED)
					|| outType.equals(OutTypeEnum.HIT_WICKET)
					|| outType.equals(OutTypeEnum.LEG_BEFORE_WICKET)) {
				fielder1.setVisible(false);
				fielder1Label.setVisible(false);
			} else {
				fielder1Label.setText(Character.toString(outType
						.getCharRepresentation()));
				fielder1Label.setVisible(true);
				fielder1.setText(model.getFielder1().getDisplayName());
				fielder1.setTargetHistoryToken(UserProfilePlace.TOKENIZER
						.getToken(new UserProfilePlace(model.getFielder1()
								.getUserId().toString())));
				fielder1.setVisible(true);
			}
			if (outType.equals(OutTypeEnum.CAUGHT)
					|| outType.equals(OutTypeEnum.BOWLED)
					|| outType.equals(OutTypeEnum.HIT_WICKET)
					|| outType.equals(OutTypeEnum.LEG_BEFORE_WICKET)
					|| outType.equals(OutTypeEnum.STUMPTED)) {
				bowlerOrFielder2Label.setText(Character
						.toString(OutTypeEnum.BOWLED.getCharRepresentation()));
				bowlerOrFielder2Label.setVisible(true);
				bowlerOrFielder2.setText(model.getFielder1().getDisplayName());
				bowlerOrFielder2
						.setTargetHistoryToken(UserProfilePlace.TOKENIZER
								.getToken(new UserProfilePlace(model
										.getFielder1().getUserId().toString())));
				bowlerOrFielder2.setVisible(true);
			} else if (outType.equals(OutTypeEnum.RUN_OUT)) {
				bowlerOrFielder2Label.setText(Character.toString(outType
						.getCharRepresentation()));
				bowlerOrFielder2Label.setVisible(true);
				bowlerOrFielder2.setText(model.getFielder1().getDisplayName());
				bowlerOrFielder2
						.setTargetHistoryToken(UserProfilePlace.TOKENIZER
								.getToken(new UserProfilePlace(model
										.getFielder1().getUserId().toString())));
				bowlerOrFielder2.setVisible(true);
			} else {
				bowlerOrFielder2Label.setVisible(false);
				bowlerOrFielder2.setVisible(false);
			}
		}
		if(model.getRuns() > 0 || model.getBallsFaced() > 0){
		runs.setText(Integer.toString(model.getRuns()));
		ballsFaced.setText(Integer.toString(model.getBallsFaced()));
		strikeRate.setText(Float.toString(model.getStrikeRate()));
		fours.setText(Integer.toString(model.getFours()));
		sixes.setText(Integer.toString(model.getSixes()));
		}
		if (model.getBatsman().isPlaying()) {
			battingEntry.setStylePrimaryName(style.battingEntryGreen());
		} else {
			battingEntry.setStylePrimaryName(style.battingEntry());
		}
	}

	@Override
	public void cleanData() {
		captain.setVisible(false);
		wicketKeeper.setVisible(false);
		strikeMarker.setVisible(false);
		batsman.setText("");
		fielder1.setVisible(false);
		fielder1Label.setVisible(false);
		bowlerOrFielder2Label.setVisible(false);
		bowlerOrFielder2.setVisible(false);
		runs.setText("");
		ballsFaced.setText("");
		strikeRate.setText("");
		fours.setText("");
		sixes.setText("");
	}

	@Override
	public void refreshdata(BattingEntryModel model) {
		if(model.getRuns() > 0 || model.getBallsFaced() > 0){
		runs.setText(Integer.toString(model.getRuns()));
		ballsFaced.setText(Integer.toString(model.getBallsFaced()));
		strikeRate.setText(Float.toString(model.getStrikeRate()));
		fours.setText(Integer.toString(model.getFours()));
		sixes.setText(Integer.toString(model.getSixes()));
		}
		if (model.getBatsman().isPlaying()) {
			battingEntry.setStylePrimaryName(style.battingEntryGreen());
		} else {
			battingEntry.setStylePrimaryName(style.battingEntry());
		}
	}

	@Override
	public void initialiseComponent(BattingEntryResource style) {
		//NO-OP now
	}

	@Override
	public void reInitialiseComponent(BattingEntryResource style) {
		//NO-OP now
	}

}
