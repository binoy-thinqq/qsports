package com.thinqq.qsports.persistence.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.thinqq.qsports.persistence.validation.annotation.NotNull;
import com.thinqq.qsports.persistence.validation.annotation.Size;
import com.thinqq.qsports.persistence.validation.annotation.ValidateEmail;

@XmlRootElement
public class UserRegisterVo extends BaseVo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7094207993558725711L;

	@NotNull
	@Size(length=50)
	private String firstName;
	
	@NotNull
	@Size(length=50)
	private String lastName;
	
	@NotNull
	@ValidateEmail
	@Size(length=100)
	private String email;
	
	private Date createdTime;

	private Integer updatedId;
	
	private Date updatedTime;
	
	@NotNull
	@Size(length=50)
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
