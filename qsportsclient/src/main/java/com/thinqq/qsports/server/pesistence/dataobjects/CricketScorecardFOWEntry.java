package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketScorecardFOWEntry {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private CricketProfile cricketProfile;
	@Persistent
	private CricketScorecard scorecard;
	@Persistent 
	private Integer wicketNumber;
	@Persistent 
	private Integer runs;
	@Persistent
	private Key createdUserKey;
	@Persistent
	private Date createdTime;
	@Persistent
	private Key updatedUserKey;
	@Persistent
	private Date updatedTime;
	
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public CricketProfile getCricketProfile() {
		return cricketProfile;
	}
	public void setCricketProfile(CricketProfile cricketProfile) {
		this.cricketProfile = cricketProfile;
	}
	public CricketScorecard getScorecard() {
		return scorecard;
	}
	public void setScorecard(CricketScorecard scorecard) {
		this.scorecard = scorecard;
	}
	public Integer getWicketNumber() {
		return wicketNumber;
	}
	public void setWicketNumber(Integer wicketNumber) {
		this.wicketNumber = wicketNumber;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
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
