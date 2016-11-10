package com.thinqq.qsports.shared;


public class QSportsRequestVo extends QsportsVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7247150654973082933L;
	
	private String serverName;
	private String returnToken;
	private String timeZone;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getReturnToken() {
		return returnToken;
	}
	public void setReturnToken(String returnToken) {
		this.returnToken = returnToken;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
