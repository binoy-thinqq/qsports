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
@Table(name = "cricket.cricket_match_ext")
public class CricketMatchExt implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7359874319155733726L;
	@Id
	@Column(name = "match_ext_id")
	@SequenceGenerator(name = "cricket_match_ext_seq", sequenceName = "cricket.cricket_match_ext_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_match_ext_seq")
	Integer matchExtId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	CricketMatch match;

	@Column(name = "weather_cond")
	String weatherCondition;

	@Column(name = "ground_cond")
	String groundCondition;
	
	@Column(name = "pitch_type")
	String pitchType;
	
	@Column(name = "match_officials")
	String matchOfficials;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

	public Integer getMatchExtId() {
		return matchExtId;
	}

	public void setMatchExtId(Integer matchExtId) {
		this.matchExtId = matchExtId;
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

	public CricketMatch getMatch() {
		return match;
	}

	public void setMatch(CricketMatch match) {
		this.match = match;
	}

	
}
