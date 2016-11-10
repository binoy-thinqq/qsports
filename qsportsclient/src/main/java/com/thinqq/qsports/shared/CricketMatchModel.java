package com.thinqq.qsports.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CricketMatchModel implements Serializable {
	List<InningsModel> innings;
	String venue;
	String location;
	Date date;
	String format;
	List<TeamModel> teams;
	String team1;
	String team2;
	String matchStatus;
	Long matchId;
	int noOfInnings;
	String tossWonBy;
	String chooseTo;
	int teamIndex;
	String matchSummary;
	int status;

	public List<InningsModel> getInnings() {
		return innings;
	}

	public void setInnings(List<InningsModel> innings) {
		this.innings = innings;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<TeamModel> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamModel> teams) {
		this.teams = teams;
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

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public int getNoOfInnings() {
		return noOfInnings;
	}

	public void setNoOfInnings(int noOfInnings) {
		this.noOfInnings = noOfInnings;
	}

	public String getTossWonBy() {
		return tossWonBy;
	}

	public void setTossWonBy(String tossWonBy) {
		this.tossWonBy = tossWonBy;
	}

	public String getChooseTo() {
		return chooseTo;
	}

	public void setChooseTo(String chooseTo) {
		this.chooseTo = chooseTo;
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int teamIndex) {
		this.teamIndex = teamIndex;
	}

	public String getMatchSummary() {
		return matchSummary;
	}

	public void setMatchSummary(String matchSummary) {
		this.matchSummary = matchSummary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
