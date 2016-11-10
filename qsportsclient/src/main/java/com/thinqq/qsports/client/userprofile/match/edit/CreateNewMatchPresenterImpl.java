package com.thinqq.qsports.client.userprofile.match.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.thinqq.qsports.client.Genie;
import com.thinqq.qsports.client.common.ClientConstants;
import com.thinqq.qsports.client.common.MessageUtil;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.client.svc.QSportsSecureServicesAsync;
import com.thinqq.qsports.client.userprofile.match.edit.CreateNewMatch.Presenter;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.match.SaveMatchRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo;

public class CreateNewMatchPresenterImpl implements Presenter {
	private static final CreateNewMatchPresenterImpl instance = new CreateNewMatchPresenterImpl();
	private QSportsSecureServicesAsync secureServices = GWT.create(QSportsSecureServices.class);
	private CreateNewMatchPresenterImpl(){}
	private CreateNewMatch view;

	public static CreateNewMatchPresenterImpl getInstance(){
		return instance;
	}

	public CreateNewMatch getView() {
		return view;
	}
	public void setView(CreateNewMatch view) {
		this.view = view;
	}

	public List<NameAndKey> getTeamsOwned( AsyncCallback<GetTeamsForPlayerResponseVo> callBack) {
		GetTeamsForPlayerRequestVo request = new GetTeamsForPlayerRequestVo();
		String cricketProfileKey = Genie.getUser().getCricketProfileKey();
		request.setGetOwnedTeamsOnly(true);
		final List<NameAndKey> ownedTeams = new ArrayList<NameAndKey>();
		if(cricketProfileKey != null){
			request.setCricketProfileKey(cricketProfileKey);
			secureServices.getAllTeamsForPlayer(request, callBack);
		} 
			return ownedTeams;
		
	}

	@Override
	public void onSaveMatch(SaveMatchRequestVo request) {
		secureServices.saveMatch(request, new AsyncCallback<SaveMatchResponseVo>() {

			@Override
			public void onFailure(Throwable error) {
				MessageUtil.showMessage(MessageUtil.FAILURE, ClientConstants.CONNECTION_FAILED_ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(SaveMatchResponseVo response) {
				MessageUtil.showMessage(MessageUtil.SUCCESS, "New Match has been created.");
				Genie.getPlacecontroller().goTo(new CricketMatchEditPlace(response.getMatchKey()));
				getView().hide();
			}
		});
	}
}
