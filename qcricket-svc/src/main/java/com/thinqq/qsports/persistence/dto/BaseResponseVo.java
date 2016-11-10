package com.thinqq.qsports.persistence.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thinqq.qsports.server.error.ErrorInfo;

public class BaseResponseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5181604213819365588L;
	/**
	 * 
	 */
	List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
	
	public List<ErrorInfo> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}
	
	

}
	