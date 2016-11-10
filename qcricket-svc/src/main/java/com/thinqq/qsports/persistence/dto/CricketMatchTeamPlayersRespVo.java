package com.thinqq.qsports.persistence.dto;

import java.util.Date;

public class CricketMatchTeamPlayersRespVo {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -8755216244031538272L;

	Integer matchTeamPlayerId;
	
	Integer teamId;
	
	String teamName;
	
	Integer profileId;
	
	String profileName;
	
	Boolean sub;
	
	Integer createdId;

	Date createdTime;

	Integer updatedId;

	Date updatedTime;

	public Integer getMatchTeamPlayerId() {
		return matchTeamPlayerId;
	}

	public void setMatchTeamPlayerId(Integer matchTeamPlayerId) {
		this.matchTeamPlayerId = matchTeamPlayerId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Boolean getSub() {
		return sub;
	}

	public void setSub(Boolean sub) {
		this.sub = sub;
	}

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getUpdatedId() {
		return updatedId;
	}

	public void setUpdatedId(Integer updatedId) {
		this.updatedId = updatedId;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	

}
