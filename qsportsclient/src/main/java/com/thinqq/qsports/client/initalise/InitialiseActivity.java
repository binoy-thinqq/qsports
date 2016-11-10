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
package com.thinqq.qsports.client.initalise;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class InitialiseActivity extends AbstractActivity implements InitialiseView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;
	private QSportsCricketSVCAsync cricketService = GWT.create(QSportsCricketSVC.class);
	public InitialiseActivity(InitialisePlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		InitialiseView view = clientFactory.getInitialiseView();
		//cricketService.
		view.setName(name);
		view.setPresenter(this);

		containerWidget.setWidget(view.asWidget());
	}

	@Override
	public String mayStop() {
		return "Please hold on. This activity is stopping.";
	}

	/**
	 * @see InitialiseView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void initialise() {
		cricketService.initialise(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean arg0) {
				Window.alert("Success");
				
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Failure");
				
			}
		});
	}
	
	
}
