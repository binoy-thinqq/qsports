package com.thinqq.qsports.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.thinqq.qsports.client.confirmation.ConfirmationActivity;
import com.thinqq.qsports.client.confirmation.ConfirmationPlace;
import com.thinqq.qsports.client.cricket.CricketActivity;
import com.thinqq.qsports.client.cricket.CricketPlace;
import com.thinqq.qsports.client.cricket.matchview.CricketMatchActivity;
import com.thinqq.qsports.client.cricket.matchview.CricketMatchPlace;
import com.thinqq.qsports.client.home.QSportsHomeActivity;
import com.thinqq.qsports.client.home.QSportsHomePlace;
import com.thinqq.qsports.client.initalise.InitialiseActivity;
import com.thinqq.qsports.client.initalise.InitialisePlace;
import com.thinqq.qsports.client.login.LoginActivity;
import com.thinqq.qsports.client.login.LoginPlace;
import com.thinqq.qsports.client.registration.RegistrationActivity;
import com.thinqq.qsports.client.registration.RegistrationPlace;
import com.thinqq.qsports.client.teamprofile.TeamProfileActivity;
import com.thinqq.qsports.client.teamprofile.TeamProfilePlace;
import com.thinqq.qsports.client.userprofile.EditUserProfileActivity;
import com.thinqq.qsports.client.userprofile.EditUserProfilePlace;
import com.thinqq.qsports.client.userprofile.UserProfileActivity;
import com.thinqq.qsports.client.userprofile.UserProfilePlace;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditActivity;
import com.thinqq.qsports.client.userprofile.match.edit.CricketMatchEditPlace;

public class QSportsActivityMapper implements
		com.google.gwt.activity.shared.ActivityMapper {
	private ClientFactory clientFactory;

	public QSportsActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof QSportsHomePlace){
			return new QSportsHomeActivity((QSportsHomePlace) place, clientFactory);
		}else if (place instanceof LoginPlace){
			return new LoginActivity((LoginPlace) place, clientFactory);
		}else if (place instanceof RegistrationPlace){
			return new RegistrationActivity((RegistrationPlace) place, clientFactory);
		}else if (place instanceof UserProfilePlace){
			return new UserProfileActivity((UserProfilePlace) place, clientFactory);
		}else if (place instanceof CricketPlace){
			return new CricketActivity((CricketPlace) place, clientFactory);
		}else if (place instanceof CricketMatchPlace){
			return new CricketMatchActivity((CricketMatchPlace) place, clientFactory);
		}else if (place instanceof EditUserProfilePlace){
			return new EditUserProfileActivity((EditUserProfilePlace) place, clientFactory);
		}else if (place instanceof InitialisePlace){
			return new InitialiseActivity((InitialisePlace) place, clientFactory);
		}else if (place instanceof TeamProfilePlace){
			return new TeamProfileActivity((TeamProfilePlace) place, clientFactory);
		}else if (place instanceof ConfirmationPlace){
			return new ConfirmationActivity((ConfirmationPlace) place, clientFactory);
		}else if (place instanceof CricketMatchEditPlace){
			return new CricketMatchEditActivity((CricketMatchEditPlace) place, clientFactory);
		}
		
		return null;
	}

}
