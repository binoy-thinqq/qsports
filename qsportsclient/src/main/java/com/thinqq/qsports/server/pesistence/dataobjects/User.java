package com.thinqq.qsports.server.pesistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class User {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key; 
	@Persistent
	private String displayName; 
	@Persistent
	private String email;
	@Persistent
	private Date dob;
	@Persistent 
	private String birthday; 	 
	@Persistent
	private String gender;	
	@Persistent
	private String city;	
	@Persistent
	private String state;	
	@Persistent
	private String isoCountryCode;	
	@Persistent
	private String timezone;
	@Persistent
	private String locale;
	@Persistent
	private String status;
	@Persistent
	private String userType;
	@Persistent
	private String googleRefreshToken;
	@Persistent
	private String googleUserId;
	@Persistent
	private String googlePictureURL;
	@Persistent
	private String confirmationCode;
	@Persistent
	private Key createdUserKey;
	@Persistent
	private Date createdTime;
	@Persistent
	private Key updatedUserKey;
	@Persistent
	private Date updatedTime;
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Key getCreatedUserKey() {
		return createdUserKey;
	}
	public void setCreatedUserKey(Key createdUserKey) {
		this.createdUserKey = createdUserKey;
	}
	public Key getUpdatedUserKey() {
		return updatedUserKey;
	}
	public void setUpdatedUserKey(Key updatedUserKey) {
		this.updatedUserKey = updatedUserKey;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getGoogleRefreshToken() {
		return googleRefreshToken;
	}
	public void setGoogleRefreshToken(String googleRefreshToken) {
		this.googleRefreshToken = googleRefreshToken;
	}
	public String getGoogleUserId() {
		return googleUserId;
	}
	public void setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getGooglePictureURL() {
		return googlePictureURL;
	}
	public void setGooglePictureURL(String googlePictureURL) {
		this.googlePictureURL = googlePictureURL;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
