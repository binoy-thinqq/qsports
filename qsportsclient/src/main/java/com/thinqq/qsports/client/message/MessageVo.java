package com.thinqq.qsports.client.message;

public class MessageVo implements Comparable<MessageVo>{
 private Integer messageId;
 private MessageTypeEnum messageType;
 public MessageVo(Integer messageId, MessageTypeEnum messageType,
		String message, String anchor, String historyToken) {
	super();
	this.messageId = messageId;
	this.messageType = messageType;
	this.message = message;
	this.anchor = anchor;
	this.historyToken = historyToken;
}
private String message;
 private String anchor;
 private String historyToken;
 
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getAnchor() {
	return anchor;
}
public void setAnchor(String anchor) {
	this.anchor = anchor;
}
public String getHistoryToken() {
	return historyToken;
}
public void setHistoryToken(String historyToken) {
	this.historyToken = historyToken;
}
public Integer getMessageId() {
	return messageId;
}
public void setMessageId(Integer messageId) {
	this.messageId = messageId;
}
public MessageTypeEnum getMessageType() {
	return messageType;
}
public void setMessageType(MessageTypeEnum messageType) {
	this.messageType = messageType;
}
@Override
public int compareTo(MessageVo o) {
	if(this.messageId!=null){
		if(o.getMessageId()!=null){
			return -(o.getMessageId().compareTo(this.messageId));
		}
	}
	return 0;
}
}
