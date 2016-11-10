package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true")
public class CricketMatch extends Match {

	@Persistent
	private String team1;
	@Persistent
	private String team2;
	
	
	@Persistent
	private Integer status;
	@Persistent
	private Integer format;
	
	@Persistent
	private Key innings1Key;
	@Persistent
	private Key innings2Key;
	@Persistent
	private Key innings3Key;
	@Persistent
	private Key innings4Key;

	@Persistent
	private String tossWonByTeamKey;
	@Persistent
	private String battingFirstTeamKey;
	
	@Persistent
	private String pitchCondition;
	@Persistent
	private String weather;
	
	@Persistent
	private String wonByTeamKey;
	@Persistent
	private Integer wonByType;
	@Persistent
	private Integer wonByNumbers;
	@Persistent
	private int version;


	@Persistent
	private List<String> team1Players;
	@Persistent
	private List<String> team2Players;
	
	@Persistent
	private List<String> officials;

	@Persistent
	private Key matchOwner;

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Key getInnings1Key() {
		return innings1Key;
	}
	public void setInnings1Key(Key innings1Key) {
		this.innings1Key = innings1Key;
	}
	public Key getInnings2Key() {
		return innings2Key;
	}
	public void setInnings2Key(Key innings2Key) {
		this.innings2Key = innings2Key;
	}
	public Key getInnings3Key() {
		return innings3Key;
	}
	public void setInnings3Key(Key innings3Key) {
		this.innings3Key = innings3Key;
	}
	public Key getInnings4Key() {
		return innings4Key;
	}
	public void setInnings4Key(Key innings4Key) {
		this.innings4Key = innings4Key;
	}
	
	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	public Integer getFormat() {
		return format;
	}
	public void setFormat(Integer format) {
		this.format = format;
	}
	public String getTossWonByTeamKey() {
		return tossWonByTeamKey;
	}
	public void setTossWonByTeamKey(String tossWonByTeamKey) {
		this.tossWonByTeamKey = tossWonByTeamKey;
	}
	public String getBattingFirstTeamKey() {
		return battingFirstTeamKey;
	}
	public void setBattingFirstTeamKey(String battingFirstTeamKey) {
		this.battingFirstTeamKey = battingFirstTeamKey;
	}
	public String getWonByTeamKey() {
		return wonByTeamKey;
	}
	public void setWonByTeamKey(String wonByTeamKey) {
		this.wonByTeamKey = wonByTeamKey;
	}
	public Integer getWonByType() {
		return wonByType;
	}
	public void setWonByType(Integer wonByType) {
		this.wonByType = wonByType;
	}
	public Integer getWonByNumbers() {
		return wonByNumbers;
	}
	public void setWonByNumbers(Integer wonByNumbers) {
		this.wonByNumbers = wonByNumbers;
	}
	public List<String> getTeam1Players() {
		return team1Players;
	}
	public void setTeam1Players(List<String> team1Players) {
		this.team1Players = team1Players;
	}
	public List<String> getTeam2Players() {
		return team2Players;
	}
	public void setTeam2Players(List<String> team2Players) {
		this.team2Players = team2Players;
	}
	public List<String> getOfficials() {
		return officials;
	}
	public void setOfficials(List<String> officials) {
		this.officials = officials;
	}
	public Key getMatchOwner() {
		return matchOwner;
	}
	public void setMatchOwner(Key matchOwner) {
		this.matchOwner = matchOwner;
	}
	
	public String getPitchCondition() {
		return pitchCondition;
	}
	public void setPitchCondition(String pitchCondition) {
		this.pitchCondition = pitchCondition;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
}
