package com.thinqq.qsports.client.svc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("ssvc")
public interface QSportsSecureServices extends RemoteService{
	/**User Profile Services*/
	UserProfileResponseVo getSignedInUserProfile();
	UserProfileResponseVo getUserProfile(UserProfileRequestVo request);
	SaveUserProfileResponseVo saveUserProfile(SaveUserProfileRequestVo request);
	CricketStatisticsResponseVo getCricketStatistics(CricketStatisticsRequestVo request);
	
	/**Team Profile Services*/
	TeamProfileResponseVo getTeamProfile(TeamProfileRequestVo request);
	SaveTeamProfileResponseVo saveTeamProfile(SaveTeamProfileRequestVo request);
	GetTeamStatisticsResponseVo getTeamStatistics(GetTeamStatisticsRequestVo request);
	GetTeamPlayerProfileResponseVo getAllTeamPlayers(GetTeamPlayerProfileRequestVo request);
	AddTeamPlayerResponseVo addTeamPlayer(AddTeamPlayerRequestVo request);
	RemoveTeamPlayerResponseVo removeTeamPlayer(RemoveTeamPlayerRequestVo request);
	GetTeamsForPlayerResponseVo getAllTeamsForPlayer(GetTeamsForPlayerRequestVo request);
	
	/** Search Services*/
	SearchResultsResponseVo searchTeamNameStartingWith(SearchResultsRequestVo request);
	SearchResultsResponseVo searchPlayerNameStartingWith(SearchResultsRequestVo request);
	
	/** Match creation Services*/
	SaveMatchResponseVo saveMatch(SaveMatchRequestVo request);
	GetMatchDetailsResponseVo getMatchDetails(GetMatchDetailsRequestVo request);
	SaveMatchTeamResponseVo saveMatchTeam(SaveMatchTeamRequestVo request);
	MatchStateChangeResponseVo changeMatchState(MatchStateChangeRequestVo request);
	List<GetMatchDetailsResponseVo> findAllMatches(String userId);
}
