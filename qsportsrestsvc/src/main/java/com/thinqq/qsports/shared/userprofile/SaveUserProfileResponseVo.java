package com.thinqq.qsports.shared.userprofile;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class SaveUserProfileResponseVo extends QSportsResponseVo{
	
	private static final long serialVersionUID = 3105404831172217091L;
	private Boolean savedSuccessfully;

	private String savedUsername;
	private String savedUserTimeZoneId;
	
	private String savedUserKey;
	private String savedCricketProfileKey;

	public String getSavedCricketProfileKey() {
		return savedCricketProfileKey;
	}

	public String getSavedUserKey() {
		return savedUserKey;
	}

	public void setSavedUserKey(String savedUserKey) {
		this.savedUserKey = savedUserKey;
	}

	public void setSavedCricketProfileKey(String savedCricketProfileKey) {
		this.savedCricketProfileKey = savedCricketProfileKey;
	}

	public Boolean isSavedSuccessfully() {
		return savedSuccessfully;
	}

	public void setSavedSuccessfully(Boolean savedSuccessfully) {
		this.savedSuccessfully = savedSuccessfully;
	}

	public String getSavedUsername() {
		return savedUsername;
	}

	public void setSavedUsername(String savedUsername) {
		this.savedUsername = savedUsername;
	}

	public String getSavedUserTimeZoneId() {
		return savedUserTimeZoneId;
	}

	public void setSavedUserTimeZoneId(String savedUserTimeZoneId) {
		this.savedUserTimeZoneId = savedUserTimeZoneId;
	}

}
