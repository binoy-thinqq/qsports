package com.thinqq.qsports.server.error.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.InvalidRequestArgumentException;

@EnableWebMvc
@ControllerAdvice
public class QsportsExceptionHandler extends ResponseStatusExceptionResolver {

	public QsportsExceptionHandler() {
		// Turn logging on by default
		setWarnLogCategory(getClass().getName());

		// Make sure this handler runs before the default
		// ExceptionHandlerExceptionResolver
		setOrder(LOWEST_PRECEDENCE - 1);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidRequestArgumentException.class)
	@ResponseBody ErrorInfo handleConflict(InvalidRequestArgumentException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setErrorCode(ErrorConstants.INVALID_REQUEST);
		if (ex.getMessage() != null) {
			info.setErrorDesc(ex.getMessage());
		} else {
			info.setErrorDesc("Invalid Request parameters or parameters passed is not sufficient." +
					"Please check the service contract!!");
		}
		
		if (ex.getInvalidParams() != null) {
			info.setParam(ex.getInvalidParams());
		}
		return info;
	}

}
