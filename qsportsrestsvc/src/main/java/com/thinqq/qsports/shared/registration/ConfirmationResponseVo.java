package com.thinqq.qsports.shared.registration;

import com.thinqq.qsports.shared.QSportsResponseVo;


public class ConfirmationResponseVo extends QSportsResponseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4215519923397254287L;
	private Boolean confirmationSuccessful;
	private String userKey;
	public Boolean getConfirmationSuccessful() {
		return confirmationSuccessful;
	}
	public void setConfirmationSuccessful(Boolean confirmationSuccessful) {
		this.confirmationSuccessful = confirmationSuccessful;
	}
	public String getSignedInUserId() {
		return userKey;
	}
	public void setSignedInUserId(String userKey) {
		this.userKey = userKey;
	}

}
