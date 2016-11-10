package com.thinqq.qsports.persistence.dto;

import java.util.Map;

public class NotificationVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String notificationType;
	private Map<String, Object> notificationData;
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public Map<String, Object> getNotificationData() {
		return notificationData;
	}
	public void setNotificationData(Map<String, Object> notificationData) {
		this.notificationData = notificationData;
	}
	
	

}
