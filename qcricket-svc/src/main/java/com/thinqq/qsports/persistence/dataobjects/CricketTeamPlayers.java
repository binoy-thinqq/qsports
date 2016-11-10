package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cricket.cricket_team_players")
public class CricketTeamPlayers implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cricket_team_players_id")
	@SequenceGenerator(name = "cricket_team_players_seq", sequenceName = "cricket.cricket_team_players_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_team_players_seq")
	private Integer teamPlayersId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id", nullable = false)
	CricketTeam cricketTeam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cricket_profile_id", nullable = false)
	CricketProfile cricketProfile;
	
	@Column(name = "is_moderator")
	private Boolean isModerator;
	
	@Column(name = "created_id")
	private Integer  createdId;
	
	@Column(name = "created_time")
	private Date  createdTime;
	
	@Column(name = "updated_id")
	private Integer  updatedId;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	@Column(name = "is_active")
	private Boolean  isActive;
	
	@Column(name = "status")
	private String  status;

	public Integer getTeamPlayersId() {
		return teamPlayersId;
	}

	public void setTeamPlayersId(Integer teamPlayersId) {
		this.teamPlayersId = teamPlayersId;
	}

	public CricketTeam getCricketTeam() {
		return cricketTeam;
	}

	public void setCricketTeam(CricketTeam cricketTeam) {
		this.cricketTeam = cricketTeam;
	}

	public CricketProfile getCricketProfile() {
		return cricketProfile;
	}

	public void setCricketProfile(CricketProfile cricketProfile) {
		this.cricketProfile = cricketProfile;
	}

	public Boolean getIsModerator() {
		return isModerator;
	}

	public void setIsModerator(Boolean isModerator) {
		this.isModerator = isModerator;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
