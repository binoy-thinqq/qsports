package com.thinqq.qsports.shared.registration;

import com.thinqq.qsports.shared.QSportsResponseVo;


public class SigninResponseVo extends QSportsResponseVo {
	private static final long serialVersionUID = 2718774858030467399L;

	private Boolean invalidEmailPassword;
	private Boolean userDeactivated;
	
	public Boolean isInvalidEmailPassword() {
		return invalidEmailPassword;
	}
	public void setInvalidEmailPassword(Boolean invalidEmailPassword) {
		this.invalidEmailPassword = invalidEmailPassword;
	}
	public Boolean isUserDeactivated() {
		return userDeactivated;
	}
	public void setUserDeactivated(Boolean userDeactivated) {
		this.userDeactivated = userDeactivated;
	}
}
