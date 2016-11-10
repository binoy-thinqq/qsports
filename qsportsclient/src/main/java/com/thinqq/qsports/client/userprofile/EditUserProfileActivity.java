package com.thinqq.qsports.client.userprofile;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.thinqq.qsports.client.ClientFactory;
import com.thinqq.qsports.client.login.LoginPlace;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public class EditUserProfileActivity extends AbstractActivity {
	/**
	 * Used to obtain views, eventBus, placeController.
	 * Alternatively, could be injected via GIN.
	 */
	private ClientFactory clientFactory;

	/**
	 * Sample property.
	 */
	private String name;
	EditUserProfileView view;
	QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	public EditUserProfileActivity(EditUserProfilePlace place, ClientFactory clientFactory) {
		this.name = place.getName();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
		view = clientFactory.getEditUserProfileView();
		
		secureServices.getUserProfile(new UserProfileRequestVo(), new AsyncCallback<UserProfileResponseVo>() {
			
			@Override
			public void onSuccess(UserProfileResponseVo response) {
				containerWidget.setWidget(view.asWidget());
				view.populatePersonalDetails(response);
			}
			
			@Override
			public void onFailure(Throwable error) {
				goTo(new LoginPlace("Cannot get User Details"));
			}
		});
		//Call get user personal details and cricket profile detials asynchronously
		//Populate data
		view.setName(name);
			//containerWidget.setWidget(new Horizonta)
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
	
	public void onSaveProfile(SaveUserProfileRequestVo request){
		secureServices.saveUserProfile(request, new AsyncCallback<SaveUserProfileResponseVo>() {
			
			@Override
			public void onSuccess(SaveUserProfileResponseVo response) {
				if(response.isNoUserSignedIn()==null || response.isNoUserSignedIn()){
					goTo(new LoginPlace(response.getReturnToken()));
					return;
				}
			}
			
			@Override
			public void onFailure(Throwable error) {
				goTo(new LoginPlace("Cannot get User Details"));
			}
		});
	}
}
