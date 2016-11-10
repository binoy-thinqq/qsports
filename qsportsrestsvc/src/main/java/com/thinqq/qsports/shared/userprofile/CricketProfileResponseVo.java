package com.thinqq.qsports.shared.userprofile;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.thinqq.qsports.shared.QSportsResponseVo;

@XmlRootElement
public class CricketProfileResponseVo extends QSportsResponseVo {
	private static final long serialVersionUID = -6394045131412270762L;
	private Integer battingHandType; 
	private Integer battingOrder;
	private Integer bowlingHandType;
	private Integer bowlingMethod;
	private Integer fieldPosition;
	private String cricketProfileKey;
	private String userKey;
	private boolean isEditable;
	private List<BattingStatisticsResponseVo> battingStats;
	private List<BowlingStatisticsResponseVo> bowlingStats;
	
	public Integer getBattingHandType() {
		return battingHandType;
	}
	public void setBattingHandType(Integer battingHandType) {
		this.battingHandType = battingHandType;
	}
	public Integer getBattingOrder() {
		return battingOrder;
	}
	public void setBattingOrder(Integer battingOrder) {
		this.battingOrder = battingOrder;
	}
	public Integer getBowlingHandType() {
		return bowlingHandType;
	}
	public void setBowlingHandType(Integer bowlingHandType) {
		this.bowlingHandType = bowlingHandType;
	}
	public Integer getBowlingMethod() {
		return bowlingMethod;
	}
	public void setBowlingMethod(Integer bowlingMethod) {
		this.bowlingMethod = bowlingMethod;
	}
	public Integer getFieldPosition() {
		return fieldPosition;
	}
	public void setFieldPosition(Integer fieldPosition) {
		this.fieldPosition = fieldPosition;
	}
	
	public String getCricketProfileKey() {
		return cricketProfileKey;
	}
	public void setCricketProfileKey(String cricketProfileKey) {
		this.cricketProfileKey = cricketProfileKey;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public List<BattingStatisticsResponseVo> getBattingStats() {
		return battingStats;
	}
	public void setBattingStats(List<BattingStatisticsResponseVo> battingStats) {
		this.battingStats = battingStats;
	}
	public List<BowlingStatisticsResponseVo> getBowlingStats() {
		return bowlingStats;
	}
	public void setBowlingStats(List<BowlingStatisticsResponseVo> bowlingStats) {
		this.bowlingStats = bowlingStats;
	}
	
}
