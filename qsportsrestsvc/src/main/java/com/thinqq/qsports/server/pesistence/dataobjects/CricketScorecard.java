package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketScorecard {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;	
	@Persistent
	private String name; 
	
	@Persistent
	private CricketInnings innings;
	@Persistent
	private List<CricketScorecardBowlingEntry> bowlingEntires;
	@Persistent
	private List<CricketScorecardBattingEntry> battingEntires;
	/*@Persistent(mappedBy="scorecard")
	private List<CricketScorecardFOWEntry> fowEntires;*/
	@Persistent(defaultFetchGroup = "true")
	private Extras extras;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CricketInnings getInnings() {
		return innings;
	}
	public void setInnings(CricketInnings innings) {
		this.innings = innings;
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
	public List<CricketScorecardBowlingEntry> getBowlingEntires() {
		return bowlingEntires;
	}
	public void setBowlingEntires(List<CricketScorecardBowlingEntry> bowlingEntires) {
		this.bowlingEntires = bowlingEntires;
	}
	public List<CricketScorecardBattingEntry> getBattingEntires() {
		return battingEntires;
	}
	public void setBattingEntires(List<CricketScorecardBattingEntry> battingEntires) {
		this.battingEntires = battingEntires;
	}
	public Extras getExtras() {
		return extras;
	}
	public void setExtras(Extras extras) {
		this.extras = extras;
	}
	/*public List<CricketScorecardFOWEntry> getFowEntires() {
		return fowEntires;
	}
	public void setFowEntires(List<CricketScorecardFOWEntry> fowEntires) {
		this.fowEntires = fowEntires;
	}*/
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
