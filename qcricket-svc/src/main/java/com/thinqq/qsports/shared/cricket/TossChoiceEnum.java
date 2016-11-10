package com.thinqq.qsports.shared.cricket;

public enum TossChoiceEnum {
	BAT_FIRST(1, "Bat First"),
	BOWL_FIRST(2, "Bowl First");
	
	private final int id;
	private final String screenName;
	
	private TossChoiceEnum(int id, String screenName) {
		this.id = id;
		this.screenName = screenName;
	}
	public int getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}
}
