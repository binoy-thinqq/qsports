/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinqq.qsports.client.userprofile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.login.LoginPlace;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class UserProfileActivity extends AbstractActivity implements UserProfileView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;
	private String userKey;
	private UserProfileView view ;
	EditUserProfileView editUserProfileView;
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	public UserProfileActivity(UserProfilePlace place, ClientFactory clientFactory) {
		this.userKey= place.getUserKey();
		this.clientFactory = clientFactory;
		editUserProfileView = clientFactory.getEditUserProfileView();
		editUserProfileView.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
			view = clientFactory.getUserProfileView();
		if(userKey!=null && userKey.length()>0){
			UserProfileRequestVo userProfileRequest = new UserProfileRequestVo();
			userProfileRequest.setUserKey(userKey);
			secureServices.getUserProfile(userProfileRequest, new AsyncCallback<UserProfileResponseVo>() {
				@Override
				public void onSuccess(UserProfileResponseVo response) {
					view.setUserDetails(response);
					view.showEditPofileButton(response.isEditable());
					loadCricketStatistics(response.getCricketProfileKey());
					loadTeamsList(response.getCricketProfileKey());
				}
				@Override
				public void onFailure(Throwable arg0) {
						goTo(new LoginPlace("User Profile Fetch failed Unreasonably"));
				}
			});
		} else {
			if(Genie.getUser()!=null){
				setUserProfileDetails(Genie.getUser());
			}else{
				secureServices.getSignedInUserProfile(new AsyncCallback<UserProfileResponseVo>() {

					@Override
					public void onSuccess(UserProfileResponseVo response) {
						setUserProfileDetails(response);
					}
					@Override
					public void onFailure(Throwable error) {
						Window.alert("We are unable to identify you. You might have to login again");
					}
				});
			}
		}
		view.setName(name);
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}
	private void setUserProfileDetails(UserProfileResponseVo response){
		view.setUserDetails(response);
		view.showEditPofileButton(response.isEditable());
		loadCricketStatistics(response.getCricketProfileKey());
		loadTeamsList(response.getCricketProfileKey());
	}
	
	public void refreshUserDetails(String userKey){
		UserProfileRequestVo userProfileRequest = new UserProfileRequestVo();
		userProfileRequest.setUserKey(userKey);
		secureServices.getUserProfile(userProfileRequest, new AsyncCallback<UserProfileResponseVo>() {
			@Override
			public void onSuccess(UserProfileResponseVo response) {
				view.setUserDetails(response);
				view.showEditPofileButton(response.isEditable());
			}
			@Override
			public void onFailure(Throwable arg0) {
					goTo(new LoginPlace("User Profile Fetch failed"));
			}
		});
	}
	@Override
	public String mayStop() {
		return null;
	}

	private void loadTeamsList(String cricketProfileKey) {
		GetTeamsForPlayerRequestVo request = new GetTeamsForPlayerRequestVo();
		request.setCricketProfileKey(cricketProfileKey);
		secureServices.getAllTeamsForPlayer(request, new AsyncCallback<GetTeamsForPlayerResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				//show FATAL Error
			}

			@Override
			public void onSuccess(GetTeamsForPlayerResponseVo response) {
				view.setTeamsList(response.getTeams());
			}
		});
	}
	/**
	 * @see UserProfileView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

		private void loadCricketStatistics(String cricketProfileKey) {
		CricketStatisticsRequestVo request = new CricketStatisticsRequestVo();
		request.setCricketProfileKey(cricketProfileKey);
		secureServices.getCricketStatistics(request, new AsyncCallback<CricketStatisticsResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				// TODO Throw unable to reach the server error
			}

			@Override
			public void onSuccess(CricketStatisticsResponseVo response) {
				view.setBattingStatistics(response.getT20Batting(), response.getoDBatting(), response.getTestBatting());
				view.setBowlingStatistics(response.getT20Bowling(), response.getoDBowling(), response.getTestBowling());
			}
		});
		
	}

	@Override
	public void onEditProfileShow() {
		secureServices.getUserProfile(new UserProfileRequestVo(), new AsyncCallback<UserProfileResponseVo>() {
			
			@Override
			public void onSuccess(UserProfileResponseVo response) {
				editUserProfileView.populatePersonalDetails(response);
				((PopupPanel)editUserProfileView).setPixelSize(800, 400);
				((PopupPanel)editUserProfileView).show();
				((PopupPanel)editUserProfileView).center();
			}
			
			@Override
			public void onFailure(Throwable error) {
				//FATAL ERROR
			}
		});

	}

	@Override
	public void onEditProfileSave(SaveUserProfileRequestVo request) {
		secureServices.saveUserProfile(request, new AsyncCallback<SaveUserProfileResponseVo>() {
			
			@Override
			public void onSuccess(SaveUserProfileResponseVo response) {
				refreshUserDetails(response.getSavedUserKey());
				((PopupPanel)editUserProfileView).hide();
				MessageUtil.showMessage(MessageUtil.SUCCESS, "You profile has been updated");
			}
			
			@Override
			public void onFailure(Throwable error) {
				goTo(new LoginPlace("Cannot get User Details"));
			}
		});
	}
}
