package com.thinqq.qsports.shared.userprofile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BowlingStatisticsResponseVo {
	
	Integer cricketFormat;
	
	Integer innings;
	Integer matches;
	Float avg;
	Integer balls;
	String bestBowling;
	Float economy;
	Integer runs;
	
	Integer catches;
	Integer stumps;
	
	Integer noOfFiveWickets;
	Integer noOfWickets;
	Integer noOfThreeWickets;
	
	String updatedUserKey;
	Date updatedTime;
	Date createdTime;
	String createdUserKey;
	
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public Integer getInnings() {
		return innings;
	}
	public void setInnings(Integer innings) {
		this.innings = innings;
	}
	public Integer getMatches() {
		return matches;
	}
	public void setMatches(Integer matches) {
		this.matches = matches;
	}
	public Float getAvg() {
		return avg;
	}
	public void setAvg(Float avg) {
		this.avg = avg;
	}
	public Integer getBalls() {
		return balls;
	}
	public void setBalls(Integer balls) {
		this.balls = balls;
	}
	public String getBestBowling() {
		return bestBowling;
	}
	public void setBestBowling(String bestBowling) {
		this.bestBowling = bestBowling;
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
	public Integer getNoOfFiveWickets() {
		return noOfFiveWickets;
	}
	public void setNoOfFiveWickets(Integer noOfFiveWickets) {
		this.noOfFiveWickets = noOfFiveWickets;
	}
	public Integer getNoOfWickets() {
		return noOfWickets;
	}
	public void setNoOfWickets(Integer noOfWickets) {
		this.noOfWickets = noOfWickets;
	}
	public Integer getNoOfThreeWickets() {
		return noOfThreeWickets;
	}
	public void setNoOfThreeWickets(Integer noOfThreeWickets) {
		this.noOfThreeWickets = noOfThreeWickets;
	}
	public String getUpdatedUserKey() {
		return updatedUserKey;
	}
	public void setUpdatedUserKey(String updatedUserKey) {
		this.updatedUserKey = updatedUserKey;
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
	public String getCreatedUserKey() {
		return createdUserKey;
	}
	public void setCreatedUserKey(String createdUserKey) {
		this.createdUserKey = createdUserKey;
	}

}
