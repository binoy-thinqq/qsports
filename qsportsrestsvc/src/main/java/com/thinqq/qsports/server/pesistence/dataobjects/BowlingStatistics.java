package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class BowlingStatistics {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private Integer mat;   
	@Persistent
	private Integer inn;   
	@Persistent 
	private Integer balls;
	@Persistent
	private Integer wkts;
	@Persistent
	private Float avg;
	@Persistent
	private Float econ; 
	@Persistent
	private Integer runs;
	@Persistent
	private Integer threeWkts;
	@Persistent
	private Integer fiveWkts;
	@Persistent
	private Integer catches;
	@Persistent
	private Integer stumps;
	@Persistent
	private String bestBowling;
	@Persistent
	private Key cricketProfile;
	@Persistent
	private Integer cricketFormat;
	@Persistent
	private Key createdUserKey;
	@Persistent
	private Date createdTime;
	@Persistent
	private Key updatedUserKey;
	@Persistent
	private Date updatedTime;
	
	
	public BowlingStatistics(Integer mat, Integer inn, Integer balls,
			Integer wkts, Float avg, Float econ, Integer runs,
			Integer threeWkts, Integer fiveWkts, Integer catches,
			Integer stumps, String bestBowling, Key cricketProfile,
			Integer cricketFormat, Key createdUserKey, Date createdTime,
			Key updatedUserKey, Date updatedTime) {
		super();
		this.mat = mat;
		this.inn = inn;
		this.balls = balls;
		this.wkts = wkts;
		this.avg = avg;
		this.econ = econ;
		this.runs = runs;
		this.threeWkts = threeWkts;
		this.fiveWkts = fiveWkts;
		this.catches = catches;
		this.stumps = stumps;
		this.bestBowling = bestBowling;
		this.cricketProfile = cricketProfile;
		this.cricketFormat = cricketFormat;
		this.createdUserKey = createdUserKey;
		this.createdTime = createdTime;
		this.updatedUserKey = updatedUserKey;
		this.updatedTime = updatedTime;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Integer getMat() {
		return mat;
	}
	public void setMat(Integer mat) {
		this.mat = mat;
	}
	public Integer getInn() {
		return inn;
	}
	public void setInn(Integer inn) {
		this.inn = inn;
	}
	public Integer getBalls() {
		return balls;
	}
	public void setBalls(Integer balls) {
		this.balls = balls;
	}
	public Integer getWkts() {
		return wkts;
	}
	public void setWkts(Integer wkts) {
		this.wkts = wkts;
	}
	public  Float getAvg() {
		return avg;
	}
	public void setAvg(Float avg) {
		this.avg = avg;
	}
	public Float getEcon() {
		return econ;
	}
	public void setEcon(Float econ) {
		this.econ = econ;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
	}
	public Integer getThreeWkts() {
		return threeWkts;
	}
	public void setThreeWkts(Integer threeWkts) {
		this.threeWkts = threeWkts;
	}
	public Integer getFiveWkts() {
		return fiveWkts;
	}
	public void setFiveWkts(Integer fiveWkts) {
		this.fiveWkts = fiveWkts;
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
	public Key getCricketProfile() {
		return cricketProfile;
	}
	public void setCricketProfile(Key cricketProfile) {
		this.cricketProfile = cricketProfile;
	}
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public Key getCreatedUserKey() {
		return createdUserKey;
	}
	public void setCreatedUserKey(Key createdUserKey) {
		this.createdUserKey = createdUserKey;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Key getUpdatedUserKey() {
		return updatedUserKey;
	}
	public void setUpdatedUserKey(Key updatedUserKey) {
		this.updatedUserKey = updatedUserKey;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
}
