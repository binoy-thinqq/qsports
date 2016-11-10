package com.thinqq.qsports.shared;

import java.util.List;

import com.thinqq.qsports.server.message.MessageVo;
import com.thinqq.qsports.shared.validation.ErrorVo;

public class QSportsResponseVo extends QsportsVo {

	private static final long serialVersionUID = 1893304210296995992L;

	private List<ErrorVo> errors;
	private List<MessageVo> messages;
	private String returnToken;
	private Boolean noUserSignedIn;

	public Boolean isNoUserSignedIn() {
		return noUserSignedIn;
	}

	public void setNoUserSignedIn(Boolean noUserSignedIn) {
		this.noUserSignedIn = noUserSignedIn;
	}

	public List<ErrorVo> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorVo> errors) {
		this.errors = errors;
	}

	public List<MessageVo> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageVo> messages) {
		this.messages = messages;
	}

	public String getReturnToken() {
		return returnToken;
	}

	public void setReturnToken(String returnToken) {
		this.returnToken = returnToken;
	}
}
