package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.cricket_team")
public class CricketTeam implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9176202731875213863L;

	@Id
	@Column(name = "team_id")
	@SequenceGenerator(name = "cricket_team_seq", sequenceName = "cricket.cricket_team_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_team_seq")
	private Integer teamId;

	@Column(name = "team_name")
	private String teamName;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "description")
	private String description;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "created_id")
	private Integer createdId;

	@Column(name = "updated_id")
	private Integer updatedId;

	@Column(name = "updated_time")
	private Date updatedTime;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cricketTeam", cascade=CascadeType.ALL)
	private List<CricketTeamPlayers> teamPlayers;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="cricketTeam", cascade=CascadeType.ALL)
	Set<CricketTeamStats> teamStats;

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

	public List<CricketTeamPlayers> getTeamPlayers() {
		return teamPlayers;
	}

	public void setTeamPlayers(List<CricketTeamPlayers> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public Set<CricketTeamStats> getTeamStats() {
		return teamStats;
	}

	public void setTeamStats(Set<CricketTeamStats> teamStats) {
		this.teamStats = teamStats;
	}
	
}
