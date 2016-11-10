package com.thinqq.qsports.server.oauth;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class GoogleUserProfile extends GenericJson{
	@Key
	private String id;
	@Key
	private String email;
	@Key("verified_email")
	private boolean verifiedEmail;
	@Key
	private String name;
	@Key("given_name")
	private String givenName;
	@Key("family_name")
	private String familyName;
	@Key
	private String picture;
	@Key
	private String gender;
	@Key
	private String locale;
	@Key
	private String link;
	@Key
	private String birthday;
	
	public GoogleUserProfile(){}
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
	
	
}