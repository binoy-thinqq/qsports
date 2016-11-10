package com.thinqq.qsports.shared.cricket;

public enum TeamPlayerStatusEnum {

	ACCEPTED(1, "ACCEPTED"), REQ_BY_TEAM(2, " REQ_BY_TEAM"), REQ_BY_PLAYER(3,"  REQ_BY_PLAYER"),
	REJECTED_BY_TEAM(4, " REJECTED_BY_TEAM"), REJECTED_BY_PLAYER(5, "  REJECTED_BY_PLAYER"),
	BLOCKED_BY_TEAM(6, " BLOCKED_BY_TEAM"), BLOCKED_BY_PLAYER(7, " BLOCKED_BY_PLAYER");

	private Integer id;
	private String name;

	private TeamPlayerStatusEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static TeamPlayerStatusEnum getEnum(String name) {
		if (name != null && name.trim() != "") {
			for (TeamPlayerStatusEnum enumValue : TeamPlayerStatusEnum.values()) {
				if (enumValue.getName().equals(name)) {
					return enumValue;
				}
			}
		}
		return null;
	}

	public static TeamPlayerStatusEnum getEnumById(int id) {
		for (TeamPlayerStatusEnum enumValue : TeamPlayerStatusEnum.values()) {
			if (enumValue.getId().equals(id)) {
				return enumValue;
			}
		}
		return null;
	}
}