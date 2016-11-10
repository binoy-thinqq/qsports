package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;

public class CricketMatchVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2694401782175914278L;
	
	Integer matchId;
	
	Date matchDate;
	
	@NotNull
	Integer teamId;
	
	Integer oppTeamId;
	
	String oppTeamName;
	
	@NotNull
	String groundName;
	
	@NotNull
	String groundLoc;
	
	@NotNull
	String city;
	
	@NotNull
	String state;
	
	@NotNull
	String country;
	
	Integer maxPlayers;
	
	Integer maxOvers;
	
	@NotNull
	Integer cricketFormat;
	
	String matchWonBy;
	
	Integer winByMargin;
	
	String winReason;
	
	Integer approvedBy;
	
	String approvalComments;
	
	String weatherCondition;
	
	String groundCondition;
	
	String pitchType;
	
	String matchOfficials;
	
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getOppTeamId() {
		return oppTeamId;
	}
	public void setOppTeamId(Integer oppTeamId) {
		this.oppTeamId = oppTeamId;
	}
	public String getOppTeamName() {
		return oppTeamName;
	}
	public void setOppTeamName(String oppTeamName) {
		this.oppTeamName = oppTeamName;
	}
	public String getGroundName() {
		return groundName;
	}
	public void setGroundName(String groundName) {
		this.groundName = groundName;
	}
	public String getGroundLoc() {
		return groundLoc;
	}
	public void setGroundLoc(String groundLoc) {
		this.groundLoc = groundLoc;
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
	public Integer getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public Integer getMaxOvers() {
		return maxOvers;
	}
	public void setMaxOvers(Integer maxOvers) {
		this.maxOvers = maxOvers;
	}
	public Integer getCricketFormat() {
		return cricketFormat;
	}
	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}
	public String getMatchWonBy() {
		return matchWonBy;
	}
	public void setMatchWonBy(String matchWonBy) {
		this.matchWonBy = matchWonBy;
	}
	public Integer getWinByMargin() {
		return winByMargin;
	}
	public void setWinByMargin(Integer winByMargin) {
		this.winByMargin = winByMargin;
	}
	public String getWinReason() {
		return winReason;
	}
	public void setWinReason(String winReason) {
		this.winReason = winReason;
	}
	public Integer getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovalComments() {
		return approvalComments;
	}
	public void setApprovalComments(String approvalComments) {
		this.approvalComments = approvalComments;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public String getGroundCondition() {
		return groundCondition;
	}
	public void setGroundCondition(String groundCondition) {
		this.groundCondition = groundCondition;
	}
	public String getPitchType() {
		return pitchType;
	}
	public void setPitchType(String pitchType) {
		this.pitchType = pitchType;
	}
	public String getMatchOfficials() {
		return matchOfficials;
	}
	public void setMatchOfficials(String matchOfficials) {
		this.matchOfficials = matchOfficials;
	}
	
}
