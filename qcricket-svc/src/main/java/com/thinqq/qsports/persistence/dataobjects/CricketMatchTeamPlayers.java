package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cricket.cricket_match_team_players")
public class CricketMatchTeamPlayers implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081101902400968227L;

	@Id
	@Column(name = "match_team_player_id")
	@SequenceGenerator(name = "cricket_match_team_players_seq", sequenceName = "cricket.cricket_match_team_players_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cricket_match_team_players_seq")
	Integer matchTeamPlayerId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	CricketMatch match;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	CricketTeam team;
	
	@Column(name = "team_name")
	String teamName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", referencedColumnName="cricket_profile_id")
	CricketProfile profile;
	
	@Column(name = "profile_name")
	String profileName;
	
	@Column(name = "sub")
	Boolean sub;
	
	@Column(name = "created_id")
	Integer createdId;

	@Column(name = "created_time")
	Date createdTime;

	@Column(name = "updated_id")
	Integer updatedId;

	@Column(name = "updated_time")
	Date updatedTime;

	public Integer getMatchTeamPlayerId() {
		return matchTeamPlayerId;
	}

	public void setMatchTeamPlayerId(Integer matchTeamPlayerId) {
		this.matchTeamPlayerId = matchTeamPlayerId;
	}

	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Boolean getSub() {
		return sub;
	}

	public void setSub(Boolean sub) {
		this.sub = sub;
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

	public CricketMatch getMatch() {
		return match;
	}

	public void setMatch(CricketMatch match) {
		this.match = match;
	}

	public CricketTeam getTeam() {
		return team;
	}

	public void setTeam(CricketTeam team) {
		this.team = team;
	}

	public CricketProfile getProfile() {
		return profile;
	}

	public void setProfile(CricketProfile profile) {
		this.profile = profile;
	}
	
	
	
}
