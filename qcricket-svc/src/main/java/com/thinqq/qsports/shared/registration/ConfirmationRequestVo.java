package com.thinqq.qsports.shared.registration;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class ConfirmationRequestVo extends QSportsRequestVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4101772875749850270L;
	private String email;
	private String confirmationKey;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirmationKey() {
		return confirmationKey;
	}
	public void setConfirmationKey(String confirmationKey) {
		this.confirmationKey = confirmationKey;
	}
}
