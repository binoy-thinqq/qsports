package com.thinqq.qsports.persistence.dto;


import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.thinqq.qsports.persistence.validation.annotation.ExpectedValue;
import com.thinqq.qsports.persistence.validation.annotation.NotNull;


@XmlRootElement
public class CricketProfileVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 831314019107106363L;


	@NotNull
	Integer cricketProfileId;
	
	@ExpectedValue(values={"0","1","2"})
	String battingHandType;
	
	@ExpectedValue(values={"0","1","2","3","4","5","6"})
	String battingOrder;
	
	@ExpectedValue(values={"0","1","2"})
	String bowlingHandType;
	
	@ExpectedValue(values={"0","1","2","3","4","5","6","8","9","10","11"})
	String bowlingMethod;
	
	@ExpectedValue(values={"0","1","2","3","4","5","6","8","9","10","11","12","13"})
	String fieldPosition;
	
	Boolean wicketKeeper;
	
	Integer createdId;
	
	Date createdTime;
	
	Integer updatedId;
	
	Date updatedTime;
		
	Set<BattingStatisticsVo> battingStats;
	
	Set<BowlingStatisticsVo> bowlingStats;
	
	UserInfoVo userInfo;

	public Integer getCricketProfileId() {
		return cricketProfileId;
	}

	public void setCricketProfileId(Integer cricketProfileId) {
		this.cricketProfileId = cricketProfileId;
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

	public Set<BattingStatisticsVo> getBattingStats() {
		return battingStats;
	}

	public void setBattingStats(Set<BattingStatisticsVo> battingStats) {
		this.battingStats = battingStats;
	}

	public Set<BowlingStatisticsVo> getBowlingStats() {
		return bowlingStats;
	}

	public void setBowlingStats(Set<BowlingStatisticsVo> bowlingStats) {
		this.bowlingStats = bowlingStats;
	}

	public UserInfoVo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoVo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	
}
