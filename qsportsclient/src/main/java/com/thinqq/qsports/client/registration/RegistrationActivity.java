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
package com.thinqq.qsports.client.registration;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.login.LoginPlace;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.registration.RegistrationResponseVo;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class RegistrationActivity extends AbstractActivity implements RegistrationView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;
	QSportsCricketSVCAsync cricketSVC = GWT.create(QSportsCricketSVC.class);
	RegistrationView view = null;
	/**
	 * Sample property.
	 */
	private String name;

	public RegistrationActivity(RegistrationPlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getRegistrationView();
	
		view.setName(name);
		view.setPresenter(this);
		view.initialiseFields();
		containerWidget.setWidget(view.asWidget());
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see RegistrationView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onRegisterSubmit(RegistrationRequestVo registrationRequest) {
		view.showRegisteringMessage();
		cricketSVC.registerUser(registrationRequest, new AsyncCallback<RegistrationResponseVo>() {
		
		@Override
		public void onSuccess(RegistrationResponseVo response) {
			if(response.isRegisteredSuccessfully()){
				goTo(new LoginPlace(ClientConstants.REGISTRATION_SUCESS));
			}else if(response.isEmailAlreadyRegistered()){
				view.showEmailAlreadyExistsError();
			}else{
				view.showUnableToRegisterError();
			}
			view.hideRegisteringMessage();
			
		}
		
		@Override
		public void onFailure(Throwable error) {
			view.showUnableToRegisterError();
			view.hideRegisteringMessage();
		}
	});
}

	@Override
	public void onSignInButtonClick() {
		goTo(new LoginPlace(""));
		
	}
}
