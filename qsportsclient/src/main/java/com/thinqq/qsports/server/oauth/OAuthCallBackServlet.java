package com.thinqq.qsports.server.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jdo.auth.oauth2.JdoCredentialStore;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.appengine.api.users.UserServiceFactory;
import com.thinqq.qsports.server.constants.ServerConstants;
import com.thinqq.qsports.server.persistence.QSportsPersistenceManagerFactory;

/**
 * Servlet implementation class OAuthCallBackServlet
 */
public class OAuthCallBackServlet extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 1L;

	public OAuthCallBackServlet() {
		super();
	}

	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
			throws ServletException, IOException {
		OAuthHelper.onSignInSuccess(req, resp, credential, getUserId(req));
	}

	@Override
	protected void onError(
			HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
					throws ServletException, IOException {
		resp.sendRedirect("/");
	}	

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
	IOException {
		List<String> list = Arrays.asList(ServerConstants.GOOGLE_USER_EMAIL_SCOPE, ServerConstants.GOOGLE_USER_PROFILE_SCOPE);
		GoogleAuthorizationCodeFlow.Builder authCodeFlowBuilder = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), 
				new JacksonFactory(),
				ServerConstants.GOOGLE_CLIENT_ID,ServerConstants.GOOGLE_SECRET_KEY,list);
		authCodeFlowBuilder.setCredentialStore(new JdoCredentialStore(QSportsPersistenceManagerFactory
				.getPrimaryPersistenceManagerFactory()));
		return authCodeFlowBuilder.build();
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(ServerConstants.OAUTH_CALLBACK_SERVLET_URL);
		return url.build();
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException,
	IOException {
		if(ServerConstants.IS_DEV){
			return UUID.randomUUID().toString();
		} else {
			return UserServiceFactory.getUserService().getCurrentUser().getUserId();
		}
	}

}
