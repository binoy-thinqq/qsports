package com.thinqq.qsports.shared;

import java.io.Serializable;
import java.util.List;

public class TeamModel implements Serializable{
	String teamProfileId;
	List<TeamMemberModel> teamList;
	String teamName;
	public String getTeamProfileId() {
		return teamProfileId;
	}
	public void setTeamProfileId(String teamProfileId) {
		this.teamProfileId = teamProfileId;
	}
	public List<TeamMemberModel> getTeamList() {
		return teamList;
	}
	public void setTeamList(List<TeamMemberModel> teamList) {
		this.teamList = teamList;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	

}
