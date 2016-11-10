package com.thinqq.qsports.persistence.dto;

public class AddPlayerToMatchResponse extends BaseResponseVo {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1450396077796873859L;

	private Integer matchTeamPlayerId;
	
	private Integer teamId;
	
	private Integer profileId;
	
	private Integer matchId;
	
	private String profileName;
	
	
	private String teamName;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getMatchTeamPlayerId() {
		return matchTeamPlayerId;
	}

	public void setMatchTeamPlayerId(Integer matchTeamPlayerId) {
		this.matchTeamPlayerId = matchTeamPlayerId;
	}

		
}

