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
package com.thinqq.qsports.client.login;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.registration.RegistrationPlace;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;
import com.thinqq.qsports.shared.registration.SigninRequestVo;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class LoginActivity extends AbstractActivity implements LoginView.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;
	QSportsCricketSVCAsync cricketSVC = GWT.create(QSportsCricketSVC.class);
	LoginView  view = null;
	/**
	 * Sample property.
	 */
	private String token;

	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		this.token = place.getToken();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getLoginView();
		view.setName(token);
		view.setPresenter(this);
		//clientFactory.getThinqQWireFrame().unSetUser();
		containerWidget.setWidget(view.asWidget());
		if(token!=null && token.equals(ClientConstants.REGISTRATION_SUCESS)){
			view.showConfirmationEmailMessage();
		}
	}

	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see LoginView.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onUserNameChange(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPasswordFocus(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubmitButtonClick(String userEmail, String password) {
		SigninRequestVo request = new SigninRequestVo();
		request.setUserEmail(userEmail);
		request.setPassword(password);
		/*cricketSVC.signInUser(request, new AsyncCallback<SigninResponseVo>() {
			
			@Override
			public void onSuccess(SigninResponseVo response) {
				if(response.isInvalidEmailPassword()!=null && response.isInvalidEmailPassword() ){
					view.showEmailPasswordMismatchError();
				}else if(response.isUserDeactivated() !=null && response.isUserDeactivated()){
					view.showUserDeactivatedError();
				}else if(response.getSignedInUserId()!=null){
					goTo(new UserProfilePlace("SignedIn", response.getSignedInUserId()));
					
				}else{
					view.showUnableToProceedError();
				}
				
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				view.showUnableToProceedError();
				
			}
		});*/
	}

	@Override
	public void onSignUpButtonClick() {
		goTo(new RegistrationPlace(""));
		
	}

}
