package com.thinqq.qsports.shared.teamprofile;

import java.util.Date;
import java.util.List;

import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.QSportsResponseVo;

public class TeamProfileResponseVo extends QSportsResponseVo {
	private static final long serialVersionUID = 1995783652940409178L;
	private Boolean teamNotFound;
	private String name;
	private String description;
	private String city;
	private String state;
	private String country;
	private Date dateOfEstd;
	private String dateOfEstdString;
	
	private List<NameAndKey> owners;
	private Boolean allowEdit;
	private String teamKey;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Date getDateOfEstd() {
		return dateOfEstd;
	}
	public void setDateOfEstd(Date dateOfEstd) {
		this.dateOfEstd = dateOfEstd;
	}
	public Boolean getTeamNotFound() {
		return teamNotFound;
	}
	public void setTeamNotFound(Boolean teamNotFound) {
		this.teamNotFound = teamNotFound;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<NameAndKey> getOwners() {
		return owners;
	}
	public void setOwners(List<NameAndKey> owners) {
		this.owners = owners;
	}
	public Boolean isAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(Boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public String getDateOfEstdString() {
		return dateOfEstdString;
	}
	public void setDateOfEstdString(String dateOfEstdString) {
		this.dateOfEstdString = dateOfEstdString;
	}
	public String getTeamKey() {
		return teamKey;
	}
	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
}
