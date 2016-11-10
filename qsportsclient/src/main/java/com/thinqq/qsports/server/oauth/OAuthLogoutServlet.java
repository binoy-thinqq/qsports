package com.thinqq.qsports.server.oauth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jdo.auth.oauth2.JdoCredentialStore;
import com.thinqq.qsports.server.constants.ServerConstants;
import com.thinqq.qsports.server.persistence.QSportsPersistenceManagerFactory;

/**
 * Servlet implementation class OAuthLogoutServlet
 */
public class OAuthLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OAuthLogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute(ServerConstants.SESSION_USER_OBJECT);
		if(user!=null){
			String userId = user.getId();
			request.getSession().invalidate();
			JdoCredentialStore jdoCredentialStore = new JdoCredentialStore(QSportsPersistenceManagerFactory
					.getPrimaryPersistenceManagerFactory());
		Credential userCredential = new Credential(BearerToken.authorizationHeaderAccessMethod());
		userCredential.setAccessToken(null);
		userCredential.setExpirationTimeMilliseconds(null);
		userCredential.setRefreshToken(null);
		jdoCredentialStore.store(userId, userCredential);
		}
		response.sendRedirect("/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
