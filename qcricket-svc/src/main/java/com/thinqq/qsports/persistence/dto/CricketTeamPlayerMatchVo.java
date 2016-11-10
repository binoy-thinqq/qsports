package com.thinqq.qsports.persistence.dto;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;

public class CricketTeamPlayerMatchVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5070707395519197801L;

	private Integer teamId;
	
	private Integer profileId;
	
	@NotNull
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
}
