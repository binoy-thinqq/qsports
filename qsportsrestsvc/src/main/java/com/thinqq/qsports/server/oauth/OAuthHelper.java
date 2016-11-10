package com.thinqq.qsports.server.oauth;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jdo.auth.oauth2.JdoCredentialStore;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.constants.ServerConstants;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.QSportsPersistenceManagerFactory;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.shared.UserStatusEnum;

public class OAuthHelper {

	private static final Logger logger = Logger.getLogger(UserProfileProcess.class.getName());
	
	private static boolean saveUserInformation(UserInfo userInfo) {
		UserDAO userDAO = new UserDAO();
		PersistenceManager pm = DAO.getPersistenceManager();
		User user = userDAO.findUserByEmail(userInfo.getEmail(), pm);
		boolean isNewUser;
		if(user!=null){
			isNewUser = false;
			user.setGoogleUserId(userInfo.getId());
			if(userInfo.getRefreshToken()!=null){
				user.setGoogleRefreshToken(userInfo.getRefreshToken());
			}
			user.setLocale(userInfo.getLocale());
			user.setGooglePictureURL(userInfo.getPicture());
			user.setGender(userInfo.getGender());
			user.setUpdatedTime(Calendar.getInstance().getTime());
			pm.currentTransaction().begin();
			userDAO.update(user, pm);
			pm.currentTransaction().commit();
			logger.info("User information has been updated successfully");
		} else {
			logger.info("Creating new user");
			isNewUser = true;
			user = new User();
			user.setGivenName(userInfo.getGivenName());
			user.setEmail(userInfo.getEmail());
			user.setGender(userInfo.getGender());
			user.setGoogleUserId(userInfo.getId());
			if(userInfo.getRefreshToken()!=null){
				user.setGoogleRefreshToken(userInfo.getRefreshToken());
			}
			user.setGooglePictureURL(userInfo.getPicture());
			user.setBirthday(userInfo.getBirthday());
			user.setLocale(userInfo.getLocale());
			user.setUserType("GOOGLE");
			if(userInfo.isVerifiedEmail()){
				user.setStatus(UserStatusEnum.CONFIRMED.name());
			}else{
				user.setStatus(UserStatusEnum.UNCONFIRMED.name());
			}
			Date time = Calendar.getInstance().getTime();
			user.setCreatedTime(time);
			user.setUpdatedTime(time);
			pm.currentTransaction().begin();
			userDAO.insert(user, pm);
			pm.currentTransaction().commit();
		}
		String userKey = KeyFactory.keyToString(user.getKey());
		userInfo.setqSportsUserKey(userKey);
		userInfo.setTimeZoneId(user.getTimezone());
		pm.close();
		return isNewUser;
	}

	public static void onSignInSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential, String userId) throws IOException{
		String userInfoUrl = ServerConstants.GOOGLE_USER_INFO_API_URL+credential.getAccessToken();
		/*Get User Information from Google*/
		HttpRequest request = new NetHttpTransport().createRequestFactory().buildGetRequest(new GenericUrl(userInfoUrl));
		request.setParser(new JsonObjectParser(new JacksonFactory()));
		try {
			HttpResponse response = request.execute();

			StringReader sReader = new StringReader(response.parseAsString());
			JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
			GoogleUserProfile googleProfile = parser.parseAndClose(sReader, GoogleUserProfile.class);
			String userProfileId;
			if(ServerConstants.IS_DEV){
				userProfileId = "1";
			}else{
				userProfileId = googleProfile.getId();
			}
			UserInfo user = new UserInfo(userProfileId, credential.getRefreshToken(),googleProfile.getEmail(),
					googleProfile.isVerifiedEmail(),googleProfile.getName(),
					googleProfile.getGivenName(),googleProfile.getFamilyName(),
					googleProfile.getPicture(), googleProfile.getGender(), 
					googleProfile.getLocale(),googleProfile.getLink(),googleProfile.getBirthday());

			req.getSession().setAttribute(ServerConstants.SESSION_USER_OBJECT, user);
			boolean isNewUser = saveUserInformation(user);
			logger.info("User has been successfully created. User key is "+user.getqSportsUserKey());
			String redirectURL = ServerConstants.REDIRECT_URL;
			if(isNewUser){
				redirectURL = redirectURL+ServerConstants.NEW_USER_PARAM;
				UserProfileProcess.getInstance().createCricketProfile(user.getqSportsUserKey());
			}
			PersistenceManager pm = DAO.getPersistenceManager();
			CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
			CricketProfile profile = cricketProfileDAO.findCricketProfileByUserKey(user.getqSportsUserKey(), pm);
			pm.close();
			user.setqSportCricketProfileKey(KeyFactory.keyToString(profile.getKey()));
			resp.sendRedirect(redirectURL);
		} catch (HttpResponseException e){
			if(e.getStatusCode() == 401){
				//Delete old JDO Credential
				JdoCredentialStore jdoCredentialStore = new JdoCredentialStore(QSportsPersistenceManagerFactory
						.getPrimaryPersistenceManagerFactory());
				Credential userCredential = new Credential(BearerToken.authorizationHeaderAccessMethod());
				userCredential.setAccessToken(null);
				userCredential.setExpirationTimeMilliseconds(null);
				userCredential.setRefreshToken(null);
				jdoCredentialStore.store(userId, userCredential);
				//Redirect to Sign In page
				resp.sendRedirect(ServerConstants.OAUTH_SERVLET_URL);
			}
		}
	}
}
