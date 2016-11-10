package com.thinqq.qsports.shared;


public enum MatchFormat {
TEN_OVERS(1,"10 Overs"),
TWENTY_OVERS(2,"20 Overs"),
THIRTY_OVERS(3,"30 Overs"),
FORTY_OVERS(4,"40 Overs"),
FIFTY_OVERS(5,"50 Overs"),
THREE_DAY_TEST(6,"Three day test"),
FIVE_DAY_TEST(7,"Five day test");

private Integer id;
private String screenName;
private MatchFormat(Integer id, String screenName){
	this.id = id;
	this.screenName = screenName;
}
public Integer getId() {
	return id;
}
public String getScreenName() {
	return screenName;
}
public static  MatchFormat getById(Integer id){
	for(MatchFormat style : MatchFormat.values()){
		if(style.getId().equals(id)){
			return style;
		}
	}
	return TEN_OVERS;
}
}
