package com.thinqq.qsports.shared.cricket;

public enum RegistrationTypeEnum {
	
	GOOGLE("GOOGLE"), FACEBOOK("FACEBOOK"), INTERNAL("INTERNAL");
	private String type;
	
	
	private RegistrationTypeEnum(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return type;
	}
	
}
