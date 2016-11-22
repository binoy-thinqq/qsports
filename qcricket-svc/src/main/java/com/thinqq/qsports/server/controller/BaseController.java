package com.thinqq.qsports.server.controller;

import javax.servlet.http.HttpServletRequest;

import com.thinqq.qsports.persistence.dto.BaseVo;
import com.thinqq.qsports.persistence.dto.UserVo;

public abstract class BaseController {
	
	
	/**
	 * Set Signed user
	 * @param request
	 * @param httpRequest
	 */
	protected void setSignedInUser(BaseVo request, HttpServletRequest httpRequest) {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		request.setSignedInUserId(signedInUser.getUserId());
	}
	
	protected UserVo getSignedInUser(HttpServletRequest httpRequest) {
		return (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
	}
	private static final String SIGNED_IN_USER = "signedInUser";

}
