package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketScorecardBowlingEntry {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private CricketProfile cricketProfile;
	@Persistent
	private String noProfileString;
	@Persistent
	private Float overs;
	@Persistent  
	private Integer maidens;
	@Persistent 
	private Integer wickets;
	@Persistent 
	private Integer runs;
	@Persistent 
	private Integer wides;
	@Persistent 
	private Integer noballs;
	@Persistent(mappedBy="bowlingEntires")
	private CricketScorecard scorecard;
	@Persistent
	private Key createdUserKey;
	@Persistent
	private Date createdTime;
	@Persistent
	private Key updatedUserKey;
	@Persistent
	private Date updatedTime;
	
	public CricketProfile getCricketProfile() {
		return cricketProfile;
	}
	public void setCricketProfile(CricketProfile cricketProfile) {
		this.cricketProfile = cricketProfile;
	}
	public String getNoProfileString() {
		return noProfileString;
	}
	public void setNoProfileString(String noProfileString) {
		this.noProfileString = noProfileString;
	}
	public Float getOvers() {
		return overs;
	}
	public void setOvers(Float overs) {
		this.overs = overs;
	}
	public Integer getMaidens() {
		return maidens;
	}
	public void setMaidens(Integer maidens) {
		this.maidens = maidens;
	}
	public Integer getWickets() {
		return wickets;
	}
	public void setWickets(Integer wickets) {
		this.wickets = wickets;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
	}
	public Integer getWides() {
		return wides;
	}
	public void setWides(Integer wides) {
		this.wides = wides;
	}
	public Integer getNoballs() {
		return noballs;
	}
	public void setNoballs(Integer noballs) {
		this.noballs = noballs;
	}
	public CricketScorecard getScorecard() {
		return scorecard;
	}
	public void setScorecard(CricketScorecard scorecard) {
		this.scorecard = scorecard;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getCreatedUserKey() {
		return createdUserKey;
	}
	public void setCreatedUserKey(Key createdUserKey) {
		this.createdUserKey = createdUserKey;
	}
	public Key getUpdatedUserKey() {
		return updatedUserKey;
	}
	public void setUpdatedUserKey(Key updatedUserKey) {
		this.updatedUserKey = updatedUserKey;
	}
}
