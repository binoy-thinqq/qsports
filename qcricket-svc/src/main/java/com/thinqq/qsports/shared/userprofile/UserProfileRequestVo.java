package com.thinqq.qsports.shared.userprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class UserProfileRequestVo extends QSportsRequestVo{
	private static final long serialVersionUID = 5953491734801775563L;
	private String userKey;
	private String email;
	private boolean isUseOnlyEmail;
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isUseOnlyEmail() {
		return isUseOnlyEmail;
	}

	public void setUseOnlyEmail(boolean isUseOnlyEmail) {
		this.isUseOnlyEmail = isUseOnlyEmail;
	}

}
