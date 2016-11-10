package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.events.CreateNewMatchEvent;
import com.thinqq.qsports.client.events.CreateNewMatchEventHandler;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo.TeamProfileMinDetails;

public class CreateNewMatchEventHandlerImpl implements CreateNewMatchEventHandler {

	@Override
	public void createNewMatchEvent(CreateNewMatchEvent event) {

		final CreateNewMatchPresenterImpl presenter = CreateNewMatchPresenterImpl.getInstance();
		presenter.getTeamsOwned(new AsyncCallback<GetTeamsForPlayerResponseVo>() {

			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(GetTeamsForPlayerResponseVo response) {
				List<NameAndKey> ownedTeams = new ArrayList<NameAndKey>();
				for(TeamProfileMinDetails playerDetails: response.getTeams()){
					ownedTeams.add(new NameAndKey(playerDetails.getName(), playerDetails.getTeamKey()));
				}
				if(ownedTeams.size()>0){
					CreateNewMatch createTeamPopup = Genie.getClientfactory().getCreateNewMatchPopUp();
					createTeamPopup.setMyTeams(ownedTeams);
					createTeamPopup.setListener(presenter);
					createTeamPopup.setPixelSize(800, 300);
					createTeamPopup.center();
				} else {
					MessageUtil.showMessage(MessageUtil.FAILURE, "Only owners of a team can create a match. You do not own any team :(");
				}
			}
		});

	}


}
