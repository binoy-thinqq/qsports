package com.thinqq.qsports.shared;

import java.util.HashMap;
import java.util.Map;

import com.thinqq.qsports.shared.cricket.MatchFormatEnum;

public final class Constants {

	public static final String registerendPoint = "/proxy/ws/userservice/register";
	
	public static final String matchCreateendpoint = "/proxy/ws//match/create";
	public static final String SUCCESS = "success";
	public static final String REG_SUCCESS_URL = "regsuccess";
	public static final String EMAIL="email";
	public static final String CONFIRMATION_KEY = "confirmationKey";
	public static Map<MatchFormatEnum, Integer> formatInningsMap = new HashMap<MatchFormatEnum, Integer>();
	static{
		formatInningsMap.put(MatchFormatEnum.OD, 2);
		formatInningsMap.put(MatchFormatEnum.T20, 2);
		formatInningsMap.put(MatchFormatEnum.TEST, 4);
	}
	public static String NAME_PREFIX="name:";
	
}
