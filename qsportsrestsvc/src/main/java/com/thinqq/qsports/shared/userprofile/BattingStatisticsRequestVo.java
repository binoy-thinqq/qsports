package com.thinqq.qsports.shared.userprofile;

import java.util.Date;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class BattingStatisticsRequestVo extends QSportsRequestVo{
	

	
	Float avg;
	Date createdTime;
	String createdUserKey;
	Integer cricketFormat;
	Integer fifties;
	Integer hundreds;
	Integer highestScore;
	Integer inningsPlayed;
	Integer matchesPlayed;
	Integer fours;
	Integer notOuts;
	Integer runs;
	Integer sixes;
	Float strikeRate;
	Integer thirties;
	Date updateTime;
	String updatedUserKey;
	public Float getAvg() {
		return avg;
	}
	public void setAvg(Float avg) {
		this.avg = avg;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedUserKey() {
		return createdUserKey;
	}
	public void setCreatedUserKey(String createdUserKey) {
		this.createdUserKey = createdUserKey;
	}
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public Integer getFifties() {
		return fifties;
	}
	public void setFifties(Integer fifties) {
		this.fifties = fifties;
	}
	public Integer getHundreds() {
		return hundreds;
	}
	public void setHundreds(Integer hundreds) {
		this.hundreds = hundreds;
	}
	public Integer getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(Integer highestScore) {
		this.highestScore = highestScore;
	}
	public Integer getInningsPlayed() {
		return inningsPlayed;
	}
	public void setInningsPlayed(Integer inningsPlayed) {
		this.inningsPlayed = inningsPlayed;
	}
	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}
	public void setMatchesPlayed(Integer matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	public Integer getFours() {
		return fours;
	}
	public void setFours(Integer fours) {
		this.fours = fours;
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
	public Integer getSixes() {
		return sixes;
	}
	public void setSixes(Integer sixes) {
		this.sixes = sixes;
	}
	public Float getStrikeRate() {
		return strikeRate;
	}
	public void setStrikeRate(Float strikeRate) {
		this.strikeRate = strikeRate;
	}
	public Integer getThirties() {
		return thirties;
	}
	public void setThirties(Integer thirties) {
		this.thirties = thirties;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdatedUserKey() {
		return updatedUserKey;
	}
	public void setUpdatedUserKey(String updatedUserKey) {
		this.updatedUserKey = updatedUserKey;
	}
	
}
