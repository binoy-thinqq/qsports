package com.thinqq.qsports.client.svc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.registration.ConfirmationRequestVo;
import com.thinqq.qsports.shared.registration.ConfirmationResponseVo;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.registration.RegistrationResponseVo;

public interface QSportsCricketSVCAsync {
	void getCricketMatch(String matchId,AsyncCallback<CricketMatchModel> callBack);
	void createCricketMatch(CricketMatchModel cricketMatchModel,AsyncCallback<CricketMatchModel> callBack);
	void initialise(AsyncCallback<Boolean> initialiseCallback);
	void registerUser(RegistrationRequestVo registrationRequest,AsyncCallback<RegistrationResponseVo> registrationCallBack);
	void confirmUser(ConfirmationRequestVo request,
			AsyncCallback<ConfirmationResponseVo> callback);

/*	void signInUser(SigninRequestVo request,
			AsyncCallback<SigninResponseVo> callback);*/
	void getSuggestions(SuggestOracle.Request req, AsyncCallback callback);
}
