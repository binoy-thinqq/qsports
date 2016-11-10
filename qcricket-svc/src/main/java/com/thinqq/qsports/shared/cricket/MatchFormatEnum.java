package com.thinqq.qsports.shared.cricket;

public enum MatchFormatEnum {
	T20(1,"T20 (5 - 20 Overs)", "T20", 20, 2), OD(2,"One Day (21 to 50 Overs)", "OD", 50, 2), TEST(3, "TEST (Unlimited Overs)", "TEST", 0, 4);

private Integer id;
private String screenName;
private String name;
private Integer maxOvers;
private Integer maxInns;

private MatchFormatEnum(Integer id, String screenName, String name, Integer maxOvers, Integer maxInns){
	this.id = id;
	this.screenName = screenName;
	this.name = name;
	this.maxOvers = maxOvers;
	this.maxInns = maxInns;
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

public Integer getMaxOvers() {
	return maxOvers;
}

public Integer getMaxInns() {
	return maxInns;
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
