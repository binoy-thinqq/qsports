package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.scorecard_bowling_entry")
public class BowlingScorecardEntry implements DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1664260561496219429L;

	@Id
	@Column(name = "bowling_entry_id")
	@SequenceGenerator(name = "scorecard_bowling_entry_seq", sequenceName = "cricket.scorecard_bowling_entry_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scorecard_bowling_entry_seq")
	Integer bowlingEntryId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_team_player_id", nullable = false)
	CricketMatchTeamPlayers player;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "innings_id", nullable = false)
	Innings innings;
	
	
	@Column(name = "overs")
	Double overs;
	
	@Column(name = "runs")
	Integer runs;
	
	@Column(name = "wickets")
	Integer wickets;
	
	@Column(name = "no_balls")
	Integer noBalls;
	
	@Column(name = "wide")
	Integer wide;
	@Column(name = "maiden")
	Integer maiden;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

	public Integer getBowlingEntryId() {
		return bowlingEntryId;
	}

	public void setBowlingEntryId(Integer bowlingEntryId) {
		this.bowlingEntryId = bowlingEntryId;
	}

	public CricketMatchTeamPlayers getPlayer() {
		return player;
	}

	public void setPlayer(CricketMatchTeamPlayers player) {
		this.player = player;
	}

	public Innings getInnings() {
		return innings;
	}

	public void setInnings(Innings innings) {
		this.innings = innings;
	}

	public Double getOvers() {
		return overs;
	}

	public void setOvers(Double overs) {
		this.overs = overs;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Integer getWickets() {
		return wickets;
	}

	public void setWickets(Integer wickets) {
		this.wickets = wickets;
	}

	public Integer getNoBalls() {
		return noBalls;
	}

	public void setNoBalls(Integer noBalls) {
		this.noBalls = noBalls;
	}

	public Integer getWide() {
		return wide;
	}

	public void setWide(Integer wide) {
		this.wide = wide;
	}

	public Integer getMaiden() {
		return maiden;
	}

	public void setMaiden(Integer maiden) {
		this.maiden = maiden;
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
