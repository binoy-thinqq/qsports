package com.thinqq.qsports.matchdetails.dto;


public class CricketMatchDetailsTO {

	 Team battingTeam;
	 Team bowlingTeam;
	 int overs;
	 String name;
	
	 String ground;
	public Team getBattingTeam() {
		return battingTeam;
	}
	public void setBattingTeam(Team battingTeam) {
		this.battingTeam = battingTeam;
	}
	public Team getBowlingTeam() {
		return bowlingTeam;
	}
	public void setBowlingTeam(Team bowlingTeam) {
		this.bowlingTeam = bowlingTeam;
	}
	public int getOvers() {
		return overs;
	}
	public void setOvers(int overs) {
		this.overs = overs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGround() {
		return ground;
	}
	public void setGround(String ground) {
		this.ground = ground;
	}
	 
}
