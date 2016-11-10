package com.thinqq.qsports.event.DO;

import org.springframework.util.StringUtils;

public enum MatchStatusEnum {
	
	PLANNED("planned"),COMPLETED("completed");
	private String name;
	private MatchStatusEnum(String name) {
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public MatchStatusEnum getEnum(String name){
		if(!StringUtils.hasText(name)){
			return null;
		}
		for(MatchStatusEnum enumValue : MatchStatusEnum.values()){
			if(enumValue.getName().equals(name)){
				return enumValue;
			}
		}
		return null;
	}

}
