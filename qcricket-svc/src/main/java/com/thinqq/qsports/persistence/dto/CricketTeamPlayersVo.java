package com.thinqq.qsports.persistence.dto;

import java.util.Date;
import java.util.Set;

public class CricketTeamPlayersVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4783927048968983397L;

	private Integer teamPlayersId;
	
	private Integer teamId;
	
	private Integer cricketProfileId;
	
	private Boolean isModerator;
	
	private Integer  createdId;
	
	private Date  createdTime;
	
	private Integer  updatedId;
	
	private Date updatedTime;
	
	private Boolean  isActive;
	
	private String  isAccepted;
	
	private UserInfoVo userInfo;
	
	Set<BattingStatisticsVo> battingStats;
	
	Set<BowlingStatisticsVo> bowlingStats;

	public Integer getTeamPlayersId() {
		return teamPlayersId;
	}

	public void setTeamPlayersId(Integer teamPlayersId) {
		this.teamPlayersId = teamPlayersId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getCricketProfileId() {
		return cricketProfileId;
	}

	public void setCricketProfileId(Integer cricketProfileId) {
		this.cricketProfileId = cricketProfileId;
	}

	public Boolean getIsModerator() {
		return isModerator;
	}

	public void setIsModerator(Boolean isModerator) {
		this.isModerator = isModerator;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(String isAccepted) {
		this.isAccepted = isAccepted;
	}

	public UserInfoVo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoVo userInfo) {
		this.userInfo = userInfo;
	}

	public Set<BattingStatisticsVo> getBattingStats() {
		return battingStats;
	}

	public void setBattingStats(Set<BattingStatisticsVo> battingStats) {
		this.battingStats = battingStats;
	}

	public Set<BowlingStatisticsVo> getBowlingStats() {
		return bowlingStats;
	}

	public void setBowlingStats(Set<BowlingStatisticsVo> bowlingStats) {
		this.bowlingStats = bowlingStats;
	}
	
	
	
}
