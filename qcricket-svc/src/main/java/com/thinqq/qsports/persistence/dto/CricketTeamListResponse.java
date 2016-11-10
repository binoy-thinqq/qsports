package com.thinqq.qsports.persistence.dto;
import java.util.Date;
import java.util.Set;
public class CricketTeamListResponse extends BaseResponseVo {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -1779619761152015039L;


		/**
		 * 
		 */
		private Integer teamId;

		private String teamName;

		private String city;

		private String state;

		private String country;

		private String description;

		
		private Date createdTime;

		private Integer createdId;

		private Integer updatedId;

		private Date updatedTime;
		
		private boolean isEditable;
		
		private int totalMatches;
		
		private int matchWon;
		
		private int matchLost;
		
		private double winPercentage;
		
		Set<CricketTeamStatsVo> teamStatsVo;

		public Integer getTeamId() {
			return teamId;
		}

		public void setTeamId(Integer teamId) {
			this.teamId = teamId;
		}

		public String getTeamName() {
			return teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getCreatedTime() {
			return createdTime;
		}

		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}

		public Integer getCreatedId() {
			return createdId;
		}

		public void setCreatedId(Integer createdId) {
			this.createdId = createdId;
		}

		public Integer getUpdatedId() {
			return updatedId;
		}

		public void setUpdatedId(Integer updatedId) {
			this.updatedId = updatedId;
		}

		public Date getUpdatedTime() {
			return updatedTime;
		}

		public void setUpdatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
		}
		
		public boolean isEditable() {
			return isEditable;
		}

		public void setEditable(boolean isEditable) {
			this.isEditable = isEditable;
		}

		public Set<CricketTeamStatsVo> getTeamStatsVo() {
			return teamStatsVo;
		}

		public void setTeamStatsVo(Set<CricketTeamStatsVo> teamStatsVo) {
			this.teamStatsVo = teamStatsVo;
		}

		public int getTotalMatches() {
			return totalMatches;
		}

		public void setTotalMatches(int totalMatches) {
			this.totalMatches = totalMatches;
		}

		public int getMatchWon() {
			return matchWon;
		}

		public void setMatchWon(int matchWon) {
			this.matchWon = matchWon;
		}

		public int getMatchLost() {
			return matchLost;
		}

		public void setMatchLost(int matchLost) {
			this.matchLost = matchLost;
		}

		public double getWinPercentage() {
			return winPercentage;
		}

		public void setWinPercentage(double winPercentage) {
			this.winPercentage = winPercentage;
		}
		
		
		
	}

