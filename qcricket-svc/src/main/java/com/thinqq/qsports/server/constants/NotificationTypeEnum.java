package com.thinqq.qsports.server.constants;

public enum NotificationTypeEnum {
	JOIN_TEAM_REQUEST("JOIN_TEAM_REQUEST"), ADD_TO_TEAM_REQUEST("ADD_TO_TEAM_REQUEST");
	
	String typeName;
	NotificationTypeEnum(String typeName) {
		this.typeName = typeName;
	}
	
	public String getType() {
		return typeName;
	}

	public NotificationTypeEnum getEnumValue(String type) {
		for (NotificationTypeEnum enumType : NotificationTypeEnum.values()) {
			if (enumType.getType().equals(type)) {
				return enumType;
			}
		}
		return null;
	}
}
