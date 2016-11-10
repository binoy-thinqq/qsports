package com.thinqq.qsports.client.userprofile.match.edit;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class TeamSuggestion extends MultiWordSuggestion {
	private String teamName;
	private String additionalInfo;
	private String teamKey;
	
	
	public TeamSuggestion(String teamName, String additionalInfo, String teamKey) {
		super();
		this.teamName = teamName;
		this.additionalInfo = additionalInfo;
		this.teamKey = teamKey;
	}

	@Override
	public String getDisplayString() {
		return teamName+" - <span class=\"greyInfo\">"+additionalInfo+"</span>";
	}

	@Override
	public String getReplacementString() {
		return teamName;
	}

	public String getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}

}
