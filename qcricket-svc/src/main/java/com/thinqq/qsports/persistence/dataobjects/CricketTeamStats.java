package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cricket.cricket_team_stats")
public class CricketTeamStats implements DataObject {
	
	@Id
	@Column(name = "team_stats_id")
	@SequenceGenerator(name = "cricket_team_stats_seq", sequenceName = "cricket.cricket_team_stats_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_team_stats_seq")
	private Integer teamStatsId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", nullable = false)
	CricketTeam cricketTeam;
	  
	@Column(name = "matches")
	private Integer  matches = 0;
	
	@Column(name = "won")
	private Integer  won = 0;
	
	@Column(name = "lost")
	private Integer  lost = 0;
	
	@Column(name = "no_result")
	private Integer   noResult = 0;
	
	@Column(name = "draw")
	private Integer   draw = 0;
	
	@Column(name = "runs_scored")
	private Integer   runsScored = 0;
	
	@Column(name = "cricket_format")
	private Integer  cricketFormat;
	
	@Column(name = "updated_time")
	private Date   updatedTime;
	
	@Column(name = "created_time")
	private Date  createdTime ;

	public Integer getTeamStatsId() {
		return teamStatsId;
	}

	public void setTeamStatsId(Integer teamStatsId) {
		this.teamStatsId = teamStatsId;
	}

	public CricketTeam getCricketTeam() {
		return cricketTeam;
	}

	public void setCricketTeam(CricketTeam cricketTeam) {
		this.cricketTeam = cricketTeam;
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
	
}
