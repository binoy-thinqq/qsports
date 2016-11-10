package com.thinqq.qsports.shared.cricket;

public enum MatchFormatEnum {
	T20(1,"T20 (5 - 20 Overs)", "T20"), OD(2,"One Day (21 to 50 Overs)", "OD"), TEST(3, "TEST (Unlimited Overs)", "TEST");

private Integer id;
private String screenName;
private String name;
private MatchFormatEnum(Integer id, String screenName, String name){
	this.id = id;
	this.screenName = screenName;
	this.name = name;
}
public Integer getId() {
	return id;
}
public String getScreenName() {
	return screenName;
}
public String getName() {
	return name;
}
public static MatchFormatEnum getEnum(String name) {
		if (name != null && name.trim() != "") {
			for (MatchFormatEnum enumValue : MatchFormatEnum.values()) {
				if (enumValue.getName().equals(name)) {
					return enumValue;
				}
			}
		}
		return null;
	}
public static MatchFormatEnum getEnumById(int id) {
		for (MatchFormatEnum enumValue : MatchFormatEnum.values()) {
			if (enumValue.getId().equals(id)) {
				return enumValue;
			}
		}
	return null;
}

}
