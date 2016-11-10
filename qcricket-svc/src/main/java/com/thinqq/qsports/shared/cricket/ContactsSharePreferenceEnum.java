package com.thinqq.qsports.shared.cricket;

public enum ContactsSharePreferenceEnum {
	SHARED_TO_ALL_USERS(0,"A"), 
	SHARED_TO_TEAM_MATES(1,"T"),
	SHARED_TO_NONE(2,"N");
	private Integer id;
	private String name;
	
	private ContactsSharePreferenceEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
