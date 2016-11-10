package com.thinqq.qsports.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.thinqq.qsports.client.common.MessagePopUp;
import com.thinqq.qsports.client.confirmation.Confirmation;
import com.thinqq.qsports.client.cricket.matchview.CricketMatchView;
import com.thinqq.qsports.client.home.QSportsHome;
import com.thinqq.qsports.client.initalise.InitialiseView;
import com.thinqq.qsports.client.login.LoginView;
import com.thinqq.qsports.client.registration.RegistrationView;
import com.thinqq.qsports.client.teamprofile.AddTeamPlayer;
import com.thinqq.qsports.client.teamprofile.EditTeamProfile;
import com.thinqq.qsports.client.teamprofile.TeamProfileView;
import com.thinqq.qsports.client.userprofile.EditUserProfileView;
import com.thinqq.qsports.client.userprofile.UserProfileView;
import com.thinqq.qsports.client.userprofile.match.edit.CreateNewMatch;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditView;
import com.thinqq.qsports.client.userprofile.match.edit.SelectTeamPopUp;
import com.thinqq.qsports.client.userprofile.match.edit.StartMatchPopup;
import com.thinqq.qsports.client.wireframe.QSportsWireFrame;

public interface ClientFactory {
	public EventBus getEventBus();

	public PlaceController getPlaceController();

	public QSportsHome getThinqqHome();
	public QSportsWireFrame getThinqQWireFrame();
//	public SofwareDD getSofwareDD();

	public LoginView getLoginView();
	public RegistrationView getRegistrationView();
	public UserProfileView getUserProfileView();
	//public CricketView getCricketView();

	public CricketMatchView getCricketMatchView();

	
	public EditUserProfileView getEditUserProfileView();

	public InitialiseView getInitialiseView();
	public TeamProfileView getTeamProfileView();
	public Confirmation getConfirmation();
	public EditTeamProfile getEditTeamProfilePopUp();
	public AddTeamPlayer getAddTeamPlayerPopUp();
	public CreateNewMatch getCreateNewMatchPopUp();
	public CricketMatchEditView getCricketMatchEditView();
	
	public MessagePopUp getMessagePopUp();
	
	public SelectTeamPopUp getSelectTeamPopUp();
	
	public StartMatchPopup getStartMatchPopUp();
}
