package com.thinqq.qsports.client.cricket.matchview.bowlingcard;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.IComponent;
import com.thinqq.qsports.client.cricket.matchview.battingentry.BattingEntry;

public class BowlingCard extends Composite implements IComponent<BowlingCardModel, BowlingCardResource>{

	private static final Binder binder = GWT.create(Binder.class);
	private static final BowlingCardResource.BattingCardConstants constants = GWT.create(BowlingCardResource.BattingCardConstants.class);
	
	/** Maintains batting entries in sequence Order */
	private Map<Integer,BattingEntry> entriesMap = new TreeMap<Integer,BattingEntry>();
	@UiField
	Label teamName;
	
	
	@UiField
	VerticalPanel bowlingEntries;
	
	interface Binder extends UiBinder<Widget, BowlingCard> {
	}

	public BowlingCard() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setData(BowlingCardModel model) {}

	@Override
	public void cleanData() {}

	@Override
	public void refreshdata(BowlingCardModel model) {}

	@Override
	public void initialiseComponent(BowlingCardResource style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reInitialiseComponent(BowlingCardResource style) {
		// TODO Auto-generated method stub
		
	}

}
