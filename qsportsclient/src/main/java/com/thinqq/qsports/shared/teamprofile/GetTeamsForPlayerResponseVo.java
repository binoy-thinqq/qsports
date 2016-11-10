package com.thinqq.qsports.shared.teamprofile;

import java.io.Serializable;
import java.util.List;

import com.thinqq.qsports.shared.QSportsResponseVo;

public class GetTeamsForPlayerResponseVo extends QSportsResponseVo {

	private static final long serialVersionUID = 1121836203167185937L;
	
	List<TeamProfileMinDetails> teams;
	
	public static class TeamProfileMinDetails implements Serializable{

		private static final long serialVersionUID = -4968712841064243241L;
		
		private String pictureURL;
		private String name;
		private String teamKey;
		private String cityState;
		private String country;
		private int matches;
		private int won;
		private int lost;
		private String winPercent;
		private int playersCount;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTeamKey() {
			return teamKey;
		}
		public void setTeamKey(String teamKey) {
			this.teamKey = teamKey;
		}
		public String getCityState() {
			return cityState;
		}
		public void setCityState(String cityState) {
			this.cityState = cityState;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public int getMatches() {
			return matches;
		}
		public void setMatches(int matches) {
			this.matches = matches;
		}
		public int getWon() {
			return won;
		}
		public void setWon(int won) {
			this.won = won;
		}
		public int getLost() {
			return lost;
		}
		public void setLost(int lost) {
			this.lost = lost;
		}
		public String getWinPercent() {
			return winPercent;
		}
		public void setWinPercent(String winPercent) {
			this.winPercent = winPercent;
		}
		public int getPlayersCount() {
			return playersCount;
		}
		public void setPlayersCount(int playersCount) {
			this.playersCount = playersCount;
		}
		public String getPictureURL() {
			return pictureURL;
		}
		public void setPictureURL(String pictureURL) {
			this.pictureURL = pictureURL;
		}
		
	}

	public List<TeamProfileMinDetails> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamProfileMinDetails> teams) {
		this.teams = teams;
	}

}
