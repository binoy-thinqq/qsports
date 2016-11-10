package com.thinqq.qsports.shared.cricket;

public enum HandTypeEnum {
	NOT_SPECIFIED(0,"Not Specified"),
	RIGHT_HANDED(1,"Right Hand"),
	LEFT_HANDED(2,"Left Hand");

	private Integer id;
	private String screenName;
	private HandTypeEnum(Integer id, String screenName){
		this.id = id;
		this.screenName = screenName;
	}
	public Integer getId() {
		return id;
	}
	public String getScreenName() {
		return screenName;
	}
	public  static HandTypeEnum getById(Integer id){
		for(HandTypeEnum hand : HandTypeEnum.values()){
			if(hand.getId().equals(id)){
				return hand;
			}
		}
		return NOT_SPECIFIED;
	}
}
