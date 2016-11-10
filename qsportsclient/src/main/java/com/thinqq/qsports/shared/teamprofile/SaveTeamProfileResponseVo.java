package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class SaveTeamProfileResponseVo extends QSportsResponseVo {
	private static final long serialVersionUID = -4417265667342310995L;
	private Boolean savedSuccessfully;
	private String teamProfileKey;
	
	public Boolean isSavedSuccessfully() {
		return savedSuccessfully;
	}

	public void setSavedSuccessfully(Boolean savedSuccessfully) {
		this.savedSuccessfully = savedSuccessfully;
	}

	public String getTeamProfileKey() {
		return teamProfileKey;
	}

	public void setTeamProfileKey(String teamProfileKey) {
		this.teamProfileKey = teamProfileKey;
	}
}
