package com.thinqq.qsports.shared;

import java.io.Serializable;

public class TeamMemberModel implements Serializable{
	public String teamProfileId="";
	public String playerName="";
	public String playerProfileId="";
	public String getTeamProfileId() {
		return teamProfileId;
	}
	public void setTeamProfileId(String teamProfileId) {
		this.teamProfileId = teamProfileId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerProfileId() {
		return playerProfileId;
	}
	public void setPlayerProfileId(String playerProfileId) {
		this.playerProfileId = playerProfileId;
	}

}
