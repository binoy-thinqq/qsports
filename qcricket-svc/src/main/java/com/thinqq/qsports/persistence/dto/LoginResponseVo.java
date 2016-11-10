package com.thinqq.qsports.persistence.dto;

public class LoginResponseVo extends BaseResponseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7241838245380084946L;
	String authKey;
	Integer userId;

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
