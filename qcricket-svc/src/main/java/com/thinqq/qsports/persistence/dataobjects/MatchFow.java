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


@Entity
@Table(name = "cricket.match_fow")
public class MatchFow implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1301322320489571503L;

	@Id
	@Column(name = "fow_id")
	@SequenceGenerator(name = "match_fow_seq", sequenceName = "cricket.match_fow_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_fow_seq")
	Integer fowId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batting_entry_id", nullable = false)
	BattingScorecardEntry battingEntry;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "innings_id", nullable = false)
	Innings innings;
	
	@Column(name = "runs")
	Integer runs;
	
	@Column(name = "overs")
	Double overs;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

	public Integer getFowId() {
		return fowId;
	}

	public void setFowId(Integer fowId) {
		this.fowId = fowId;
	}

	public BattingScorecardEntry getBattingEntry() {
		return battingEntry;
	}

	public void setBattingEntry(BattingScorecardEntry battingEntry) {
		this.battingEntry = battingEntry;
	}

	public Innings getInnings() {
		return innings;
	}

	public void setInnings(Innings innings) {
		this.innings = innings;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Double getOvers() {
		return overs;
	}

	public void setOvers(Double overs) {
		this.overs = overs;
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
