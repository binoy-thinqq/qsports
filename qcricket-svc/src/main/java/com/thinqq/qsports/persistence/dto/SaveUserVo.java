package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import com.thinqq.qsports.persistence.validation.annotation.ExpectedValue;
import com.thinqq.qsports.persistence.validation.annotation.NotNull;
import com.thinqq.qsports.persistence.validation.annotation.Size;


public class SaveUserVo extends BaseVo {

private static final long serialVersionUID = -4136875541979304518L;

@NotNull
@Size(length=50)
private String firstName;

@Size(length=50)
String lastName;

@Size(length=1)
@ExpectedValue(values={"M", "F", "N"})
String gender;

@Size(length=150)
String city;

@Size(length=150)
String state;

@Size(length=100)
String isoCountryCode;

@Size(length=100)
String timezone;

String locale;

Date dob;

@Size(length=400)
String notes;

@Size(length=15)
String mobileNumber;

@Size(length=1)
@ExpectedValue(values={"A", "T", "N"})
String contactShown;


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

public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
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

}
