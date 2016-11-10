package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.cricket_match_innings")
public class Innings implements DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2247262791862534568L;

	@Id
	@Column(name = "innings_id")
	@SequenceGenerator(name = "cricket_match_innings_seq", sequenceName = "cricket.cricket_match_innings_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_match_innings_seq")
	Integer inningsId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	CricketMatch match;
	  
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", nullable = true)
	CricketTeam team;
	
	@Column(name = "innings_order")
	Integer order;
	
	@Column(name = "team_name")
	String teamName;
	
	@Column(name = "byes")
	Integer byes;
	
	@Column(name = "wide")
	Integer wide;
	
	@Column(name = "no_ball")
	Integer noBall;
	
	@Column(name = "leg_byes")
	Integer legByes;
	
	@Column(name = "penalty_runs")
	Integer penaltyRuns;
	
	@Column(name = "total_runs")
	Integer totalRuns;
	
	@Column(name = "overs")
	Double overs;
	
	@Column(name = "revised_target")
	Integer revisedTarget;
	
	@Column(name = "revised_overs")
	Double revisedOvers;
	
	@Column(name = "wickets")
	Integer wickets;
	
	@Column(name = "follow_on")
	boolean followOn;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;
	
	@Column(name = "revised_reason")
	String revisedReason;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="innings", cascade=CascadeType.ALL)
	Set<BattingScorecardEntry> battingEntries;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="innings", cascade=CascadeType.ALL)
	Set<BowlingScorecardEntry> bowlingEntries;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="innings", cascade=CascadeType.ALL)
	Set<MatchFow> fallOfWickets;

	public Integer getInningsId() {
		return inningsId;
	}

	public void setInningsId(Integer inningsId) {
		this.inningsId = inningsId;
	}

	public CricketMatch getMatch() {
		return match;
	}

	public void setMatch(CricketMatch match) {
		this.match = match;
	}

	public CricketTeam getTeam() {
		return team;
	}

	public void setTeam(CricketTeam team) {
		this.team = team;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getByes() {
		return byes;
	}

	public void setByes(Integer byes) {
		this.byes = byes;
	}

	public Integer getWide() {
		return wide;
	}

	public void setWide(Integer wide) {
		this.wide = wide;
	}

	public Integer getNoBall() {
		return noBall;
	}

	public void setNoBall(Integer noBall) {
		this.noBall = noBall;
	}

	public Integer getLegByes() {
		return legByes;
	}

	public void setLegByes(Integer legByes) {
		this.legByes = legByes;
	}

	public Integer getPenaltyRuns() {
		return penaltyRuns;
	}

	public void setPenaltyRuns(Integer penaltyRuns) {
		this.penaltyRuns = penaltyRuns;
	}

	public Integer getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(Integer totalRuns) {
		this.totalRuns = totalRuns;
	}

	public Double getOvers() {
		return overs;
	}

	public void setOvers(Double overs) {
		this.overs = overs;
	}

	public Integer getRevisedTarget() {
		return revisedTarget;
	}

	public void setRevisedTarget(Integer revisedTarget) {
		this.revisedTarget = revisedTarget;
	}

	public Integer getWickets() {
		return wickets;
	}

	public void setWickets(Integer wickets) {
		this.wickets = wickets;
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

	public Set<BattingScorecardEntry> getBattingEntries() {
		return battingEntries;
	}

	public void setBattingEntries(Set<BattingScorecardEntry> battingEntries) {
		this.battingEntries = battingEntries;
	}

	public Set<BowlingScorecardEntry> getBowlingEntries() {
		return bowlingEntries;
	}

	public void setBowlingEntries(Set<BowlingScorecardEntry> bowlingEntries) {
		this.bowlingEntries = bowlingEntries;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Set<MatchFow> getFallOfWickets() {
		return fallOfWickets;
	}

	public void setFallOfWickets(Set<MatchFow> fallOfWickets) {
		this.fallOfWickets = fallOfWickets;
	}

	public boolean isFollowOn() {
		return followOn;
	}

	public void setFollowOn(boolean followOn) {
		this.followOn = followOn;
	}

	public Double getRevisedOvers() {
		return revisedOvers;
	}

	public void setRevisedOvers(Double revisedOvers) {
		this.revisedOvers = revisedOvers;
	}

	public String getRevisedReason() {
		return revisedReason;
	}

	public void setRevisedReason(String revisedReason) {
		this.revisedReason = revisedReason;
	}
 }
