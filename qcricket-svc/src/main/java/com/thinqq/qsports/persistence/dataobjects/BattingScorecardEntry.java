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
@Table(name = "cricket.scorecard_batting_entry")
public class BattingScorecardEntry implements DataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -614737583434818510L;

	@Id
	@Column(name = "batting_entry_id")
	@SequenceGenerator(name = "scorecard_batting_entry_seq", sequenceName = "cricket.scorecard_batting_entry_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scorecard_batting_entry_seq")
	Integer battingEntryId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_team_player_id", nullable = false)
	CricketMatchTeamPlayers player;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "innings_id", nullable = false)
	Innings innings;
	  
	@Column(name = "batting_order")
	Integer battingOrder;
	
	@Column(name = "runs")
	Integer runs;
	  
	@Column(name = "balls_faced")
	Integer ballsFaced;
	
	@Column(name = "minutes")
	Integer minutes;
	
	@Column(name = "dots")
	Integer dots;
	
	@Column(name = "singles")
	Integer singles;
	
	@Column(name = "doubles")
	Integer doubles;
	  
	@Column(name = "tripples")
	Integer tripples;
	
	@Column(name = "fours")
	Integer fours;
	  
	@Column(name = "five")
	Integer five;
	
	@Column(name = "six")
	Integer six;
	
	@Column(name = "seven")
	Integer seven;
	
	@Column(name = "eight")
	Integer eight;
	
	@Column(name = "nine")
	Integer nine;

	
	@Column(name = "out_type")
	Integer outType;
	
	@Column(name = "fielder")
	Integer fielder;
	
	@Column(name = "bowler_fielder")
	Integer bowlerFielder;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

	public Integer getBattingEntryId() {
		return battingEntryId;
	}

	public void setBattingEntryId(Integer battingEntryId) {
		this.battingEntryId = battingEntryId;
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

	public Integer getBattingOrder() {
		return battingOrder;
	}

	public void setBattingOrder(Integer battingOrder) {
		this.battingOrder = battingOrder;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Integer getBallsFaced() {
		return ballsFaced;
	}

	public void setBallsFaced(Integer ballsFaced) {
		this.ballsFaced = ballsFaced;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getDots() {
		return dots;
	}

	public void setDots(Integer dots) {
		this.dots = dots;
	}

	public Integer getSingles() {
		return singles;
	}

	public void setSingles(Integer singles) {
		this.singles = singles;
	}

	public Integer getDoubles() {
		return doubles;
	}

	public void setDoubles(Integer doubles) {
		this.doubles = doubles;
	}

	public Integer getTripples() {
		return tripples;
	}

	public void setTripples(Integer tripples) {
		this.tripples = tripples;
	}

	public Integer getFours() {
		return fours;
	}

	public void setFours(Integer fours) {
		this.fours = fours;
	}

	public Integer getSix() {
		return six;
	}

	public void setSix(Integer six) {
		this.six = six;
	}

	public Integer getOutType() {
		return outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public Integer getFielder() {
		return fielder;
	}

	public void setFielder(Integer fielder) {
		this.fielder = fielder;
	}

	public Integer getBowlerFielder() {
		return bowlerFielder;
	}

	public void setBowlerFielder(Integer bowlerFielder) {
		this.bowlerFielder = bowlerFielder;
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

	public Integer getFive() {
		return five;
	}

	public void setFive(Integer five) {
		this.five = five;
	}

	public Integer getSeven() {
		return seven;
	}

	public void setSeven(Integer seven) {
		this.seven = seven;
	}

	public Integer getEight() {
		return eight;
	}

	public void setEight(Integer eight) {
		this.eight = eight;
	}

	public Integer getNine() {
		return nine;
	}

	public void setNine(Integer nine) {
		this.nine = nine;
	}
	
	
}
