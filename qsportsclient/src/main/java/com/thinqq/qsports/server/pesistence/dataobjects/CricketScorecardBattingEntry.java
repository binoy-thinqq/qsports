package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketScorecardBattingEntry {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private Key cricketProfileKey;
	@Persistent
	private String noProfileString;
	@Persistent 
	private Key bowlerCricketProfileKey;
	@Persistent
	private String bowlerNoProfileString;
	@Persistent
	private Key fielder1CricketProfileKey;
	@Persistent
	private String fielder1NoProfileString;
	@Persistent
	private Key fielder2CricketProfileKey;
	@Persistent
	private String fielder2NoProfileString;
	@Persistent
	private Integer runs;
	@Persistent 
	private Integer balls;
	@Persistent 
	private Integer outType;
	@Persistent 
	private Integer sixes;
	@Persistent 
	private Integer fours;
	@Persistent(mappedBy="battingEntires")
	private CricketScorecard scorecard;
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
	public String getNoProfileString() {
		return noProfileString;
	}

	public String getBowlerNoProfileString() {
		return bowlerNoProfileString;
	}
	public void setBowlerNoProfileString(String bowlerNoProfileString) {
		this.bowlerNoProfileString = bowlerNoProfileString;
	}
	public String getFielder1NoProfileString() {
		return fielder1NoProfileString;
	}
	public void setFielder1NoProfileString(String fielder1NoProfileString) {
		this.fielder1NoProfileString = fielder1NoProfileString;
	}
	public String getFielder2NoProfileString() {
		return fielder2NoProfileString;
	}
	public void setFielder2NoProfileString(String fielder2NoProfileString) {
		this.fielder2NoProfileString = fielder2NoProfileString;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
	}
	public Integer getBalls() {
		return balls;
	}
	public void setBalls(Integer balls) {
		this.balls = balls;
	}
	public Integer getOutType() {
		return outType;
	}
	public void setOutType(Integer outType) {
		this.outType = outType;
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
	public Key getCricketProfileKey() {
		return cricketProfileKey;
	}
	public void setCricketProfileKey(Key cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}
	public Key getBowlerCricketProfileKey() {
		return bowlerCricketProfileKey;
	}
	public void setBowlerCricketProfileKey(Key bowlerCricketProfileKey) {
		this.bowlerCricketProfileKey = bowlerCricketProfileKey;
	}
	public Key getFielder1CricketProfileKey() {
		return fielder1CricketProfileKey;
	}
	public void setFielder1CricketProfileKey(Key fielder1CricketProfileKey) {
		this.fielder1CricketProfileKey = fielder1CricketProfileKey;
	}
	public Key getFielder2CricketProfileKey() {
		return fielder2CricketProfileKey;
	}
	public void setFielder2CricketProfileKey(Key fielder2CricketProfileKey) {
		this.fielder2CricketProfileKey = fielder2CricketProfileKey;
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
	public void setNoProfileString(String noProfileString) {
		this.noProfileString = noProfileString;
	}
	
}
