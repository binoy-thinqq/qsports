package com.thinqq.qsports.shared.teamprofile;

import java.io.Serializable;
import java.util.List;

import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.QSportsResponseVo;

public class GetTeamPlayerProfileResponseVo extends QSportsResponseVo {
	
	private static final long serialVersionUID = -3799423522514815308L;
	
	private List<TeamPlayerMinDetails> playersList;
	
	
	public static class TeamPlayerMinDetails implements Serializable{

		private static final long serialVersionUID = 8280407433877278891L;
		private NameAndKey player;
		private String cricketProfileKey;
		private String pictureURL;
		private String batting;
		private String bowling;
		private Integer matches;
		private Integer runs;
		private Integer wkts;
		private boolean isOwner;
		
		public TeamPlayerMinDetails(){}
		
		public TeamPlayerMinDetails(NameAndKey player, String batting,
				String bowling, Integer matches, Integer runs, Integer wkts) {
			super();
			this.player = player;
			this.batting = batting;
			this.bowling = bowling;
			this.matches = matches;
			this.runs = runs;
			this.wkts = wkts;
		}



		public NameAndKey getPlayer() {
			return player;
		}
		public void setPlayer(NameAndKey player) {
			this.player = player;
		}
		public String getBatting() {
			return batting;
		}
		public void setBatting(String batting) {
			this.batting = batting;
		}
		public String getBowling() {
			return bowling;
		}
		public void setBowling(String bowling) {
			this.bowling = bowling;
		}
		public Integer getMatches() {
			return matches;
		}
		public void setMatches(Integer matches) {
			this.matches = matches;
		}
		public Integer getRuns() {
			return runs;
		}
		public void setRuns(Integer runs) {
			this.runs = runs;
		}
		public Integer getWkts() {
			return wkts;
		}
		public void setWkts(Integer wkts) {
			this.wkts = wkts;
		}

		public boolean isOwner() {
			return isOwner;
		}

		public void setOwner(boolean isOwner) {
			this.isOwner = isOwner;
		}

		public String getPictureURL() {
			return pictureURL;
		}

		public void setPictureURL(String pictureURL) {
			this.pictureURL = pictureURL;
		}
		
		public String getCricketProfileKey() {
			return cricketProfileKey;
		}

		public void setCricketProfileKey(String cricketProfileKey) {
			this.cricketProfileKey = cricketProfileKey;
		}
		
	}



	public List<TeamPlayerMinDetails> getPlayersList() {
		return playersList;
	}



	public void setPlayersList(List<TeamPlayerMinDetails> playersList) {
		this.playersList = playersList;
	}
}
