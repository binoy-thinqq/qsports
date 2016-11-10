package com.thinqq.qsports.server.oauth;

import java.io.Serializable;
import java.util.TimeZone;




public class UserInfo  implements Serializable{
	private static final long serialVersionUID = -4867778411513691505L;
	private String id;
	private String email;
	private boolean verifiedEmail;
	private String name;
	private String givenName;
	private String familyName;
	private String picture;
	private String gender;
	private String locale;
	private String link;
	private String birthday;
	private String refreshToken;
	private String qSportsUserKey;
	private String qSportCricketProfileKey;
	private TimeZone userTimeZone;
	private String timeZoneId;
	
	public UserInfo(String id, String refreshToken, String email, boolean verifiedEmail, String name,
			String givenName, String familyName, String picture, String gender,
			String locale, String link, String birthday) {
		super();
		this.id = id;
		this.refreshToken = refreshToken;
		this.email = email;
		this.verifiedEmail = verifiedEmail;
		this.name = name;
		this.givenName = givenName;
		this.familyName = familyName;
		this.picture = picture;
		this.gender = gender;
		this.locale = locale;
		this.link = link;
		this.birthday = birthday;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isVerifiedEmail() {
		return verifiedEmail;
	}
	public void setVerifiedEmail(boolean verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getqSportsUserKey() {
		return qSportsUserKey;
	}
	public void setqSportsUserKey(String qSportsUserKey) {
		this.qSportsUserKey = qSportsUserKey;
	}
	public String getqSportCricketProfileKey() {
		return qSportCricketProfileKey;
	}
	public void setqSportCricketProfileKey(String qSportCricketProfileKey) {
		this.qSportCricketProfileKey = qSportCricketProfileKey;
	}
	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}
	public void setUserTimeZone(TimeZone userTimeZone) {
		this.userTimeZone = userTimeZone;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	
	
}