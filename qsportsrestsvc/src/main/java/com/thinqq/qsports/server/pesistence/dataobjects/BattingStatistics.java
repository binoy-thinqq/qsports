package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class BattingStatistics {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;  
	@Persistent
	private Integer matches;
	@Persistent
	private Integer inn;
	@Persistent
	private Integer no;   
	@Persistent
	private Integer runs;
	@Persistent
	private Float sr;
	@Persistent
	private Float avg;
	@Persistent
	private Integer hs;
	@Persistent
	private Integer hunds;
	@Persistent
	private Integer fifs;
	@Persistent
	private Integer thirts;
	@Persistent
	private Integer sixes;
	@Persistent
	private Integer fours;
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
	
	
	public BattingStatistics(Integer matches, Integer inn, Integer no,
			Integer runs, Float sr, Float avg, Integer hs, Integer hunds,
			Integer fifs, Integer thirts, Integer sixes, Integer fours,
			Key cricketProfile, Integer cricketFormat, Key createdUserKey,
			Date createdTime, Key updatedUserKey, Date updatedTime) {
		super();
		this.matches = matches;
		this.inn = inn;
		this.no = no;
		this.runs = runs;
		this.sr = sr;
		this.avg = avg;
		this.hs = hs;
		this.hunds = hunds;
		this.fifs = fifs;
		this.thirts = thirts;
		this.sixes = sixes;
		this.fours = fours;
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
	public Integer getMatches() {
		return matches;
	}
	public void setMatches(Integer matches) {
		this.matches = matches;
	}
	public Integer getInn() {
		return inn;
	}
	public void setInn(Integer inn) {
		this.inn = inn;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getRuns() {
		return runs;
	}
	public void setRuns(Integer runs) {
		this.runs = runs;
	}
	public Float getSr() {
		return sr;
	}
	public void setSr(Float sr) {
		this.sr = sr;
	}
	public Float getAvg() {
		return avg;
	}
	public void setAvg(Float avg) {
		this.avg = avg;
	}
	public Integer getHs() {
		return hs;
	}
	public void setHs(Integer hs) {
		this.hs = hs;
	}
	public Integer getHunds() {
		return hunds;
	}
	public void setHunds(Integer hunds) {
		this.hunds = hunds;
	}
	public Integer getFifs() {
		return fifs;
	}
	public void setFifs(Integer fifs) {
		this.fifs = fifs;
	}
	public Integer getThirts() {
		return thirts;
	}
	public void setThirts(Integer thirts) {
		this.thirts = thirts;
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
