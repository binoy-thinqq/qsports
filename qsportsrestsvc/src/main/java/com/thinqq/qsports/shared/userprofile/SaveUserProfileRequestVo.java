package com.thinqq.qsports.shared.userprofile;

import java.util.Date;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SaveUserProfileRequestVo extends QSportsRequestVo {
	private static final long serialVersionUID = -8983430920090988023L;
	private String name;
	private String sex;
	private Date dob;
	private String city;
	private String state;
	private String country;
	private String timeZoneStr;
	private Integer battingHandType; 
	private Integer battingOrder;
	private Integer bowlingHandType;
	private Integer bowlingMethod;
	private Integer fieldPosition;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
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
	public String getTimeZoneStr() {
		return timeZoneStr;
	}
	public void setTimeZoneStr(String timeZoneStr) {
		this.timeZoneStr = timeZoneStr;
	}
}
