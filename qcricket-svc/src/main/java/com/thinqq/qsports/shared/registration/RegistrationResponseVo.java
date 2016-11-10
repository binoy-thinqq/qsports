package com.thinqq.qsports.shared.registration;

import com.thinqq.qsports.shared.QSportsResponseVo;


public class RegistrationResponseVo extends QSportsResponseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5699466083219251557L;
	private boolean registeredSuccessfully;
	private String confirmationEmailId;
	private boolean emailAlreadyRegistered;
	public boolean isRegisteredSuccessfully() {
		return registeredSuccessfully;
	}
	public void setRegisteredSuccessfully(boolean registeredSuccessfully) {
		this.registeredSuccessfully = registeredSuccessfully;
	}
	public String getConfirmationEmailId() {
		return confirmationEmailId;
	}
	public void setConfirmationEmailId(String confirmationEmailId) {
		this.confirmationEmailId = confirmationEmailId;
	}
	public boolean isEmailAlreadyRegistered() {
		return emailAlreadyRegistered;
	}
	public void setEmailAlreadyRegistered(boolean emailAlreadyRegistered) {
		this.emailAlreadyRegistered = emailAlreadyRegistered;
	}
	
}
