package com.thinqq.qsports.shared.registration;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SigninRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -2510253836488130872L;
	
	private String userEmail;
	private String password;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
