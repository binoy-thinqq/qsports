package com.thinqq.qsports.server;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ServerGenie {
	public static TimeZone getTimeZone(String timeZoneId){
		TimeZone timeZone = null;
		if(timeZoneId != null ){
			timeZone = TimeZone.getTimeZone(timeZoneId);
		}
		if(timeZone == null){
			timeZone = TimeZone.getDefault();
		}
		return timeZone;
	}
	
	public static Date convertDateToUTC(Date date, TimeZone timeZone){
		Calendar calendar= Calendar.getInstance();
		int offset = timeZone.getOffset(Calendar.getInstance().getTimeInMillis());
		calendar.setTimeInMillis(date.getTime()+offset);
		return calendar.getTime();
	}
	public static Date convertDateFromUTC(Date date, TimeZone timeZone){
		Calendar calendar= Calendar.getInstance();
		int offset = timeZone.getOffset(Calendar.getInstance().getTimeInMillis());
		calendar.setTimeInMillis(date.getTime()-offset);
		return calendar.getTime();
	}
}
