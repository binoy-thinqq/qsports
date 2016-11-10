package com.thinqq.qsports.shared.match;

import java.util.List;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SaveMatchTeamRequestVo extends QSportsRequestVo {

	private static final long serialVersionUID = -5622316774957048017L;
	private String matchKey;
	private String teamKey;
	private List<String> players;
	
	
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public String getTeamKey() {
		return teamKey;
	}
	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
	public List<String> getPlayers() {
		return players;
	}
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	}
