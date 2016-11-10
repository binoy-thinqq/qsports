package com.thinqq.qsports.persistence.dto;

import java.io.Serializable;
import java.util.Date;

public class BattingStatisticsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7412714468656992983L;

	Integer battingStatsId;

	Integer matches;

	Integer innings;

	Integer notOuts;

	Integer runs;

	Float strikeRate;

	Float average;

	Integer hundreds;

	Integer doubleHundreds;

	Integer trippleHundreds;

	Integer fifties;

	Integer thirties;

	Integer sixes;

	Integer fours;
	
	String highestScore;

	Integer cricketFormat;

	Date createdTime;

	Date updateTime;

	public Integer getBattingStatsId() {
		return battingStatsId;
	}

	public void setBattingStatsId(Integer battingStatsId) {
		this.battingStatsId = battingStatsId;
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

	public Integer getNotOuts() {
		return notOuts;
	}

	public void setNotOuts(Integer notOuts) {
		this.notOuts = notOuts;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Float getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Float strikeRate) {
		this.strikeRate = strikeRate;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getHundreds() {
		return hundreds;
	}

	public void setHundreds(Integer hundreds) {
		this.hundreds = hundreds;
	}

	public Integer getDoubleHundreds() {
		return doubleHundreds;
	}

	public void setDoubleHundreds(Integer doubleHundreds) {
		this.doubleHundreds = doubleHundreds;
	}

	public Integer getTrippleHundreds() {
		return trippleHundreds;
	}

	public void setTrippleHundreds(Integer trippleHundreds) {
		this.trippleHundreds = trippleHundreds;
	}

	public Integer getFifties() {
		return fifties;
	}

	public void setFifties(Integer fifties) {
		this.fifties = fifties;
	}

	public Integer getThirties() {
		return thirties;
	}

	public void setThirties(Integer thirties) {
		this.thirties = thirties;
	}

	public Integer getSixes() {
		return sixes;
	}

	public void setSixes(Integer sixes) {
		this.sixes = sixes;
	}

	public Integer getFours() {
		return fours;
	}

	public void setFours(Integer fours) {
		this.fours = fours;
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

	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}

	
}
