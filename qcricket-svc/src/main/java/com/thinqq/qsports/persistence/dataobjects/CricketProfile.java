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
@Table(name="cricket.cricket_profile")
public class CricketProfile implements DataObject {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1581887598488128257L;
	
	private static final String NOT_DEFINED = "0";

	@Id
	@Column(name = "cricket_profile_id")
	@SequenceGenerator(name = "cricket_profile_seq", sequenceName = "cricket.cricket_profile_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cricket_profile_seq")
	Integer cricketProfileId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	User user;

	@Column(name = "batting_hand_type") 
	String battingHandType = NOT_DEFINED;
	
	
	@Column(name = "batting_order")
	String battingOrder = NOT_DEFINED;
	
	@Column(name = "bowling_hand_type") 
	String bowlingHandType = NOT_DEFINED;
	
	@Column(name = "bowling_method") 
	String bowlingMethod = NOT_DEFINED;
	
	@Column(name = "field_position") 
	String fieldPosition = NOT_DEFINED;
	
	@Column(name = "wicket_keeper") 
	Boolean wicketKeeper;
	
	@Column(name = "created_id") 
	Integer createdId;
	
	@Column(name = "created_time") 
	Date createdTime;
	
	@Column(name = "updated_id") 
	Integer updatedId;
	
	@Column(name = "updated_time") 
	Date updatedTime;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cricketProfile", cascade=CascadeType.ALL)
	Set<BattingStatistics> battingStats;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cricketProfile", cascade=CascadeType.ALL)
	Set<BowlingStatistics> bowlingStats;
	
	
	public Integer getCricketProfileId() {
		return cricketProfileId;
	}

	public void setCricketProfileId(Integer cricketProfileId) {
		this.cricketProfileId = cricketProfileId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBattingHandType() {
		return battingHandType;
	}

	public void setBattingHandType(String battingHandType) {
		this.battingHandType = battingHandType;
	}

	public String getBattingOrder() {
		return battingOrder;
	}

	public void setBattingOrder(String battingOrder) {
		this.battingOrder = battingOrder;
	}

	public String getBowlingHandType() {
		return bowlingHandType;
	}

	public void setBowlingHandType(String bowlingHandType) {
		this.bowlingHandType = bowlingHandType;
	}

	public String getBowlingMethod() {
		return bowlingMethod;
	}

	public void setBowlingMethod(String bowlingMethod) {
		this.bowlingMethod = bowlingMethod;
	}

	public String getFieldPosition() {
		return fieldPosition;
	}

	public void setFieldPosition(String fieldPosition) {
		this.fieldPosition = fieldPosition;
	}

	public Boolean getWicketKeeper() {
		return wicketKeeper;
	}

	public void setWicketKeeper(Boolean wicketKeeper) {
		this.wicketKeeper = wicketKeeper;
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

	public Set<BattingStatistics> getBattingStats() {
		return battingStats;
	}

	public void setBattingStats(Set<BattingStatistics> battingStats) {
		this.battingStats = battingStats;
	}

	public Set<BowlingStatistics> getBowlingStats() {
		return bowlingStats;
	}

	public void setBowlingStats(Set<BowlingStatistics> bowlingStats) {
		this.bowlingStats = bowlingStats;
	}
}
