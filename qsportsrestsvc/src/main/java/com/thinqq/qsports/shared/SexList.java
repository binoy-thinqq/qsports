package com.thinqq.qsports.shared;

import java.util.Map;
import java.util.TreeMap;


public class SexList {
	private static Map<String,String> listSex = new TreeMap<String,String>();
	public static Map<String,String> getListSex(){
		return listSex;
	}
	static {
		listSex.put("Male","M");
		listSex.put("Female","F");
		listSex.put("Prefer Not to Say","N");
	}
}