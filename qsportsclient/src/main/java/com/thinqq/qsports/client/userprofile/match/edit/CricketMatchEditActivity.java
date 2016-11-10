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
package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.match.GetMatchDetailsRequestVo;
import com.thinqq.qsports.shared.match.GetMatchDetailsResponseVo;
import com.thinqq.qsports.shared.match.MatchStateChangeRequestVo;
import com.thinqq.qsports.shared.match.MatchStateChangeResponseVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamResponseVo;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class CricketMatchEditActivity extends AbstractActivity implements CricketMatchEditView.Presenter, 
									SelectTeamPopUp.Presenter, StartMatchPopup.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	private String matchKey;

	private NameAndKey team1;
	
	private NameAndKey team2;
	
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	
	public CricketMatchEditActivity(CricketMatchEditPlace place, ClientFactory clientFactory) {
		this.matchKey = place.getMatchKey();
		this.clientFactory = clientFactory;
	}

	CricketMatchEditView view;
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getCricketMatchEditView();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
		loadMatchDetails(view);
	}

	private void loadMatchDetails(final CricketMatchEditView view) {
		if(matchKey!=null){
			GetMatchDetailsRequestVo request = new GetMatchDetailsRequestVo();
			request.setMatchKey(matchKey);
			secureServices.getMatchDetails(request, new AsyncCallback<GetMatchDetailsResponseVo>() {

				@Override
				public void onFailure(Throwable error) {
					MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
				}

				@Override
				public void onSuccess(GetMatchDetailsResponseVo response) {
					team1 = response.getTeam1();
					team2 = response.getTeam2();
					view.setMatchDetails(response);
				}
			});
		}
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see CricketMatchEditView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void saveTeamList(String matchKey, NameAndKey team, List<String> keys) {
		SaveMatchTeamRequestVo request = new SaveMatchTeamRequestVo();
		request.setMatchKey(matchKey);
		String teamKey = null;
		if(team.getKey() != null){
			teamKey = team.getKey();
		} else {
			teamKey = team.getDisplayName();
		}
		request.setTeamKey(teamKey);
		request.setPlayers(keys);
		secureServices.saveMatchTeam(request, new AsyncCallback<SaveMatchTeamResponseVo>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(SaveMatchTeamResponseVo result) {
				loadMatchDetails(view);
			}
		});
	}

	@Override
	public void startMatch() {
		
		StartMatchPopup startMatchPopUp = Genie.getClientfactory().getStartMatchPopUp();
		startMatchPopUp.setListener(this);
		startMatchPopUp.setMatchKey(matchKey);
		startMatchPopUp.setTeams(team1, team2);
		startMatchPopUp.center();
		startMatchPopUp.show();
		
	}

	@Override
	public void onStartMatch(MatchStateChangeRequestVo request) {
		secureServices.changeMatchState(request, new AsyncCallback<MatchStateChangeResponseVo>() {
			@Override
			public void onFailure(Throwable caught) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);				
			}

			@Override
			public void onSuccess(MatchStateChangeResponseVo result) {
				loadMatchDetails(view);
			}
		});
	}
}
