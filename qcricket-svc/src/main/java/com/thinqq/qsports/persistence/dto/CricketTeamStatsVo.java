package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;

public class CricketTeamStatsVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1383875894012688730L;

	@NotNull
	private Integer teamStatsId;
	
	@NotNull
	private Integer  teamId;
	  
	private Integer  matches;
	
	private Integer  won;
	
	private Integer  lost;
	
	private Integer   noResult;
	
	private Integer   draw;
	
	private Integer   runsScored;
	
	private Integer  cricketFormat;
	
	private Date   updatedTime;
	
	private Date  createdTime ;
	
	private Integer  winPercent;

	public Integer getTeamStatsId() {
		return teamStatsId;
	}

	public void setTeamStatsId(Integer teamStatsId) {
		this.teamStatsId = teamStatsId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

	public Integer getWon() {
		return won;
	}

	public void setWon(Integer won) {
		this.won = won;
	}

	public Integer getLost() {
		return lost;
	}

	public void setLost(Integer lost) {
		this.lost = lost;
	}

	public Integer getNoResult() {
		return noResult;
	}

	public void setNoResult(Integer noResult) {
		this.noResult = noResult;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRunsScored() {
		return runsScored;
	}

	public void setRunsScored(Integer runsScored) {
		this.runsScored = runsScored;
	}

	public Integer getCricketFormat() {
		return cricketFormat;
	}

	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getWinPercent() {
		return winPercent;
	}

	public void setWinPercent(Integer winPercent) {
		this.winPercent = winPercent;
	}
	
}
