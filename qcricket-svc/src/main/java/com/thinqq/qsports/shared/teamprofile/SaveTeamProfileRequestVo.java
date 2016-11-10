package com.thinqq.qsports.shared.teamprofile;

import java.util.Date;

import com.thinqq.qsports.shared.QSportsRequestVo;

public class SaveTeamProfileRequestVo extends QSportsRequestVo {
	private static final long serialVersionUID = -4750233805072744475L;
	private Boolean newTeam;
	private String name;
	private String description;
	private String city;
	private String state;
	private String country;
	private String teamKey;
	private Date dateOfEstd;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Boolean getNewTeam() {
		return newTeam;
	}
	public void setNewTeam(Boolean newTeam) {
		this.newTeam = newTeam;
	}
	public String getTeamKey() {
		return teamKey;
	}
	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}
	
}
