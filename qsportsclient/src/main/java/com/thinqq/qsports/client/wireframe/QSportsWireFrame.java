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
package com.thinqq.qsports.client.wireframe;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

/**
 * View base interface.
 * Extends IsWidget so a view impl can easily provide its container widget.
 */
public interface QSportsWireFrame extends IsWidget,ICleanable {

	void setUser(NameAndKey user);

	void setPresenter(Presenter listener);

	public interface Presenter {
		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
		void onSignInMenuClick();
		void onSignOutClick();
		void onNewAccountMenuClick();
		void onUserProfileEditClick();
	}
	SimplePanel getCentralArea();
	FlowPanel getMessagePanel();
}
