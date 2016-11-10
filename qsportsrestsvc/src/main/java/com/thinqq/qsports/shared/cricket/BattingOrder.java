package com.thinqq.qsports.shared.cricket;

public enum BattingOrder {
	NOT_SPECIFIED(0,"Not Specified"),
	OPENING(1, "Opening"),
	TOP_ORDER(2,"Top Order"),
	MIDDLE_ORDER(3,"Middle Order"),
	LOW_ORDER(4,"Low Order"),
	TAIL_END(5,"Tail End"),
	VERSATILE(6,"Any Position");
	private Integer id;
	private String screenName; 
	private BattingOrder(Integer id, String screenName){
		this.id = id;
		this.screenName = screenName;
	}
	public Integer getId() {
		return id;
	}
	public String getScreenName() {
		return screenName;
	}
	public static BattingOrder getById(Integer id){
		for(BattingOrder order : BattingOrder.values()){
			if(order.getId().equals(id)){
				return order;
			}
		}
		return NOT_SPECIFIED;
	}
	
}
