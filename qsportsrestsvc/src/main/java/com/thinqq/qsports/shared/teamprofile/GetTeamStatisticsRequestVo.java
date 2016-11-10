package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class GetTeamStatisticsRequestVo extends QSportsRequestVo {
	private static final long serialVersionUID = 88087224258647681L;
	private String teamProfileKey;

	public String getTeamProfileKey() {
		return teamProfileKey;
	}

	public void setTeamProfileKey(String teamProfileKey) {
		this.teamProfileKey = teamProfileKey;
	}
}
