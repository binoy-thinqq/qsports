package com.thinqq.qsports.shared;

public enum MatchStatus {
	
	PLANNED("Planned",1),
	STARTED("Started",2),
	COMPLETED("Completed",3),
	APPROVED("Approved",4),
	REJECTED("Rejected",5),
	ABANDONED("Abandoned",6);
	
	String name;
	int id;
	private MatchStatus(String name,int id) {
		this.name=name;
		this.id=id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
}
