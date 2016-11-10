package com.thinqq.qsports.persistence.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinqq.qsports.persistence.validation.annotation.ExpectedValue;
import com.thinqq.qsports.persistence.validation.annotation.NotNull;


@XmlRootElement
public class CricketProfileMinVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 831314019107106363L;

	@NotNull
	@XmlElement(required = false)
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
	
	@XmlElement(required = false)
	Boolean wicketKeeper;
	

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

}
