package com.thinqq.qsports.persistence.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.thinqq.qsports.server.error.ErrorInfo;

public class BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7690876981648399372L;
	List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
	@JsonIgnore
	Integer signedInUserId;
	
	public List<ErrorInfo> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}

	public Integer getSignedInUserId() {
		return signedInUserId;
	}

	public void setSignedInUserId(Integer signedInUserId) {
		this.signedInUserId = signedInUserId;
	}
	
	

}
