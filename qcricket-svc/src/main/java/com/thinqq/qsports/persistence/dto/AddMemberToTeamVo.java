package com.thinqq.qsports.persistence.dto;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;

public class AddMemberToTeamVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7424319916989934983L;
	
	@NotNull
	private Integer teamId;
	
	private Integer profileId;
	
	private String email;
	
	private boolean isRejected;
	
	private boolean isBlocked;
	
	private String fullName;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailId) {
		this.email = emailId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
	

}
