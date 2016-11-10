package com.thinqq.qsports.shared.cricket;

public enum FieldingPosition {
	NOT_SPECIFIED(0,"Not Specified"),
	THIRD_MAN(1,"Third man"),
	SLIPS(2,"Slips"),
	GULLY(3,"Gully"),
	POINT(4,"Point"),
	FINE_LEG(5,"Fine leg"),
	SQUARE_LEG(6,"Square leg"),
	COVER(7,"Cover"),
	MID_WICKET(8,"Mid wicket"),
	MID_OFF(9,"Mid Off"),
	MID_ON(10,"Mid On"),
	LONG_OFF(11,"Long Off"),
	LONG_ON(12,"Long On"),
	ANY_POSITION(13,"Any Position");
	
	private Integer id;
	private String screenName;
	private FieldingPosition(Integer id, String screenName){
		this.id = id;
		this.screenName = screenName;
	}
	public Integer getId() {
		return id;
	}
	public String getScreenName() {
		return screenName;
	}
	public static FieldingPosition getById(Integer id){
		for(FieldingPosition pos : FieldingPosition.values()){
			if(pos.getId().equals(id)){
				return pos;
			}
		}
		return NOT_SPECIFIED;
	}
}
