package com.thinqq.qsports.shared.cricket;

public enum UserStatusEnum {
	FIRST_LOGIN_DONE(0,"F"), SETUP(1,"S");
	private Integer id;
	private String name;
	
	private UserStatusEnum(Integer id, String name) {
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
