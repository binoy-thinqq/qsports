/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinqq.qsports.client.userprofile;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.client.teamprofile.EditTeamProfile;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo.TeamProfileMinDetails;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

/**
 * View base interface.
 * Extends IsWidget so a view impl can easily provide its container widget.
 */
public interface UserProfileView extends IsWidget, ICleanable {
  
	void setName(String helloName);
	void setPresenter(Presenter listener);
	void setUserDetails(UserProfileResponseVo profileResponse);
	void showCreateTeamView(EditTeamProfile newTeamView);
	void showFatalError();
	void showEditPofileButton(boolean show);
	void setBattingStatistics(List<String> t20Batting,
			List<String> oDBatting,
			List<String> testBatting);
	void setBowlingStatistics(List<String> t20Bowling,
			List<String> oDBowling,
			List<String> testBowling);
	void setTeamsList(List<TeamProfileMinDetails> list);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
		void onEditProfileShow();
		void onEditProfileSave(SaveUserProfileRequestVo request);
	}
}
