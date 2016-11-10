package com.thinqq.qsports.persistence.dto;

import java.util.Date;

public class CricketMatchRequestVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer matchId;

	Date matchDate;
	
	Integer teamId;
	
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
	
	String matchStatus;
	
	String tossWonBy;
	
	String electedTo;
	
	Integer innId1;
	
	Integer innId2;
	
	Integer innId3;
	
	Integer innId4;
	
	String matchWonBy;

	Integer winByMargin;

	String winReason;
	
	Integer approvedById; 

	String approvalComments;

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

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	public String getTossWonBy() {
		return tossWonBy;
	}

	public void setTossWonBy(String tossWonBy) {
		this.tossWonBy = tossWonBy;
	}

	public String getElectedTo() {
		return electedTo;
	}

	public void setElectedTo(String electedTo) {
		this.electedTo = electedTo;
	}

	public Integer getInnId1() {
		return innId1;
	}

	public void setInnId1(Integer innId1) {
		this.innId1 = innId1;
	}

	public Integer getInnId2() {
		return innId2;
	}

	public void setInnId2(Integer innId2) {
		this.innId2 = innId2;
	}

	public Integer getInnId3() {
		return innId3;
	}

	public void setInnId3(Integer innId3) {
		this.innId3 = innId3;
	}

	public Integer getInnId4() {
		return innId4;
	}

	public void setInnId4(Integer innId4) {
		this.innId4 = innId4;
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

	public Integer getApprovedById() {
		return approvedById;
	}

	public void setApprovedById(Integer approvedById) {
		this.approvedById = approvedById;
	}

	public String getApprovalComments() {
		return approvalComments;
	}

	public void setApprovalComments(String approvalComments) {
		this.approvalComments = approvalComments;
	}
	
	

}
