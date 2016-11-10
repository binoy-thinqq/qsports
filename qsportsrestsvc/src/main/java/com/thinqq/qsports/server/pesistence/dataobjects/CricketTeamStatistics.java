package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true")
public class CricketTeamStatistics {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private Integer mat;   
	@Persistent
	private Integer win;
	@Persistent
	private Integer loss;
	@Persistent
	private Integer noResult;
	@Persistent
	private Integer tied;
	@Persistent
	private Integer hs;
	@Persistent
	private Key nextMatch;
	@Persistent
	private Key cricketTeam;
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

	
	public CricketTeamStatistics(Integer mat, Integer win, Integer loss,
			Integer noResult, Integer tied, Integer hs, Key nextMatch,
			Key cricketTeam, Integer cricketFormat, Key createdUserKey,
			Date createdTime, Key updatedUserKey, Date updatedTime) {
		super();
		this.mat = mat;
		this.win = win;
		this.loss = loss;
		this.noResult = noResult;
		this.tied = tied;
		this.hs = hs;
		this.nextMatch = nextMatch;
		this.cricketTeam = cricketTeam;
		this.cricketFormat = cricketFormat;
		this.createdUserKey = createdUserKey;
		this.createdTime = createdTime;
		this.updatedUserKey = updatedUserKey;
		this.updatedTime = updatedTime;
	}
	public Integer getWin() {
		return win;
	}
	public void setWin(Integer win) {
		this.win = win;
	}
	public Integer getLoss() {
		return loss;
	}
	public void setLoss(Integer loss) {
		this.loss = loss;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
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
	public Key getCricketTeam() {
		return cricketTeam;
	}
	public void setCricketTeam(Key cricketTeam) {
		this.cricketTeam = cricketTeam;
	}
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public Integer getNoResult() {
		return noResult;
	}
	public void setNoResult(Integer noResult) {
		this.noResult = noResult;
	}
	public Integer getTied() {
		return tied;
	}
	public void setTied(Integer tied) {
		this.tied = tied;
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
	public Integer getMat() {
		return mat;
	}
	public void setMat(Integer mat) {
		this.mat = mat;
	}
	public Integer getHs() {
		return hs;
	}
	public void setHs(Integer hs) {
		this.hs = hs;
	}
	public Key getNextMatch() {
		return nextMatch;
	}
	public void setNextMatch(Key nextMatch) {
		this.nextMatch = nextMatch;
	}


}
