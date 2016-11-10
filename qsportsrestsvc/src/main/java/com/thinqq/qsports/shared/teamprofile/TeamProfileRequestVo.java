package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class TeamProfileRequestVo extends QSportsRequestVo {
	private static final long serialVersionUID = 8209595113429317140L;
	private String teamProfileKey;

	public String getTeamProfileKey() {
		return teamProfileKey;
	}

	public void setTeamProfileKey(String teamProfileKey) {
		this.teamProfileKey = teamProfileKey;
	}

}
