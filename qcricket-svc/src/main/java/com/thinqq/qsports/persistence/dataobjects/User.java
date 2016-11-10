package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;


@Entity
@Table(name="cricket.user")
public class User implements DataObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2895528366475480734L;

	@Id
	@Column(name = "user_id")
	@SequenceGenerator(name = "user_seq", sequenceName = "cricket.user_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
	Integer userId;
	
	@Column(name = "first_name")
	@NotNull
	private String firstName;
	
	@Column(name = "last_name")
	String lastName;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "dob")
	Date dob;
	
	@Column(name = "gender")
	String gender;
	
	@Column(name = "city")
	String city;
	
	@Column(name = "state")
	String state;
	
	@Column(name = "iso_country_code")
	String isoCountryCode;
	
	@Column(name = "timezone")
	String timezone;
	
	@Column(name = "locale")
	String locale;
	
	@Column(name = "status")
	String status;
	
	@Column(name = "registration_type")
	String registrationType;
	
	@Column(name = "picture_url")
	String pictureUrl;
	
	@Column(name = "confirmation_code")
	String confirmationCode;
	
	@Column(name = "created_id")
	Integer createdId;
	
	@Column(name = "created_time")
	Date createdTime;
	
	@Column(name = "updated_id")
	Integer updatedId;
	
	@Column(name = "updated_time")
	Date updatedTime;
	
	@Column(name = "notes")
	String notes;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "mobile_number")
	String mobileNumber;

	@Column(name = "is_contact_shown")
	String contactShown;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	Set<CricketProfile> cricketProfiles;	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	Set<AuthKey> authKeys;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getContactShown() {
		return contactShown;
	}

	public void setContactShown(String contactShown) {
		this.contactShown = contactShown;
	}
	public Set<CricketProfile> getCricketProfiles() {
		return cricketProfiles;
	}

	public void setCricketProfiles(Set<CricketProfile> cricketProfiles) {
		this.cricketProfiles = cricketProfiles;
	}

	public Set<AuthKey> getAuthKeys() {
		return authKeys;
	}

	public void setAuthKeys(Set<AuthKey> authKeys) {
		this.authKeys = authKeys;
	}

}
