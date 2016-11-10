package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class FieldingStatistics {
	@PrimaryKey 
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key; 
	@Persistent
	private Integer catches;	 
	@Persistent	
	private Integer stumpings;
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
	
	public FieldingStatistics(Integer catches, Integer stumpings,
			Key cricketProfile, Integer cricketFormat, Key createdUserKey,
			Date createdTime, Key updatedUserKey, Date updatedTime) {
		super();
		this.catches = catches;
		this.stumpings = stumpings;
		this.cricketProfile = cricketProfile;
		this.cricketFormat = cricketFormat;
		this.createdUserKey = createdUserKey;
		this.createdTime = createdTime;
		this.updatedUserKey = updatedUserKey;
		this.updatedTime = updatedTime;
	}
	public Integer getCatches() {
		return catches;
	}
	public void setCatches(Integer catches) {
		this.catches = catches;
	}
	public Integer getStumpings() {
		return stumpings;
	}
	public void setStumpings(Integer stumpings) {
		this.stumpings = stumpings;
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
