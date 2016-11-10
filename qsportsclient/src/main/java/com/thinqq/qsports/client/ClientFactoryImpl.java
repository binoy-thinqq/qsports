package com.thinqq.qsports.client;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.thinqq.qsports.client.common.MessagePopUp;
import com.thinqq.qsports.client.confirmation.Confirmation;
import com.thinqq.qsports.client.confirmation.ConfirmationImpl;
import com.thinqq.qsports.client.cricket.matchview.CricketMatchView;
import com.thinqq.qsports.client.cricket.matchview.CricketMatchViewImpl;
import com.thinqq.qsports.client.home.QSportsHome;
import com.thinqq.qsports.client.home.QSportsHomeImpl;
import com.thinqq.qsports.client.initalise.InitialiseView;
import com.thinqq.qsports.client.initalise.InitialiseViewImpl;
import com.thinqq.qsports.client.login.LoginView;
import com.thinqq.qsports.client.login.LoginViewImpl;
import com.thinqq.qsports.client.registration.RegistrationView;
import com.thinqq.qsports.client.registration.RegistrationViewImpl;
import com.thinqq.qsports.client.teamprofile.AddTeamPlayer;
import com.thinqq.qsports.client.teamprofile.EditTeamProfile;
import com.thinqq.qsports.client.teamprofile.TeamProfileView;
import com.thinqq.qsports.client.teamprofile.TeamProfileViewImpl;
import com.thinqq.qsports.client.userprofile.EditUserProfileView;
import com.thinqq.qsports.client.userprofile.EditUserProfileViewImpl;
import com.thinqq.qsports.client.userprofile.UserProfileView;
import com.thinqq.qsports.client.userprofile.UserProfileViewImpl;
import com.thinqq.qsports.client.userprofile.match.edit.CreateNewMatch;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditView;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditViewImpl;
import com.thinqq.qsports.client.userprofile.match.edit.SelectTeamPopUp;
import com.thinqq.qsports.client.userprofile.match.edit.StartMatchPopup;
import com.thinqq.qsports.client.wireframe.QSportsWireFrame;
import com.thinqq.qsports.client.wireframe.QSportsWireFrameImpl;

public class ClientFactoryImpl implements ClientFactory {
    private final EventBus eventBus = new SimpleEventBus();    
    private final PlaceController placeController = new PlaceController(eventBus);    
    QSportsHome home = new QSportsHomeImpl();
    QSportsWireFrame wireFrame = new QSportsWireFrameImpl(this);
    LoginView loginView = new LoginViewImpl();
    RegistrationView registrationView = new RegistrationViewImpl();
    UserProfileView userProfileView = new UserProfileViewImpl();
    EditUserProfileView editUserProfileView = new EditUserProfileViewImpl();
    InitialiseView initView = new InitialiseViewImpl();
    TeamProfileView teamView = new TeamProfileViewImpl();
    /*CricketView cricketView = new CricketViewImpl();*/
    CricketMatchView cricketMatchView = new CricketMatchViewImpl();
    Confirmation confirmationView = new ConfirmationImpl();
    EditTeamProfile editTeamProfileView = new EditTeamProfile();
    AddTeamPlayer addTeamPlayerView = new AddTeamPlayer();
    CreateNewMatch createNewMatch = new CreateNewMatch();
    CricketMatchEditView cricketMatchEditView = new CricketMatchEditViewImpl();
    MessagePopUp messagePopUp = new MessagePopUp();
    SelectTeamPopUp selectTeamPopup = new SelectTeamPopUp();
    StartMatchPopup startMatchPopup = new StartMatchPopup();
    
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public QSportsHome getThinqqHome() {
		home.clean();
		return home;
	}

	@Override
	public QSportsWireFrame getThinqQWireFrame() {
		wireFrame.clean();
		return wireFrame;
	}

	@Override
	public LoginView getLoginView() {
		loginView.clean();
		return loginView;
	}

	@Override
	public RegistrationView getRegistrationView() {
		registrationView.clean();
		return registrationView;
	}

	@Override
	public UserProfileView getUserProfileView() {
		userProfileView.clean();
		return userProfileView;
	}

	/*@Override
	public CricketView getCricketView() {
		cricketView.clean();
		return cricketView;
	}*/


	@Override
	public CricketMatchView getCricketMatchView() {
		cricketMatchView.clean();
		return cricketMatchView;
	}


	public EditUserProfileView getEditUserProfileView(){
		editUserProfileView.clean();
		return editUserProfileView;
	}

	@Override
	public InitialiseView getInitialiseView() {
		initView.clean();
		return initView;
	}

	@Override
	public TeamProfileView getTeamProfileView() {
		teamView.clean();
		return teamView;
	}

	@Override
	public Confirmation getConfirmation() {
		confirmationView.clean();
		return confirmationView;
	}

	@Override
	public EditTeamProfile getEditTeamProfilePopUp() {
		editTeamProfileView.clean();
		return editTeamProfileView;
	}

	@Override
	public AddTeamPlayer getAddTeamPlayerPopUp() {
		addTeamPlayerView.clean();
		return addTeamPlayerView;
	}

	@Override
	public CreateNewMatch getCreateNewMatchPopUp() {
		createNewMatch.clean();
		return createNewMatch;
	}

	@Override
	public CricketMatchEditView getCricketMatchEditView() {
		cricketMatchEditView.clean();
		return cricketMatchEditView;
	}

	@Override
	public MessagePopUp getMessagePopUp() {
		return messagePopUp;
	}

	@Override
	public SelectTeamPopUp getSelectTeamPopUp() {
		selectTeamPopup.clean();
		return selectTeamPopup;
	}

	@Override
	public StartMatchPopup getStartMatchPopUp() {
		startMatchPopup.clean();
		return startMatchPopup;
	}

}
