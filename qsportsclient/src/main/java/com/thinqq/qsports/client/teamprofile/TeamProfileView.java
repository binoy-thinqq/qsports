package com.thinqq.qsports.client.teamprofile;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo.TeamPlayerMinDetails;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsResponseVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public interface TeamProfileView extends IsWidget, ICleanable {
	void setName(String helloName);

	void setPresenter(Presenter listener);

	void setTeamDetails(TeamProfileResponseVo details);
	
	void setTeamStatistics(GetTeamStatisticsResponseVo statistics);
	
	void setTeamPlayerList(List<TeamPlayerMinDetails> playerList);
	
	void setTeamPlayerStatistics(List<String> t20Batting,
			List<String> oDBatting, List<String> testBatting, List<String> t20Bowling,
			List<String> oDBowling, List<String> testBowling, NameAndKey user, String cricketProfileKey);
	
	void setPlayerDetailsOnAddPlayerPopup(UserProfileResponseVo response);
	
	void showEditTeamProfile(TeamProfileResponseVo details, String teamKey);
	
	
	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
		
		void onPlayerSelection(String cricketProfileKey,NameAndKey user);
		
		void onEditTeamClick();
		
		void onRemoveOwner(RemoveTeamPlayerRequestVo request);

	}
}
