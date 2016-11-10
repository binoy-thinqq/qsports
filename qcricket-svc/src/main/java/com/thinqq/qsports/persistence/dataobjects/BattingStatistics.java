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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.batting_stats")
public class BattingStatistics implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7574579602847100663L;

	@Id
	@Column(name = "batting_stats_id")
	@SequenceGenerator(name = "batting_stats_seq", sequenceName = "cricket.batting_stats_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batting_stats_seq")
	Integer battingStatsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cricket_profile_id", nullable = false)
	CricketProfile cricketProfile;

	@Column(name = "matches")
	Integer matches = 0;

	@Column(name = "innings")
	Integer innings = 0;

	@Column(name = "not_outs")
	Integer notOuts = 0;

	@Column(name = "runs")
	Integer runs = 0;

	@Column(name = "strike_rate")
	Float strikeRate = 0F;

	@Column(name = "average")
	Float average = 0F;

	@Column(name = "hundreds")
	Integer hundreds = 0;

	@Column(name = "double_hundreds")
	Integer doubleHundreds = 0;

	@Column(name = "tripple_hundreds")
	Integer trippleHundreds = 0;

	@Column(name = "fifties")
	Integer fifties = 0;

	@Column(name = "thirties")
	Integer thirties = 0;

	@Column(name = "sixes")
	Integer sixes = 0;

	@Column(name = "fours")
	Integer fours = 0;

	@Column(name = "cricket_format")
	Integer cricketFormat;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_time")
	Date updateTime;

	@Column(name = "balls_faced")
	Integer ballsFaced = 0;
	
	@Column(name = "hs")
	String highestScore;
	
	public Integer getBattingStatsId() {
		return battingStatsId;
	}

	public void setBattingStatsId(Integer battingStatsId) {
		this.battingStatsId = battingStatsId;
	}

	public CricketProfile getCricketProfile() {
		return cricketProfile;
	}

	public void setCricketProfile(CricketProfile cricketProfile) {
		this.cricketProfile = cricketProfile;
	}

	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

	public Integer getInnings() {
		return innings;
	}

	public void setInnings(Integer innings) {
		this.innings = innings;
	}

	public Integer getNotOuts() {
		return notOuts;
	}

	public void setNotOuts(Integer notOuts) {
		this.notOuts = notOuts;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Float getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(Float strikeRate) {
		this.strikeRate = strikeRate;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getHundreds() {
		return hundreds;
	}

	public void setHundreds(Integer hundreds) {
		this.hundreds = hundreds;
	}

	public Integer getDoubleHundreds() {
		return doubleHundreds;
	}

	public void setDoubleHundreds(Integer doubleHundreds) {
		this.doubleHundreds = doubleHundreds;
	}

	public Integer getTrippleHundreds() {
		return trippleHundreds;
	}

	public void setTrippleHundreds(Integer trippleHundreds) {
		this.trippleHundreds = trippleHundreds;
	}

	public Integer getFifties() {
		return fifties;
	}

	public void setFifties(Integer fifties) {
		this.fifties = fifties;
	}

	public Integer getThirties() {
		return thirties;
	}

	public void setThirties(Integer thirties) {
		this.thirties = thirties;
	}

	public Integer getSixes() {
		return sixes;
	}

	public void setSixes(Integer sixes) {
		this.sixes = sixes;
	}

	public Integer getFours() {
		return fours;
	}

	public void setFours(Integer fours) {
		this.fours = fours;
	}

	public Integer getCricketFormat() {
		return cricketFormat;
	}

	public void setCricketFormat(Integer cricketFormat) {
		this.cricketFormat = cricketFormat;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getBallsFaced() {
		return ballsFaced;
	}

	public void setBallsFaced(Integer ballsFaced) {
		this.ballsFaced = ballsFaced;
	}

	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}
	
	
	
}
