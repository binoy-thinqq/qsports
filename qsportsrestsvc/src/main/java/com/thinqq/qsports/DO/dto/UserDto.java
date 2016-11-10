package com.thinqq.qsports.DO.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.thinqq.qsports.DO.User;

@XmlRootElement
public class UserDto {

	@XmlElement(name="email")
	public String mail;
	
	@XmlElement(name="location")
	public String addr;
	
	@XmlTransient
	public List<String> teamIdList;
	
	public UserDto(){}

	//Dto object specific to a user. Aggregates user info, profile info, user's teamId's  etc
	public UserDto(User user){
		this.mail = user.getEmail();
		
		//TODO: just to show the use of dto's....replace this code with user profile info OR data required for UI
		this.addr = "test address";
		this.teamIdList = new ArrayList<String>();
		teamIdList.add("Fear the eleven");
		teamIdList.add("Team-1");
		teamIdList.add("Team-2");
	}
	
	public void setEmail(String email) {
		this.mail = email;
	}

	public String getEmail() {
		return mail;
	}

	public void setAddress(String address) {
		this.addr = address;
	}

	@XmlTransient
	public String getAddress() {
		return addr;
	}

	public void setTeamId(List<String> teamId) {
		this.teamIdList = teamId;
	}

	@XmlElement(name="team")
	public List<String> getTeamId() {
		return teamIdList;
	}
	
	

}
