package com.thinqq.qsports.shared.teamprofile;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class GetTeamPlayerProfileRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -3799423522514815308L;

	private String teamKey;

	public String getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
}
