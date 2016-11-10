package com.thinqq.qsports.client.cricket.matchview.bowlingcard;

import java.util.Map;

import com.thinqq.qsports.client.common.IComponentModel;
import com.thinqq.qsports.client.cricket.matchview.bowlingentry.BowlingEntryModel;

public class BowlingCardModel implements IComponentModel {

	private String teamName;
	private Map<Long,BowlingEntryModel> battingEntries;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Map<Long,BowlingEntryModel> getBattingEntries() {
		return battingEntries;
	}
	public void setBattingEntries(Map<Long,BowlingEntryModel> battingEntries) {
		this.battingEntries = battingEntries;
	}
}
