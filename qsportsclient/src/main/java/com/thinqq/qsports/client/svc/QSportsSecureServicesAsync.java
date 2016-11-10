package com.thinqq.qsports.client.svc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

public interface QSportsSecureServicesAsync {
	void getUserProfile(UserProfileRequestVo request,
			AsyncCallback<UserProfileResponseVo> callback);

	void saveUserProfile(SaveUserProfileRequestVo request,
			AsyncCallback<SaveUserProfileResponseVo> callback);

	void getTeamProfile(TeamProfileRequestVo request,
			AsyncCallback<TeamProfileResponseVo> callback);

	void saveTeamProfile(SaveTeamProfileRequestVo request,
			AsyncCallback<SaveTeamProfileResponseVo> callback);

	void getSignedInUserProfile(AsyncCallback<UserProfileResponseVo> callback);

	void getCricketStatistics(CricketStatisticsRequestVo request,
			AsyncCallback<CricketStatisticsResponseVo> callback);

	void getTeamStatistics(GetTeamStatisticsRequestVo request,
			AsyncCallback<GetTeamStatisticsResponseVo> callback);

	void getAllTeamPlayers(GetTeamPlayerProfileRequestVo request,
			AsyncCallback<GetTeamPlayerProfileResponseVo> callback);

	void addTeamPlayer(AddTeamPlayerRequestVo request,
			AsyncCallback<AddTeamPlayerResponseVo> callback);

	void getAllTeamsForPlayer(GetTeamsForPlayerRequestVo request,
			AsyncCallback<GetTeamsForPlayerResponseVo> callback);

	void removeTeamPlayer(RemoveTeamPlayerRequestVo request,
			AsyncCallback<RemoveTeamPlayerResponseVo> callback);

	void searchTeamNameStartingWith(SearchResultsRequestVo request,
			AsyncCallback<SearchResultsResponseVo> callback);

	void saveMatch(SaveMatchRequestVo request,
			AsyncCallback<SaveMatchResponseVo> callback);

	void getMatchDetails(GetMatchDetailsRequestVo request,
			AsyncCallback<GetMatchDetailsResponseVo> callback);

	void searchPlayerNameStartingWith(SearchResultsRequestVo request,
			AsyncCallback<SearchResultsResponseVo> callback);

	void saveMatchTeam(SaveMatchTeamRequestVo request,
			AsyncCallback<SaveMatchTeamResponseVo> callback);

	void changeMatchState(MatchStateChangeRequestVo request,
			AsyncCallback<MatchStateChangeResponseVo> callback);
	
	 void findAllMatches(String userId, AsyncCallback<List<GetMatchDetailsResponseVo>> callback);
}
