package com.thinqq.qsports.server.error;

import java.util.Map;

public class ErrorInfo {

	int errorCode;
	String errorDesc;
	Map<String, String> param;
	
	
	public ErrorInfo() {
		super();
	}
	
	public ErrorInfo(int errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}
	
}
