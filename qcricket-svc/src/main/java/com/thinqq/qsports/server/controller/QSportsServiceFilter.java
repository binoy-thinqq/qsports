package com.thinqq.qsports.server.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinqq.qsports.persistence.dao.UserDAO;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.server.process.AuthenticateUserProcess;

public class QSportsServiceFilter implements Filter {

	String SECRET_KEY = null;

	private static Logger logger = Logger.getLogger(QSportsServiceFilter.class.getName());


	private AuthenticateUserProcess authenticateProcess = new AuthenticateUserProcess();
	
	UserDAO userDao = new UserDAO();
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean isValid = false;
		String key = ((HttpServletRequest) request).getHeader("SECRET_KEY");
		if (((HttpServletRequest) request).getPathInfo().contains("login") || ((HttpServletRequest) request).getPathInfo().contains("register")) {
			isValid = true;
		} else {
			String authKey = ((HttpServletRequest) request)
					.getHeader("AUTH_KEY");
			Cookie cookies[] = ((HttpServletRequest) request).getCookies();
			for (Cookie cookie : cookies) {
				if ("QSPORTS_AUTH_KEY".equals(cookie.getName())) {
					authKey = cookie.getValue();
					break;
				}
			}
			if (authKey == null) {
				((HttpServletResponse) response).sendError(
						HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
			try {
				
				UserVo user = authenticateProcess.checkAuthKeyValidity(authKey);
				isValid =  user != null;
				request.setAttribute("signedInUser", user);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Unable to find Auth entry", e);
				((HttpServletResponse) response).sendError(
						HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
		}
		if (key != null && key.equals(SECRET_KEY) && isValid) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendError(
					HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			return;
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		SECRET_KEY = config.getInitParameter("SECRET_KEY");
	}
	
	
}
