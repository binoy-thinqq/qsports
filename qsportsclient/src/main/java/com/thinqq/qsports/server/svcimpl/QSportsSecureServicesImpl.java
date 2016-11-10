package com.thinqq.qsports.server.svcimpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thinqq.qsports.client.svc.QSportsSecureServices;
import com.thinqq.qsports.server.constants.ServerConstants;
import com.thinqq.qsports.server.oauth.UserInfo;
import com.thinqq.qsports.server.process.CricketMatchProcess;
import com.thinqq.qsports.server.process.SearchProcess;
import com.thinqq.qsports.server.process.TeamProfileProcess;
import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.shared.QSportsRequestVo;
import com.thinqq.qsports.shared.QSportsResponseVo;
import com.thinqq.qsports.shared.match.GetMatchDetailsRequestVo;
import com.thinqq.qsports.shared.match.GetMatchDetailsResponseVo;
import com.thinqq.qsports.shared.match.MatchStateChangeRequestVo;
import com.thinqq.qsports.shared.match.MatchStateChangeResponseVo;
import com.thinqq.qsports.shared.match.SaveMatchRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchResponseVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamResponseVo;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;
import com.thinqq.qsports.shared.vo.SearchResultsRequestVo;
import com.thinqq.qsports.shared.vo.SearchResultsResponseVo;

public class QSportsSecureServicesImpl extends RemoteServiceServlet implements QSportsSecureServices{

	private static final long serialVersionUID = 7768438093728227217L;

	@Override
	public UserProfileResponseVo getSignedInUserProfile() {
		UserProfileResponseVo response = new UserProfileResponseVo();
		UserProfileRequestVo request = new UserProfileRequestVo();
		UserInfo user;
		if((user = isUserSignedIn(request,response))!=null){
			request.setUserKey(user.getqSportsUserKey());
			UserProfileProcess.getInstance().getUserProfile(request, response);
		}
		return response;
	}
	
	@Override
	public UserProfileResponseVo getUserProfile(UserProfileRequestVo request) {
		UserProfileResponseVo response = new UserProfileResponseVo();
		UserInfo user;
		if((user = isUserSignedIn(request,response))!=null){
			if(request.getUserKey()==null){
				request.setUserKey(user.getqSportsUserKey());
			}
			UserProfileProcess.getInstance().getUserProfile(request, response);
		}
		return response;
	}
	private UserInfo isUserSignedIn(QSportsRequestVo request, QSportsResponseVo response) {
		UserInfo user = (UserInfo)getThreadLocalRequest().getSession().getAttribute(ServerConstants.SESSION_USER_OBJECT);
		if(user!=null){
			request.setSignedInUserKey(user.getqSportsUserKey());
			request.setTimeZone(user.getTimeZoneId());
			response.setSignedInUserKey(user.getqSportsUserKey());
			response.setNoUserSignedIn(false);
			return user;
		}else{
			response.setNoUserSignedIn(true);
			response.setReturnToken(getThreadLocalRequest().getRequestURI());
			return null;
		}

	}
	@Override
	public SaveUserProfileResponseVo saveUserProfile(
			SaveUserProfileRequestVo request) {
		SaveUserProfileResponseVo response = new SaveUserProfileResponseVo();
		if(isUserSignedIn(request,response)!=null){
			UserProfileProcess.getInstance().saveUserProfile(request, response);
		}
		//Special treatment for Name and time zone setting the change to the session user object
		UserInfo user = (UserInfo)getThreadLocalRequest().getSession().getAttribute(ServerConstants.SESSION_USER_OBJECT);
		user.setName(response.getSavedUsername());
		user.setTimeZoneId(response.getSavedUserTimeZoneId());
		return response;
	}
	@Override
	public TeamProfileResponseVo getTeamProfile(TeamProfileRequestVo request) {
		TeamProfileResponseVo response = new TeamProfileResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().getTeamProfile(request, response);
		}
		return response;
	}
	@Override
	public SaveTeamProfileResponseVo saveTeamProfile(
			SaveTeamProfileRequestVo request) {
		SaveTeamProfileResponseVo response = new SaveTeamProfileResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().saveTeamProfile(request, response);
		}
		return response;
	}

	@Override
	public CricketStatisticsResponseVo getCricketStatistics(
			CricketStatisticsRequestVo request) {
		CricketStatisticsResponseVo response = new CricketStatisticsResponseVo();
		if(isUserSignedIn(request,response)!=null){
			UserProfileProcess.getInstance().getCricketStatistics(request, response);
		}
		return response;
	}

	@Override
	public GetTeamStatisticsResponseVo getTeamStatistics(
			GetTeamStatisticsRequestVo request) {
		GetTeamStatisticsResponseVo response = new GetTeamStatisticsResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().getTeamStatistics(request, response);
		}
		return response;
	}

	@Override
	public GetTeamPlayerProfileResponseVo getAllTeamPlayers(
			GetTeamPlayerProfileRequestVo request) {
		GetTeamPlayerProfileResponseVo response = new GetTeamPlayerProfileResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().getAllTeamPlayers(request, response);
		}
		return response;
	}

	@Override
	public AddTeamPlayerResponseVo addTeamPlayer(AddTeamPlayerRequestVo request) {
		AddTeamPlayerResponseVo response = new AddTeamPlayerResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().addTeamProfile(request, response);
		}
		return response;
	}

	@Override
	public GetTeamsForPlayerResponseVo getAllTeamsForPlayer(
			GetTeamsForPlayerRequestVo request) {
		GetTeamsForPlayerResponseVo response = new GetTeamsForPlayerResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().getAllTeamsForPlayer(request, response);
		}
		return response;
	}

	@Override
	public RemoveTeamPlayerResponseVo removeTeamPlayer(
			RemoveTeamPlayerRequestVo request) {
		RemoveTeamPlayerResponseVo response = new RemoveTeamPlayerResponseVo();
		if(isUserSignedIn(request,response)!=null){
			TeamProfileProcess.getInstance().removeTeamProfile(request, response);
		}
		return response;
	}

	@Override
	public SearchResultsResponseVo searchTeamNameStartingWith(
			SearchResultsRequestVo request) {
		SearchResultsResponseVo response = new SearchResultsResponseVo();
		if(isUserSignedIn(request,response)!=null){
			SearchProcess.getInstance().searchTeam(request, response);
		}
		return response;
	}

	@Override
	public SaveMatchResponseVo saveMatch(SaveMatchRequestVo request) {
		SaveMatchResponseVo response = new SaveMatchResponseVo();
		if(isUserSignedIn(request,response)!=null){
			CricketMatchProcess.getInstance().saveMatch(request, response);
		}
		return response;
	}

	@Override
	public GetMatchDetailsResponseVo getMatchDetails(
			GetMatchDetailsRequestVo request) {
		GetMatchDetailsResponseVo response = new GetMatchDetailsResponseVo();
		if(isUserSignedIn(request,response)!=null){
			CricketMatchProcess.getInstance().getMatchDetails(request, response);
		}
		return response;
	}

	@Override
	public SearchResultsResponseVo searchPlayerNameStartingWith(
			SearchResultsRequestVo request) {
		SearchResultsResponseVo response = new SearchResultsResponseVo();
		if(isUserSignedIn(request,response)!=null){
			SearchProcess.getInstance().searchPlayer(request, response);
		}
		return response;
	}

	@Override
	public SaveMatchTeamResponseVo saveMatchTeam(SaveMatchTeamRequestVo request) {
		SaveMatchTeamResponseVo response = new SaveMatchTeamResponseVo();
		if(isUserSignedIn(request,response)!=null){
			CricketMatchProcess.getInstance().saveMatchTeamDetails(request, response);
		}
		return response;
	}

	@Override
	public MatchStateChangeResponseVo changeMatchState(
			MatchStateChangeRequestVo request) {
		MatchStateChangeResponseVo response = new MatchStateChangeResponseVo();
		if(isUserSignedIn(request,response)!=null){
			CricketMatchProcess.getInstance().changeMatchState(request, response);
		}
		return response;
	}
}
