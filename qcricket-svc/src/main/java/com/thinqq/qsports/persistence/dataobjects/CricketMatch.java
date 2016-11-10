package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;

@Entity
@Table(name = "cricket.cricket_match")
public class CricketMatch implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8880818420964759935L;

	@Id
	@Column(name = "match_id")
	@SequenceGenerator(name = "cricket_match_seq", sequenceName = "cricket.cricket_match_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_match_seq")
	Integer matchId;

	@Column(name = "match_date")
	@NotNull
	Date matchDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", nullable = false)
	CricketTeam team;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "opp_team_id", referencedColumnName="team_id")
	CricketTeam oppTeam;
	
	@Column(name = "opp_team_name")
	String oppTeamName;
	
	@Column(name = "ground_name")
	@NotNull
	String groundName;
	
	@Column(name = "ground_loc")
	@NotNull
	String groundLoc;
	
	@Column(name = "city")
	@NotNull
	String city;
	
	@Column(name = "state")
	@NotNull
	String state;
	
	@Column(name = "country")
	@NotNull
	String country;

	@Column(name = "max_players")
	Integer maxPlayers;

	@Column(name = "max_overs")
	Integer maxOvers;

	@Column(name = "cricket_format")
	Integer cricketFormat;
	
	@Column(name = "match_status")
	Integer matchStatus;
	
	@Column(name = "toss_won_by")
	Integer tossWonBy;
	
	@Column(name = "elected_to")
	Integer electedTo;
	
	
	@Column(name = "match_won_by")
	Integer matchWonBy;

	@Column(name = "win_by_margin")
	Integer winByMargin;

	@Column(name = "win_reason")
	String winReason;
	
	@Column(name = "approved_by")
	Integer approvedBy; 

	@Column(name = "approval_comments")
	String approvalComments;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

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

	public CricketTeam getTeam() {
		return team;
	}

	public void setTeam(CricketTeam team) {
		this.team = team;
	}

	public CricketTeam getOppTeam() {
		return oppTeam;
	}

	public void setOppTeam(CricketTeam oppTeam) {
		this.oppTeam = oppTeam;
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

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	
}
