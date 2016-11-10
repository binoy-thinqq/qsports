package com.thinqq.qsports.server.oauth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thinqq.qsports.server.constants.ServerConstants;

/**
 * Servlet Filter implementation class OAuthFilter
 */
public class OAuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public OAuthFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		UserInfo user = (UserInfo)session.getAttribute(ServerConstants.SESSION_USER_OBJECT);
		/*if(ServerConstants.IS_DEV){
			user = new UserInfo("id", "RefreshToken", "subramaniam@gmail.com", true, "Subbu V", "V Subramaniam", "Vaithiyalingam", "", "Male", "en-US", "", "09 06 0000");
			user.setTimeZoneId("America/Los_Angeles");
			user.setqSportsUserKey("ag50aGlucXFjcmlja2V0MnIKCxIEVXNlchhQDA");
			session.setAttribute(ServerConstants.SESSION_USER_OBJECT, user);
		}*/
		if(user !=null){
			chain.doFilter(request, response);
		}else{
			((HttpServletResponse)response).sendRedirect(ServerConstants.OAUTH_SERVLET_URL);
			return;
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
