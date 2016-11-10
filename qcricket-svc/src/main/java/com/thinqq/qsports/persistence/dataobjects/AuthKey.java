package com.thinqq.qsports.persistence.dataobjects;

import java.util.Date;

import javax.jdo.annotations.Unique;
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

import com.thinqq.qsports.persistence.validation.annotation.NotNull;


@Entity
@Table(name="cricket.auth_key")
public class AuthKey implements DataObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3243577669789759243L;

	@Id
	@Column(name = "auth_key_id")
	@SequenceGenerator(name = "auth_key_seq", sequenceName = "cricket.auth_key_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="auth_key_seq")
	Integer authKeyId;
	
	@Column(name = "key")
	@NotNull
	@Unique
	String key;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	User user;
	
	@Column(name = "expires_on")
	@NotNull
	Date expiresOn;

	public Integer getAuthKeyId() {
		return authKeyId;
	}

	public void setAuthKeyId(Integer authKeyId) {
		this.authKeyId = authKeyId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}
	
	
	
}
