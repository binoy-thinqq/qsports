package com.thinqq.qsports.persistence.dto;

import java.util.Date;
import java.util.List;

public class CricketMatchResponseVo extends BaseResponseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer matchId;
	
	Date matchDate;
	
	Integer teamId;
	
	String teamName;
	
	Integer oppTeamId;
	
	String oppTeamName;
	
	String groundName;
	
	String groundLoc;
	
	String city;
	
	String state;
 
	String country;
	
	Integer maxPlayers;
	
	Integer maxOvers;
	
	Integer cricketFormat;
	
	Integer matchStatus;
	
	Integer tossWonBy;
	
	Integer electedTo;
	
	Integer matchWonBy;
	
	Integer winByMargin;
	
	String winReason;
	
	Integer approvedByID;
	
	String approvalComments;
	
	boolean isEditable;
	
	String weatherCondition;
	
	String groundCondition;
	
	String pitchType;
	
	String matchOfficials;
	
	List<CricketMatchTeamPlayersRespVo> teamPlayers;
	
	List<CricketMatchTeamPlayersRespVo> teamOppPlayers;
	
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
	public Integer getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}
	public Integer getTossWonBy() {
		return tossWonBy;
	}
	public void setTossWonBy(Integer tossWonBy) {
		this.tossWonBy = tossWonBy;
	}
	public Integer getElectedTo() {
		return electedTo;
	}
	public void setElectedTo(Integer electedTo) {
		this.electedTo = electedTo;
	}

	public Integer getMatchWonBy() {
		return matchWonBy;
	}
	public void setMatchWonBy(Integer matchWonBy) {
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
	public Integer getApprovedById() {
		return approvedByID;
	}
	public void setApprovedById(Integer approvedBy) {
		this.approvedByID = approvedBy;
	}
	public String getApprovalComments() {
		return approvalComments;
	}
	public void setApprovalComments(String approvalComments) {
		this.approvalComments = approvalComments;
	}
	public List<CricketMatchTeamPlayersRespVo> getTeamPlayers() {
		return teamPlayers;
	}
	public void setTeamPlayers(List<CricketMatchTeamPlayersRespVo> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}
	public List<CricketMatchTeamPlayersRespVo> getTeamOppPlayers() {
		return teamOppPlayers;
	}
	public void setTeamOppPlayers(List<CricketMatchTeamPlayersRespVo> teamOppPlayers) {
		this.teamOppPlayers = teamOppPlayers;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
