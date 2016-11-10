package com.thinqq.qsports.client.userprofile.match.edit;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class PlayerSuggestion extends MultiWordSuggestion {
	private String playerName;
	private String additionalInfo;
	private String profileKey;
	private String teamProfileMapKey;
	
	
	public PlayerSuggestion(String playerName, String additionalInfo, String profileKey) {
		super();
		this.playerName = playerName;
		this.additionalInfo = additionalInfo;
		this.profileKey = profileKey;
	}
	
	

	public PlayerSuggestion(String playerName, String additionalInfo,
			String profileKey, String teamProfileMapKey) {
		super();
		this.playerName = playerName;
		this.additionalInfo = additionalInfo;
		this.profileKey = profileKey;
		this.teamProfileMapKey = teamProfileMapKey;
	}



	@Override
	public String getDisplayString() {
		return playerName+" - <br/><span class=\"greyInfo\">"+additionalInfo+"</span>";
	}

	@Override
	public String getReplacementString() {
		return playerName;
	}

	public String getProfileKey() {
		return profileKey;
	}

	public void setProfileKey(String profileKey) {
		this.profileKey = profileKey;
	}

	public String getTeamProfileMapKey() {
		return teamProfileMapKey;
	}

	public void setTeamProfileMapKey(String teamProfileMapKey) {
		this.teamProfileMapKey = teamProfileMapKey;
	}

	public String getPlayerName() {
		return playerName;
	}



}
