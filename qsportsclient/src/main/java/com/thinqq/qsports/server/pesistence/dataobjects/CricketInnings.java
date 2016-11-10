package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class CricketInnings {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;	
	@Persistent
	private String inningsSummary;	
	@Persistent
	private Key match;
	@Persistent
	private List<Key> bowlingEntires;
	@Persistent
	private List<Key> battingEntires;
	@Persistent
	private List<Key> fowEntires;
	@Persistent
	private Extras extras;
	@Persistent
	private List<Integer> scoreByOvers;
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
	public String getInningsSummary() {
		return inningsSummary;
	}
	public void setInningsSummary(String inningsSummary) {
		this.inningsSummary = inningsSummary;
	}
	public Key getMatch() {
		return match;
	}
	public void setMatch(Key match) {
		this.match = match;
	}
	public List<Key> getBowlingEntires() {
		return bowlingEntires;
	}
	public void setBowlingEntires(List<Key> bowlingEntires) {
		this.bowlingEntires = bowlingEntires;
	}
	public List<Key> getBattingEntires() {
		return battingEntires;
	}
	public void setBattingEntires(List<Key> battingEntires) {
		this.battingEntires = battingEntires;
	}
	public List<Key> getFowEntires() {
		return fowEntires;
	}
	public void setFowEntires(List<Key> fowEntires) {
		this.fowEntires = fowEntires;
	}
	public Extras getExtras() {
		return extras;
	}
	public void setExtras(Extras extras) {
		this.extras = extras;
	}
	public List<Integer> getScoreByOvers() {
		return scoreByOvers;
	}
	public void setScoreByOvers(List<Integer> scoreByOvers) {
		this.scoreByOvers = scoreByOvers;
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
