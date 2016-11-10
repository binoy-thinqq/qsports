package com.thinqq.qsports.server.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.shared.userprofile.UserInfoRequestVo;
import com.thinqq.qsports.shared.userprofile.UserInfoResponseVo;

public class QSportsServiceFilter implements Filter {

	String SECRET_KEY = null;
	static Map<String, UserInfoResponseVo> userCacheMap = new HashMap<String, UserInfoResponseVo>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean isValid = false;
		String key = ((HttpServletRequest) request).getHeader("SECRET_KEY");
		if (((HttpServletRequest) request).getPathInfo().contains("login")) {
			isValid = true;
		} else {
			String authKey = ((HttpServletRequest) request)
					.getHeader("AUTH_KEY");
			if (authKey == null) {
				((HttpServletResponse) response).sendError(
						HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
			try {
				byte[] authKeyByte = Base64.decodeBase64(authKey.getBytes());
				String userKey = new String(authKeyByte, "UTF-8");
				UserInfoResponseVo responseUser = userCacheMap.get(userKey);
				if (responseUser == null) {
					responseUser = new UserInfoResponseVo();
					UserProfileProcess process = UserProfileProcess
							.getInstance();
					UserInfoRequestVo requestUser = new UserInfoRequestVo();
					requestUser.setKey(userKey);
					process.getUserProfile(requestUser, responseUser);
				}
				if (responseUser.getEmail() != null) {
					isValid = true;
					userCacheMap.put(userKey, responseUser);
					request.setAttribute("signedInUser", responseUser);
				}
			} catch (Exception e) {
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
