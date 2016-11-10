package com.thinqq.qsports.shared;


public enum OutTypeEnum {
	BOWLED("Bowled",1,'B'),
	TIMED_OUT("Timed out",2,'T'),
	CAUGHT("Caught",3,'C'),
	HANDLED_THE_BALL("Handling the ball",4,'H'),
	HIT_THE_BALL_TWICE("Hit the ball twice",5,'I'),
	HIT_WICKET("Hit Wicket",6,'K'),
	LEG_BEFORE_WICKET("Leg before wicket",7,'L'),
	OBSTRUCTING_THE_FIELD("Obstructucting the field",8,'O'),
	RUN_OUT("Run out",9,'R'),
	STUMPTED("Stumped",10,'S'),
	RETIRED_HURT("Retired Hurt",11,'S');
	
	private String name;
	private int outTypeId;
	private char charRepresentation;
	OutTypeEnum(String name,int outTypeId, char charRepresentation){
		this.name = name;
		this.outTypeId = outTypeId;
		this.charRepresentation = charRepresentation;
	}
	
	public String getOutTypeName(){
		return name;
	}

	public int getOutTypeId(){
		return outTypeId;
	}
	
	public static int getOutTypeIdWithName(String name){
		for(OutTypeEnum enumValue : values()){
			if(enumValue.getOutTypeName().equals(name))
			{
				return enumValue.getOutTypeId();
			}
		}
		return -1;
	}

	public char getCharRepresentation() {
		return charRepresentation;
	}
}
