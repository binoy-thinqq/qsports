package com.thinqq.qsports.client.teamprofile;

import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.events.CreateNewTeamEvent;
import com.thinqq.qsports.client.events.CreateNewTeamEventHandler;

public class CreateNewTeamEventHandlerImpl implements CreateNewTeamEventHandler {

	@Override
	public void createNewTeamEvent(CreateNewTeamEvent event) {
		EditTeamProfile createTeamPopup = Genie.getClientfactory().getEditTeamProfilePopUp();
		createTeamPopup.setListener(EditTeamProfilePresenter.getInstance());
		createTeamPopup.setPixelSize(500, 300);
		createTeamPopup.center();
	}


}
