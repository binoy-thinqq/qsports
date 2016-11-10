package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.thinqq.qsports.persistence.validation.annotation.ExpectedValue;
import com.thinqq.qsports.persistence.validation.annotation.NotNull;
import com.thinqq.qsports.persistence.validation.annotation.Size;
import com.thinqq.qsports.persistence.validation.annotation.ValidateEmail;

@XmlRootElement
public class UserVo extends BaseVo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7094207993558725711L;

	private Integer userId;
	
	private Integer profileId;
	
	@Size(length=50)
	private String firstName;
	
	@Size(length=50)
	private String lastName;
	
	@NotNull
	@ValidateEmail
	@Size(length=100)
	private String email;
	
	private String confirmationCode;
	
	@Size(length=1)
	@ExpectedValue(values={"M", "F", "N"})
	private String gender;
	
	@Size(length=150)
	private String city;
	
	@Size(length=150)
	private String state;
	
	@Size(length=100)
	private String isoCountryCode;
	
	@Size(length=100)
	private String timezone;
	
	private String locale;
	
	private String status;
	
	@NotNull
	@Size(length=25)
	@ExpectedValue(values={"GOOGLE", "FACEBOOK", "INTERNAL"})
	private String registrationType;
	
	private String pictureUrl;
	
	private Integer createdId;

	private Date dob;
	
	private Date createdTime;

	private Integer updatedId;
	
	private Date updatedTime;
	
	@Size(length=400)
	private String notes;
	
	@Size(length=50)
	private String password;
	
	@Size(length=15)
	private String mobileNumber;
	
	private boolean contactShown;
	
	private boolean isEditable;

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

	public boolean getContactShown() {
		return contactShown;
	}

	public void setContactShown(boolean contactShown) {
		this.contactShown = contactShown;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

}
