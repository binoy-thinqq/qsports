package com.thinqq.qsports.client.svc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.thinqq.qsports.shared.CricketMatchModel;
import com.thinqq.qsports.shared.registration.ConfirmationRequestVo;
import com.thinqq.qsports.shared.registration.ConfirmationResponseVo;
import com.thinqq.qsports.shared.registration.RegistrationRequestVo;
import com.thinqq.qsports.shared.registration.RegistrationResponseVo;

@RemoteServiceRelativePath("svc")
public interface QSportsCricketSVC extends RemoteService{
	
	CricketMatchModel  getCricketMatch(String matchId);
	CricketMatchModel createCricketMatch(CricketMatchModel cricketMatchModel);
	Boolean initialise();
	/** Registration Services*/
	RegistrationResponseVo registerUser(RegistrationRequestVo request);
	ConfirmationResponseVo confirmUser(ConfirmationRequestVo request);
	/**Sign In Service*/
/*	SigninResponseVo signInUser(SigninRequestVo request);*/

	SuggestOracle.Response getSuggestions(SuggestOracle.Request req);
}
