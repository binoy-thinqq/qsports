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
package com.thinqq.qsports.client.wireframe;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.login.LoginPlace;
import com.thinqq.qsports.client.registration.RegistrationPlace;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.client.userprofile.EditUserProfilePlace;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class QSportsWireActivity extends AbstractActivity implements QSportsWireFrame.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	QSportsSecureServicesAsync secureService = GWT.create(QSportsSecureServices.class);
	public QSportsWireActivity(QSportsWirePlace place, ClientFactory clientFactory) {
		//this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		final QSportsWireFrame view = clientFactory.getThinqQWireFrame();
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see QSportsWireFrame.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onSignInMenuClick() {
		goTo(new LoginPlace(""));
		
	}

	@Override
	public void onNewAccountMenuClick() {
		goTo(new RegistrationPlace(""));
		
	}

	@Override
	public void onSignOutClick() {
		goTo(new LoginPlace(""));
		
	}

	@Override
	public void onUserProfileEditClick() {
		goTo(new EditUserProfilePlace(""));
		
	} 


}
