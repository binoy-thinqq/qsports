package com.thinqq.qsports.persistence.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;


public class CricketTeamResponseVo  extends BaseResponseVo {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1779619761152015039L;


	/**
	 * 
	 */

	private List<CricketTeamPlayersVo> teamPlayersVo;
	
	
	Set<CricketTeamStatsVo> teamStatsVo;

	private Integer teamId;

	private String teamName;

	private String city;

	private String state;

	private String country;

	private String description;

	
	private Date createdTime;

	private Integer createdId;

	private Integer updatedId;

	private Date updatedTime;
	
	private boolean isEditable;
	
	private String teamAssociation;

	private int teamEntryState;
	
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
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
	
	public List<CricketTeamPlayersVo> getTeamPlayersVo() {
		return teamPlayersVo;
	}

	
	public void setTeamPlayersVo(List<CricketTeamPlayersVo> teamPlayers) {
		this.teamPlayersVo = teamPlayers;
	}

	public Set<CricketTeamStatsVo> getTeamStatsVo() {
		return teamStatsVo;
	}

	
	public void setTeamStatsVo(Set<CricketTeamStatsVo> teamStats) {
		this.teamStatsVo = teamStats;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}


	public String getTeamAssociation() {
		return teamAssociation;
	}

	public void setTeamAssociation(String teamAssociation) {
		this.teamAssociation = teamAssociation;
	}
	
}

