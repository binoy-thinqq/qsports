package com.thinqq.qsports.client.cricket.matchview.battingcard;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.common.IComponent;
import com.thinqq.qsports.client.cricket.matchview.battingentry.BattingEntry;
import com.thinqq.qsports.client.cricket.matchview.battingentry.BattingEntryModel;
import com.google.gwt.user.client.ui.FlowPanel;

public class BattingCard extends Composite implements IComponent<BattingCardModel, BattingCardResource>{

	private static final Binder binder = GWT.create(Binder.class);
	private static final BattingCardResource.BattingCardConstants constants = GWT.create(BattingCardResource.BattingCardConstants.class);
	
	/** Maintains batting entries in sequence Order */
	private Map<Integer,BattingEntry> entriesMap = new TreeMap<Integer,BattingEntry>();
	@UiField
	Image inProgressImage;
	
	@UiField
	Label teamName;
	
	@UiField
	Label noBalls;
	
	@UiField
	Label wides;
	
	@UiField
	Label byes;
	
	@UiField
	Label legByes;
	
	@UiField
	Label penaltyRuns;
	
	@UiField
	Label extrasTotal;
	
	@UiField
	Label grandTotal;
	
	@UiField
	Label totalOvers;
	
	@UiField
	VerticalPanel battingEntries;
	@UiField FlowPanel didNotBat;
	
	interface Binder extends UiBinder<Widget, BattingCard> {
	}

	public BattingCard() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setData(BattingCardModel model) {
		inProgressImage.setVisible(model.isInProgress());
		teamName.setText(model.getTeamName()+constants.battingHeading());
		Map<Long,BattingEntryModel> battingModelEntries = model.getBattingEntries();
		Set<Long> battingEntryIds = battingModelEntries.keySet();
		for(Long battingEntryId: battingEntryIds){
			BattingEntryModel entryModel = battingModelEntries.get(battingEntryId);
			BattingEntry battingEntryWidget = new BattingEntry();
			battingEntryWidget.setData(entryModel);
			entriesMap.put(entryModel.getSequence(), battingEntryWidget);
		}
		Set<Integer> battingSequence = entriesMap.keySet();
		for(Integer sequence : battingSequence){
			battingEntries.add(entriesMap.get(sequence));
		}
		noBalls.setText(Integer.toString(model.geteNoBalls()));
		wides.setText(Integer.toString(model.geteWides()));
		byes.setText(Integer.toString(model.geteByes()));
		legByes.setText(Integer.toString(model.geteLegByes()));
		penaltyRuns.setText(Integer.toString(model.getePenaltyRuns()));
		grandTotal.setText(Integer.toString(model.getTotal())+" / "+Integer.toString(model.getWickets()));
		totalOvers.setText("Total in "+model.getOvers()+"."+model.getBalls()+" overs - ");
	}

	@Override
	public void cleanData() {
		inProgressImage.setVisible(false);
		teamName.setText("");
		entriesMap.clear();
		battingEntries.clear();
		noBalls.setText("");
		wides.setText("");
		byes.setText("");
		legByes.setText("");
		penaltyRuns.setText("");
		grandTotal.setText("");
		totalOvers.setText("");
	}

	@Override
	public void refreshdata(BattingCardModel model) {
		noBalls.setText(Integer.toString(model.geteNoBalls()));
		wides.setText(Integer.toString(model.geteWides()));
		byes.setText(Integer.toString(model.geteByes()));
		legByes.setText(Integer.toString(model.geteLegByes()));
		penaltyRuns.setText(Integer.toString(model.getePenaltyRuns()));
		grandTotal.setText(Integer.toString(model.getTotal())+" / "+Integer.toString(model.getWickets()));
		totalOvers.setText("Total in "+model.getOvers()+"."+model.getBalls()+" overs - ");
		inProgressImage.setVisible(model.isInProgress());
		//Update running Ids and change sequence - if changed
	}

	@Override
	public void initialiseComponent(BattingCardResource style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reInitialiseComponent(BattingCardResource style) {
		// TODO Auto-generated method stub
		
	}

}
