package com.thinqq.qsports.shared;

public class IdUtil {
	public static boolean isJustName(String content){
		return content.startsWith(Constants.NAME_PREFIX);
	}
	
	public static String addNamePrefix(String name){
		return Constants.NAME_PREFIX+name;
	}
	public static String removeNamePrefix(String name){
		return name.replaceFirst(Constants.NAME_PREFIX, "");
	}
}
