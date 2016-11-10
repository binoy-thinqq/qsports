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
package com.thinqq.qsports.client.confirmation;

import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.confirmation.ConfirmationPlace;
import com.thinqq.qsports.client.confirmation.Confirmation;
import com.thinqq.qsports.client.registration.RegistrationPlace;
import com.thinqq.qsports.client.svc.QSportsCricketSVC;
import com.thinqq.qsports.client.svc.QSportsCricketSVCAsync;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.shared.registration.ConfirmationRequestVo;
import com.thinqq.qsports.shared.registration.ConfirmationResponseVo;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Activities are started and stopped by an ActivityManager associated with a container Widget.
 */
public class ConfirmationActivity extends AbstractActivity implements Confirmation.Presenter {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;
	QSportsCricketSVCAsync cricketSVC = GWT.create(QSportsCricketSVC.class);
	/**
	 * Sample property.
	 */
	private String name;

	public ConfirmationActivity(ConfirmationPlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		Confirmation view = clientFactory.getConfirmation();
		view.setName(name);
		view.setPresenter(this);
		containerWidget.setWidget(view.asWidget());
	    view.showConfirmingMessage();
	    ConfirmationRequestVo request = new ConfirmationRequestVo();
	    request.setConfirmationKey(view.getConfirmationKey());
	    request.setEmail(view.getEmail());

		 cricketSVC.confirmUser(request, new ConfirmationCallBack(view) );
	}
	class ConfirmationCallBack implements AsyncCallback<ConfirmationResponseVo> {
		public ConfirmationCallBack(Confirmation view){
			this.confirmationView=view;
		}
		private Confirmation confirmationView;
		@Override
		public void onSuccess(ConfirmationResponseVo response) {
			goTo(new UserProfilePlace(response.getSignedInUserId()));
			confirmationView.hideConfirmingMessage();
		}
		
		@Override
		public void onFailure(Throwable arg0) {
			goTo(new RegistrationPlace("Confirmation UnSucessful"));
			confirmationView.hideConfirmingMessage();
			
		}
	}
	@Override
	public String mayStop() {
		return null;
	}

	/**
	 * @see Confirmation.Presenter#goTo(Place)
	 */
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
