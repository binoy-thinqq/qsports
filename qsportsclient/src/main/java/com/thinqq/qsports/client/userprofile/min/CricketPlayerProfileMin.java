package com.thinqq.qsports.client.userprofile.min;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ConfirmationPopUp;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.shared.NameAndKey;

public class CricketPlayerProfileMin extends Composite implements ICleanable{

	public interface Presenter{
		void onRemoveProfile(String crickProfileKey);
		void onMakeOwner(String crickProfileKey);
	}
	private Presenter listener;
	private static final Binder binder = GWT.create(Binder.class);
	private static final String NAME_SUFFIX = "'s Statistics";
	private NameAndKey user;
	private String cricketProfileKey;
	interface Binder extends UiBinder<Widget, CricketPlayerProfileMin> {
	}
	@UiField
	Label name;
	
	@UiField
	Button viewProfile;
	
	@UiField
	Button removeProfile;
	
	@UiField
	Button makeOwner;
	
	@UiField
	Label matchesT20;

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
	Label matchesOD;

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
	Label matchesTst;

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
	Image loadingProfileImage;
	
	public void setBattingStatistics(List<String> t20Batting,
			List<String> oDBatting, List<String> testBatting) {
		matchesT20.setText(t20Batting.get(0));
		runsT20.setText(t20Batting.get(1));
		strikeRateT20.setText(t20Batting.get(2));
		averBatT20.setText(t20Batting.get(3));
		highScoreT20.setText(t20Batting.get(4));
		hundredsT20.setText(t20Batting.get(5));
		fiftiesT20.setText(t20Batting.get(6));

		
		matchesOD.setText(oDBatting.get(0));
		runsOD.setText(oDBatting.get(1));
		strikeRateOD.setText(oDBatting.get(2));
		averBatOD.setText(oDBatting.get(3));
		highScoreOD.setText(oDBatting.get(4));
		hundredsOD.setText(oDBatting.get(5));
		fiftiesOD.setText(oDBatting.get(6));
		
		matchesTst.setText(testBatting.get(0));
		runsTst.setText(testBatting.get(1));
		strikeRateTst.setText(testBatting.get(2));
		averBatTst.setText(testBatting.get(3));
		highScoreTst.setText(testBatting.get(4));
		hundredsTst.setText(testBatting.get(5));
		fiftiesTst.setText(testBatting.get(6));
		loadingProfileImage.setVisible(false);
	}
	
	@UiField
	Label b_matchesT20;
	@UiField
	Label b_wicketsT20;
	@UiField
	Label b_avgT20;
	@UiField
	Label b_econT20;
	@UiField
	Label b_5WicketsT20;
	@UiField
	Label b_bestBowlingT20;
	@UiField
	Label b_CatchesT20;
	
	@UiField
	Label b_matchesOD;
	@UiField
	Label b_wicketsOD;
	@UiField
	Label b_avgOD;
	@UiField
	Label b_econOD;
	@UiField
	Label b_5WicketsOD;
	@UiField
	Label b_bestBowlingOD;
	@UiField
	Label b_CatchesOD;
	
	@UiField
	Label b_matchesTest;
	@UiField
	Label b_wicketsTest;
	@UiField
	Label b_avgTest;
	@UiField
	Label b_econTest;
	@UiField
	Label b_5WicketsTest;
	@UiField
	Label b_bestBowlingTest;
	@UiField
	Label b_CatchesTest;
	
	public void setBowlingStatistics(List<String> t20Bowling,
			List<String> oDBowling, List<String> testBowling) {
		b_matchesT20.setText(t20Bowling.get(0));
		b_wicketsT20.setText(t20Bowling.get(1));
		b_avgT20.setText(t20Bowling.get(2));
		b_econT20.setText(t20Bowling.get(3));
		b_5WicketsT20.setText(t20Bowling.get(4));
		b_bestBowlingT20.setText(t20Bowling.get(5));
		b_CatchesT20.setText(t20Bowling.get(6));

		
		b_matchesOD.setText(oDBowling.get(0));
		b_wicketsOD.setText(oDBowling.get(1));
		b_avgOD.setText(oDBowling.get(2));
		b_econOD.setText(oDBowling.get(3));
		b_5WicketsOD.setText(oDBowling.get(4));
		b_bestBowlingOD.setText(oDBowling.get(5));
		b_CatchesOD.setText(oDBowling.get(6));
		
		b_matchesTest.setText(testBowling.get(0));
		b_wicketsTest.setText(testBowling.get(1));
		b_avgTest.setText(testBowling.get(2));
		b_econTest.setText(testBowling.get(3));
		b_5WicketsTest.setText(testBowling.get(4));
		b_bestBowlingTest.setText(testBowling.get(5));
		b_CatchesTest.setText(testBowling.get(6));
		loadingProfileImage.setVisible(false);
		
	}
	
	public void setUser(NameAndKey user){
		this.user = user;
		name.setText(user.getDisplayName()+NAME_SUFFIX);
		viewProfile.setVisible(true);
	}
	
	public CricketPlayerProfileMin() {
		initWidget(binder.createAndBindUi(this));
		viewProfile.setVisible(false);
		viewProfile.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				if(user!=null){
					Genie.getPlacecontroller().goTo(new UserProfilePlace(user.getKey()));
				}
			}
		});
		makeOwner.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				ConfirmationPopUp popUp = new ConfirmationPopUp(
						"Add Owner", 
						"Are you sure you want to add this player as owner?", 
						new ClickHandler() {								
							@Override
							public void onClick(ClickEvent event) {
								if(listener !=null){
									listener.onMakeOwner(cricketProfileKey);
								}
							}
						});
				popUp.show();
				popUp.center();
				
			}
		});
		removeProfile.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				ConfirmationPopUp popUp = new ConfirmationPopUp(
						"Remove Player", 
						"Are you sure you want to remove this player from the team?", 
						new ClickHandler() {								
							@Override
							public void onClick(ClickEvent event) {
								if(listener !=null){
									listener.onRemoveProfile(cricketProfileKey);
								}
							}
						});
				popUp.show();
				popUp.center();
			}
		});
	}

	@Override
	public void clean() {
		user = null;
		name.setText("Select a player");
		viewProfile.setVisible(false);
		b_matchesT20.setText("-");
		b_wicketsT20.setText("-");
		b_avgT20.setText("-");
		b_econT20.setText("-");
		b_5WicketsT20.setText("-");
		b_bestBowlingT20.setText("-");
		b_CatchesT20.setText("-");

		
		b_matchesOD.setText("-");
		b_wicketsOD.setText("-");
		b_avgOD.setText("-");
		b_econOD.setText("-");
		b_5WicketsOD.setText("-");
		b_bestBowlingOD.setText("-");
		b_CatchesOD.setText("-");
		
		b_matchesTest.setText("-");
		b_wicketsTest.setText("-");
		b_avgTest.setText("-");
		b_econTest.setText("-");
		b_5WicketsTest.setText("-");
		b_bestBowlingTest.setText("-");
		b_CatchesTest.setText("-");
		loadingProfileImage.setVisible(true);
	}

	public Presenter getListener() {
		return listener;
	}

	public void setListener(Presenter listener) {
		this.listener = listener;
	}

	public String getCricketProfileKey() {
		return cricketProfileKey;
	}

	public void setCricketProfileKey(String cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}

}
