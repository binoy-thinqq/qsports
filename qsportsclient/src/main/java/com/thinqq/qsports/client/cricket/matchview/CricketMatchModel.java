package com.thinqq.qsports.client.cricket.matchview;

import java.util.List;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.TeamProfileMinData;
import com.thinqq.qsports.client.cricket.matchview.innings.InningsComponentModel;

public class CricketMatchModel implements IComponentModel {
	private List<InningsComponentModel> inningsList;
	private TeamProfileMinData team1;
	private TeamProfileMinData team2;
	private String location;
	private String format;
	private String wonByString;
	private List<String> matchHighlights;
	private String status;
	
	public List<InningsComponentModel> getInningsList() {
		return inningsList;
	}
	public void setInningsList(List<InningsComponentModel> inningsList) {
		this.inningsList = inningsList;
	}
	public TeamProfileMinData getTeam1() {
		return team1;
	}
	public void setTeam1(TeamProfileMinData team1) {
		this.team1 = team1;
	}
	public TeamProfileMinData getTeam2() {
		return team2;
	}
	public void setTeam2(TeamProfileMinData team2) {
		this.team2 = team2;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getWonByString() {
		return wonByString;
	}
	public void setWonByString(String wonByString) {
		this.wonByString = wonByString;
	}
	public List<String> getMatchHighlights() {
		return matchHighlights;
	}
	public void setMatchHighlights(List<String> matchHighlights) {
		this.matchHighlights = matchHighlights;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	


}
