package com.thinqq.qsports.shared.userprofile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.thinqq.qsports.shared.QSportsResponseVo;

@XmlRootElement
public class UserProfileResponseVo extends QSportsResponseVo {
	private static final long serialVersionUID = -6394045131412270762L;
	private String name;
	private String email;
	private String isoCountryCode;
	private String timeZone;
	private String state;
	private String city;
	private String sex;
	private String pictureURL;
	private Date dob;
	private String dobString;
	private String dateOfBirth;
	private Integer battingHandType; 
	private Integer battingOrder;
	private Integer bowlingHandType;
	private Integer bowlingMethod;
	private Integer fieldPosition;
	private String cricketProfileKey;
	private String userKey;
	private boolean isEditable;
	private boolean userNotFound;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
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
	public String getDobString() {
		return dobString;
	}
	public void setDobString(String dobString) {
		this.dobString = dobString;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public boolean isUserNotFound() {
		return userNotFound;
	}
	public void setUserNotFound(boolean userNotFound) {
		this.userNotFound = userNotFound;
	}




}
