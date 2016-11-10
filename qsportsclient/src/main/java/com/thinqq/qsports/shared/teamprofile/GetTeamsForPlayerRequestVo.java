package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class GetTeamsForPlayerRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -7097385776767097509L;

	private String cricketProfileKey;

	private boolean getOwnedTeamsOnly;
	
	public String getCricketProfileKey() {
		return cricketProfileKey;
	}

	public void setCricketProfileKey(String cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}

	public boolean isGetOwnedTeamsOnly() {
		return getOwnedTeamsOnly;
	}

	public void setGetOwnedTeamsOnly(boolean getOwnedTeamsOnly) {
		this.getOwnedTeamsOnly = getOwnedTeamsOnly;
	}
	
}
