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
@Table(name = "cricket.bowling_stats")
public class BowlingStatistics implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3948853867988568430L;

	@Id
	@Column(name = "bowling_stats_id")
	@SequenceGenerator(name = "bowling_stats_seq", sequenceName = "cricket.bowling_stats_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bowling_stats_seq")
	Integer bowlingStatsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cricket_profile_id", nullable = false)
	CricketProfile cricketProfile;

	@Column(name = "matches")
	Integer matches = 0;

	@Column(name = "innings")
	Integer innings = 0;

	@Column(name = "balls")
	Integer balls = 0;

	@Column(name = "wickets")
	Integer wickets = 0;

	@Column(name = "average")
	Float average = 0F;

	@Column(name = "economy")
	Float economy = 0F;

	@Column(name = "runs")
	Integer runs = 0;

	@Column(name = "three_wickets")
	Integer threeWickets = 0;

	@Column(name = "five_wickets")
	Integer fiveWickets = 0;

	@Column(name = "catches")
	Integer catches = 0;

	@Column(name = "stumps")
	Integer stumps = 0;

	@Column(name = "best_bowling")
	String bestBowling;

	@Column(name = "cricket_format")
	Integer cricketFormat;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_time")
	Date updateTime;

	public Integer getBowlingStatsId() {
		return bowlingStatsId;
	}

	public void setBowlingStatsId(Integer bowlingStatsId) {
		this.bowlingStatsId = bowlingStatsId;
	}

	public CricketProfile getCricketProfile() {
		return cricketProfile;
	}

	public void setCricketProfile(CricketProfile cricketProfile) {
		this.cricketProfile = cricketProfile;
	}

	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

	public Integer getInnings() {
		return innings;
	}

	public void setInnings(Integer innings) {
		this.innings = innings;
	}

	public Integer getBalls() {
		return balls;
	}

	public void setBalls(Integer balls) {
		this.balls = balls;
	}

	public Integer getWickets() {
		return wickets;
	}

	public void setWickets(Integer wickets) {
		this.wickets = wickets;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Float getEconomy() {
		return economy;
	}

	public void setEconomy(Float economy) {
		this.economy = economy;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Integer getThreeWickets() {
		return threeWickets;
	}

	public void setThreeWickets(Integer threeWickets) {
		this.threeWickets = threeWickets;
	}

	public Integer getFiveWickets() {
		return fiveWickets;
	}

	public void setFiveWickets(Integer fiveWickets) {
		this.fiveWickets = fiveWickets;
	}

	public Integer getCatches() {
		return catches;
	}

	public void setCatches(Integer catches) {
		this.catches = catches;
	}

	public Integer getStumps() {
		return stumps;
	}

	public void setStumps(Integer stumps) {
		this.stumps = stumps;
	}

	public String getBestBowling() {
		return bestBowling;
	}

	public void setBestBowling(String bestBowling) {
		this.bestBowling = bestBowling;
	}

	public Integer getCricketFormat() {
		return cricketFormat;
	}

	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
