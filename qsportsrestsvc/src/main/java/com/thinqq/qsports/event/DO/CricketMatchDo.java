package com.thinqq.qsports.event.DO;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
@PersistenceCapable
public class CricketMatchDo implements MatchDo{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	String typeOfMatch;
	@Persistent
	InningsDo innings1;
	@Persistent
	InningsDo innings2;
	@Persistent
	InningsDo innings3;
	@Persistent
	InningsDo innings4;
	@Persistent
	String venue;
	@Persistent
	Date date;
	@Persistent
	MatchStatusEnum status;
	@Persistent
	String teamWon;
	@Persistent
	String playingTeam1;
	@Persistent
	String playingTeam2;
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getTypeOfMatch() {
		return typeOfMatch;
	}
	public void setTypeOfMatch(String typeOfMatch) {
		this.typeOfMatch = typeOfMatch;
	}
	public InningsDo getInnings1() {
		return innings1;
	}
	public void setInnings1(InningsDo innings1) {
		this.innings1 = innings1;
	}
	public InningsDo getInnings2() {
		return innings2;
	}
	public void setInnings2(InningsDo innings2) {
		this.innings2 = innings2;
	}
	public InningsDo getInnings3() {
		return innings3;
	}
	public void setInnings3(InningsDo innings3) {
		this.innings3 = innings3;
	}
	public InningsDo getInnings4() {
		return innings4;
	}
	public void setInnings4(InningsDo innings4) {
		this.innings4 = innings4;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public MatchStatusEnum getStatus() {
		return status;
	}
	public void setStatus(MatchStatusEnum status) {
		this.status = status;
	}
	public String getTeamWon() {
		return teamWon;
	}
	public void setTeamWon(String teamWon) {
		this.teamWon = teamWon;
	}
	public String getPlayingTeam1() {
		return playingTeam1;
	}
	public void setPlayingTeam1(String playingTeam1) {
		this.playingTeam1 = playingTeam1;
	}
	public String getPlayingTeam2() {
		return playingTeam2;
	}
	public void setPlayingTeam2(String playingTeam2) {
		this.playingTeam2 = playingTeam2;
	}
	

}
