package com.thinqq.qsports.shared.match;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class SaveMatchTeamResponseVo extends QSportsResponseVo {

	private static final long serialVersionUID = 4196793140216737113L;
	
	
	private boolean savedSuccessfully;
	private String matchKey;
	private String teamKey;
	
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public boolean isSavedSuccessfully() {
		return savedSuccessfully;
	}
	public void setSavedSuccessfully(boolean savedSuccessfully) {
		this.savedSuccessfully = savedSuccessfully;
	}
	public String getTeamKey() {
		return teamKey;
	}
	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
	

}
