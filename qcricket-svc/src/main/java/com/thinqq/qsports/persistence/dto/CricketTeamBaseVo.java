package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;
import com.thinqq.qsports.persistence.validation.annotation.Size;

public class CricketTeamBaseVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1755395953039266439L;

	private Integer teamId;

	@NotNull
	private String teamName;

	@Size(length=100)
	private String city;

	@Size(length=100)
	private String state;

	@Size(length=100)
	private String country;

	@Size(length=400)
	private String description;

	private Date createdTime;

	private Integer createdId;

	private Integer updatedId;

	private Date updatedTime;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Integer createdId) {
		this.createdId = createdId;
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
	
}
