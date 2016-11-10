package com.thinqq.qsports.shared.match;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class MatchStateChangeRequestVo extends QSportsRequestVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700299558667954214L;

	private String matchKey;
	private String tossWonTeamKey;
	private int tossChoiceTaken;
	private String pitchCondition;
	private String weatherDetails;
	private String officials;
	private int stateId;
	
	public MatchStateChangeRequestVo(){}
	
	public MatchStateChangeRequestVo(String matchKey, int stateId) {
		super();
		this.matchKey = matchKey;
		this.stateId = stateId;
	}
	
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getTossWonTeamKey() {
		return tossWonTeamKey;
	}

	public void setTossWonTeamKey(String tossWonTeamKey) {
		this.tossWonTeamKey = tossWonTeamKey;
	}

	public int getTossChoiceTaken() {
		return tossChoiceTaken;
	}

	public void setTossChoiceTaken(int tossChoiceTaken) {
		this.tossChoiceTaken = tossChoiceTaken;
	}

	public String getPitchCondition() {
		return pitchCondition;
	}

	public void setPitchCondition(String pitchCondition) {
		this.pitchCondition = pitchCondition;
	}

	public String getWeatherDetails() {
		return weatherDetails;
	}

	public void setWeatherDetails(String weatherDetails) {
		this.weatherDetails = weatherDetails;
	}

	public String getOfficials() {
		return officials;
	}

	public void setOfficials(String officials) {
		this.officials = officials;
	}
	
	
}
