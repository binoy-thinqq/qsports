package com.thinqq.qsports.server.process.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneUtil {
public static Date changeUTCTimeTo(Date time, String timeZoneId){
	int offset = TimeZone.getTimeZone(timeZoneId).getOffset(time.getTime());
	long timeInMilliSeconds = time.getTime()+offset;
	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
	//System.out.println(calendar.get));
	calendar.setTimeInMillis(timeInMilliSeconds);
	System.out.println(calendar.getTime());
	calendar.setTimeZone(TimeZone.getTimeZone(timeZoneId));
	System.out.println(calendar.getTime());
	return calendar.getTime();
}
public static void main(String args[]){
	Date time = Calendar.getInstance().getTime();
	System.out.println(time);
	changeUTCTimeTo(time, "America/Chicago");
}
}
