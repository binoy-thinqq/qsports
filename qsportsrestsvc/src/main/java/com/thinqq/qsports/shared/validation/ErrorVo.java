package com.thinqq.qsports.shared.validation;

import java.io.Serializable;

public class ErrorVo implements Serializable,Comparable<ErrorVo> {
/**
	 * 
	 */
	private static final long serialVersionUID = -4115789302680490444L;
private Integer errorId;
private String errorMessage;
private String fieldName;
private String anchorName;

public ErrorVo(){}

public ErrorVo(Integer errorId, String errorMessage, String fieldName,
		String anchorName) {
	super();
	this.errorId = errorId;
	this.errorMessage = errorMessage;
	this.fieldName = fieldName;
	this.anchorName = anchorName;
}
public Integer getErrorId() {
	return errorId;
}
public void setErrorId(Integer errorId) {
	this.errorId = errorId;
}
public String getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}
public String getFieldName() {
	return fieldName;
}
public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
}
public String getAnchorName() {
	return anchorName;
}
public void setAnchorName(String anchorName) {
	this.anchorName = anchorName;
}
@Override
public int compareTo(ErrorVo o) {
	if(this.errorId!=null){
		if(o.getErrorId()!=null){
			return -(o.getErrorId().compareTo(this.errorId));
		}
	}
	return 0;
}
}
