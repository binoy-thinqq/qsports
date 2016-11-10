package com.thinqq.qsports.shared.cricket;

public enum BowlingStyle {
	NOT_SPECIFIED(0,"Not Specified"),
	RF(1,"Fast"),
	RFM(2,"Fast medium"),
	RM(3,"Medium"),
	OB(4,"Off break"),
	LB(5,"Leg break"),
	LBG(6,"Leg break googly"),
	SLA(7,"Slow orthodox"),
	SLC(8,"Slow chinaman"),	
	OS(9,"Off spin"),
	LS(10,"Leg spin"),
	NONE(11,"None");
	private Integer id;
	private String screenName;
	private BowlingStyle(Integer id, String screenName){
		this.id = id;
		this.screenName = screenName;
	}
	public Integer getId() {
		return id;
	}
	public String getScreenName() {
		return screenName;
	}
	public static  BowlingStyle getById(Integer id){
		for(BowlingStyle style : BowlingStyle.values()){
			if(style.getId().equals(id)){
				return style;
			}
		}
		return NOT_SPECIFIED;
	}
}
