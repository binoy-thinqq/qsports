package com.thinqq.qsports.persistence.dto;

import java.io.Serializable;
import java.util.Date;

public class BowlingStatisticsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2817757721097318969L;

	Integer bowlingStatsId;

	Integer matches;

	Integer innings;

	Integer balls;

	Integer wickets;

	Float average;

	Float economy;

	Integer runs;

	Integer threeWickets;

	Integer fiveWickets;

	Integer catches;

	Integer stumps;

	String bestBowling;

	Integer cricketFormat;

	Date createdTime;

	Date updateTime;

	public Integer getBowlingStatsId() {
		return bowlingStatsId;
	}

	public void setBowlingStatsId(Integer bowlingStatsId) {
		this.bowlingStatsId = bowlingStatsId;
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
