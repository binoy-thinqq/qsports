package com.thinqq.qsports.client.userprofile;

import com.google.gwt.user.client.ui.IsWidget;
import com.thinqq.qsports.client.core.ICleanable;
import com.thinqq.qsports.client.userprofile.UserProfileView.Presenter;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public interface EditUserProfileView extends IsWidget, ICleanable  {
  
	void setName(String helloName);

	void populatePersonalDetails(UserProfileResponseVo userPersonalDetails);

	void setPresenter(Presenter listener);
	

}