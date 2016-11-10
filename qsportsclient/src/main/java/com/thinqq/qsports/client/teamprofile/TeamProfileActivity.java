package com.thinqq.qsports.client.teamprofile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.client.userprofile.UserProfileView;
import com.thinqq.qsports.client.userprofile.min.CricketPlayerProfileMin;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsResponseVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public class TeamProfileActivity extends AbstractActivity implements TeamProfileView.Presenter,AddTeamPlayer.Presenter,EditTeamProfile.Presenter,CricketPlayerProfileMin.Presenter {

	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;
	private String teamKey;
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	TeamProfileView view;
	public TeamProfileActivity(TeamProfilePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.teamKey = place.getTeamKey();
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getTeamProfileView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
		loadTeamDetails(false);
	}

	private void loadTeamDetails(final boolean loadOnlyDetails) {
		TeamProfileRequestVo request = new TeamProfileRequestVo();
		request.setTeamProfileKey(teamKey);
		secureServices.getTeamProfile(request, new AsyncCallback<TeamProfileResponseVo>() {
			
			@Override
			public void onSuccess(TeamProfileResponseVo response) {
				view.setTeamDetails(response);
				if(!loadOnlyDetails){
					loadTeamStatistics();
					loadTeamPlayersList();
				}
			}

			@Override
			public void onFailure(Throwable error) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);				
			}
		});
	}

	private void loadTeamPlayersList() {
		GetTeamPlayerProfileRequestVo request = new GetTeamPlayerProfileRequestVo();
		request.setTeamKey(teamKey);
		secureServices.getAllTeamPlayers(request, new AsyncCallback<GetTeamPlayerProfileResponseVo>() {

			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(GetTeamPlayerProfileResponseVo response) {
				view.setTeamPlayerList(response.getPlayersList());
			}
		});
		
	}
	private void loadTeamStatistics() {
		GetTeamStatisticsRequestVo request = new GetTeamStatisticsRequestVo();
		request.setTeamProfileKey(teamKey);
		secureServices.getTeamStatistics(request, new AsyncCallback<GetTeamStatisticsResponseVo>() {
			
			@Override
			public void onSuccess(GetTeamStatisticsResponseVo response) {
				view.setTeamStatistics(response);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);				
			}
		});
	}
	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see UserProfileView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onAddTeamPlayer(String profileKey) {
		AddTeamPlayerRequestVo request = new AddTeamPlayerRequestVo();
		request.setTeamKey(teamKey);
		request.setProfileKey(profileKey);
		secureServices.addTeamPlayer(request, new AsyncCallback<AddTeamPlayerResponseVo>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(AddTeamPlayerResponseVo result) {
				Window.alert("PlayerAdded Successfully "+result.getAlreadyExists());
				loadTeamPlayersList();
			}
		});
	}

	@Override
	public void onPlayerSelection(String cricketProfileKey, NameAndKey user) {
		loadPlayerMinimumStatistics(cricketProfileKey, user);
	}

	private void loadPlayerMinimumStatistics(final String cricketProfileKey, final NameAndKey user) {
		CricketStatisticsRequestVo request = new CricketStatisticsRequestVo();
		request.setCricketProfileKey(cricketProfileKey);
		request.setMinimumStatsReq(true);
		secureServices.getCricketStatistics(request, new AsyncCallback<CricketStatisticsResponseVo>() {
			@Override
			public void onFailure(Throwable error) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(CricketStatisticsResponseVo response) {
				view.setTeamPlayerStatistics(response.getT20Batting(), response.getoDBatting(), response.getTestBatting(),
						response.getT20Bowling(), response.getoDBowling(), response.getTestBowling(), user, response.getCricketProfileKey());
			}
		});
	}

	@Override
	public void onSearchPlayer(String email) {
		UserProfileRequestVo request = new UserProfileRequestVo();
		request.setEmail(email);
		request.setUseOnlyEmail(true);
		secureServices.getUserProfile(request, new AsyncCallback<UserProfileResponseVo>() {

			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(UserProfileResponseVo response) {
				view.setPlayerDetailsOnAddPlayerPopup(response);
			}
		});
	}

	@Override
	public void onEditTeamClick() {
		TeamProfileRequestVo request = new TeamProfileRequestVo();
		request.setTeamProfileKey(teamKey);
		secureServices.getTeamProfile(request, new AsyncCallback<TeamProfileResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(TeamProfileResponseVo details) {
				view.showEditTeamProfile(details, teamKey);
			}
		});
	}


	@Override
	public void onTeamSave(SaveTeamProfileRequestVo request) {
		request.setTeamKey(teamKey);
		secureServices.saveTeamProfile(request, new AsyncCallback<SaveTeamProfileResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(SaveTeamProfileResponseVo response) {
				loadTeamDetails(true);
			}
		});
	}

	@Override
	public void onRemoveOwner(RemoveTeamPlayerRequestVo request) {
		secureServices.removeTeamPlayer(request, new AsyncCallback<RemoveTeamPlayerResponseVo>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(RemoveTeamPlayerResponseVo result) {
				loadTeamDetails(true);
				if(result.getRemovedSuccessfully()){
					MessageUtil.showMessage(MessageUtil.SUCCESS, "Team ownership has been updated");
				} else if(result.getCannotRemoveTheOnlyOwner()){
					MessageUtil.showMessage(MessageUtil.WARNING, "Team should have at least one owner.");
				} else if(result.getAlreadyRemoved()){
					MessageUtil.showMessage(MessageUtil.WARNING, "Owner has already been removed");
				} else if(result.getNoOwnerPrivilege()){
					MessageUtil.showMessage(MessageUtil.WARNING, "You do not have the privilege to remove ownership");
				}
			}
		});
	}

	@Override
	public void onRemoveProfile(String cricketProfileKey) {
		RemoveTeamPlayerRequestVo request = new RemoveTeamPlayerRequestVo();
		request.setProfileKey(cricketProfileKey);
		request.setTeamKey(teamKey);
		secureServices.removeTeamPlayer(request, new AsyncCallback<RemoveTeamPlayerResponseVo>() {
			
			@Override
			public void onSuccess(RemoveTeamPlayerResponseVo response) {
				loadTeamPlayersList();
				loadTeamDetails(true);
				if(response.getRemovedSuccessfully()){
					MessageUtil.showMessage(MessageUtil.SUCCESS, "The player has been removed from the team. Player's past records will not be removed.");
				} else if(response.getCannotRemoveTheOnlyOwner()){
					MessageUtil.showMessage(MessageUtil.WARNING, "Team should have at least one owner.");
				} else if(response.getAlreadyRemoved()){
					MessageUtil.showMessage(MessageUtil.WARNING, "Player has already been removed");
				} else if(response.getNoOwnerPrivilege()){
					MessageUtil.showMessage(MessageUtil.WARNING, "You do not have the privilege to remove ownership");
				}

			}
			
			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}
		});
	}

	@Override
	public void onMakeOwner(String cricketProfileKey) {
		AddTeamPlayerRequestVo request = new AddTeamPlayerRequestVo();
		request.setAddAsOwner(true);
		request.setProfileKey(cricketProfileKey);
		request.setTeamKey(teamKey);
		secureServices.addTeamPlayer(request, new AsyncCallback<AddTeamPlayerResponseVo>() {

			@Override
			public void onFailure(Throwable arg0) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);	
			}

			@Override
			public void onSuccess(AddTeamPlayerResponseVo response) {
				loadTeamDetails(true);
				loadTeamPlayersList();
				if(response.getAddedSuccessfully()){
					MessageUtil.showMessage(MessageUtil.SUCCESS, "New Owner has been added successfully");
				} else if(response.getAlreadyExists()){
					MessageUtil.showMessage(MessageUtil.WARNING, "The player already is a owner");
				} else if(response.getNoOwnerPrivilege()){
					MessageUtil.showMessage(MessageUtil.WARNING, "You do not have the privilege to remove ownership");
				}
			}
			
		});
	}

}
