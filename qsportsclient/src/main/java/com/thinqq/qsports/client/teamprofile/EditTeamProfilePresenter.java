package com.thinqq.qsports.client.teamprofile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.client.teamprofile.EditTeamProfile.Presenter;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileResponseVo;

public class EditTeamProfilePresenter implements Presenter{
	private QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	private static final EditTeamProfilePresenter instance = new EditTeamProfilePresenter();
	private EditTeamProfilePresenter(){}
	public static EditTeamProfilePresenter getInstance(){
		return instance;
	}
	@Override
	public void onTeamSave(SaveTeamProfileRequestVo request) {
		secureServices.saveTeamProfile(request, new AsyncCallback<SaveTeamProfileResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				//Throw FATAL ERROR
			}

			@Override
			public void onSuccess(SaveTeamProfileResponseVo response) {
				Genie.getPlacecontroller().goTo(new TeamProfilePlace(response.getTeamProfileKey()));
			}
		});
	}

}
