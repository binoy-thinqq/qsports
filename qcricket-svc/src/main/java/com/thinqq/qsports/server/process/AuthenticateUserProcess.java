package com.thinqq.qsports.server.process;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.AuthKeyDAO;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.UserDAO;
import com.thinqq.qsports.persistence.dataobjects.AuthKey;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.LoginResponseVo;
import com.thinqq.qsports.persistence.dto.UserRegisterVo;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.InvalidRequestArgumentException;
import com.thinqq.qsports.util.ObjectTransformUtil;

public class AuthenticateUserProcess {

	private Logger logger = Logger.getLogger(AuthenticateUserProcess.class
			.getName());

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserProfileProcess userProfileProcess;

	private static Map<String, AuthKey> userCacheMap = new HashMap<String, AuthKey>();

	/**
	 * Used during login. Updates only the picture URL
	 * 
	 * @param userInfo
	 * @param response
	 * @return
	 */
	public LoginResponseVo authenticate(UserVo userVo) {
		
		boolean isAuthenticationSuccessful = false;
		String authKey = null;
			Session session = null;
			LoginResponseVo lognResponse = new LoginResponseVo();
			try {
				session = SessionManager.getSessionFactory().openSession();
				User user = userDao.getByEmail(session, userVo.getEmail());
				System.out.println("REQUEST PASSWORD: " + userVo.getPassword() + ": ACTUAL PASSWORD: " + user.getPassword());
				if (userVo.getRegistrationType().equals("GOOGLE")
						|| userVo.getRegistrationType().equals("FACEBOOK")) {
					if (user == null) {
						createNewUser(userVo, session);
						isAuthenticationSuccessful = true;
						user = userDao.getByEmail(session, userVo.getEmail());
					} else {
						updateUser(userVo, session, user);
						isAuthenticationSuccessful = true;
					}
				} else {
					isAuthenticationSuccessful = true;
					if (user == null) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.USER_NOT_REGISTERED);
						errorInfo.setErrorDesc("User is not registered with QSports");
						lognResponse.getErrors().add(errorInfo);
						isAuthenticationSuccessful = false;
					} 
					if (!user.getPassword().equals(userVo.getPassword())) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.USER_PASSWORD_NOT_MATCH);
						errorInfo.setErrorDesc("User Email/Password doesn't match" + "REQUEST PASSWORD: " + userVo.getPassword() + ": ACTUAL PASSWORD: " + user.getPassword());
						lognResponse.getErrors().add(errorInfo);
						isAuthenticationSuccessful = false;
					}
				}
				if (isAuthenticationSuccessful) {
					//Fix expiration time
					Calendar expirationTime = Calendar.getInstance();
					expirationTime.add(Calendar.HOUR_OF_DAY, 3);
					//Generate AuthKey
					String authKeyTemp = UUID.randomUUID().toString();
					//Insert into AuthKey
					AuthKey authKeyDo = new AuthKey();
					authKeyDo.setExpiresOn(expirationTime.getTime());
					authKeyDo.setKey(authKeyTemp);
					authKeyDo.setUser(user);
					session.getTransaction().begin();
					AuthKeyDAO.getInstance().insert(session, authKeyDo);
					session.getTransaction().commit();
					//Send Auth Key only if insertion is successfull
					authKey = authKeyTemp;
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Cannot Authenticate User", e);
				if (session != null && session.isOpen()) {
					session.getTransaction().rollback();
				}
				isAuthenticationSuccessful = false;
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			lognResponse.setAuthKey(authKey);
		return lognResponse;
	}
	
	
	/**
	 * Used during registration. Updates only the picture URL
	 * 
	 * @param userInfo
	 * @param response
	 * @return
	 */
	public UserRegisterVo register(UserRegisterVo userRegisterVo) {
			Session session = null;
			try {
				session = SessionManager.getSessionFactory().openSession();
				User user = userDao.getByEmail(session, userRegisterVo.getEmail());
				if (user == null) {
					UserVo userVo = new UserVo();
					ObjectTransformUtil.transform(userRegisterVo, userVo);
					userVo.setRegistrationType("INTERNAL");
					createNewUser(userVo, session);
				} else {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.USER_ALREADY_REGISTERED);
					errorInfo.setErrorDesc("User is already registered with QSports");
					userRegisterVo.getErrors().add(errorInfo);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Cannot Authenticate User", e);
				if (session != null && session.isOpen()) {
					session.getTransaction().rollback();
				}
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		return userRegisterVo;
	}

	/**
	 * Update existing user if required
	 * 
	 * @param userVo
	 * @param session
	 * @param user
	 */
	private void updateUser(UserVo userVo, Session session, User user) {
		if (userVo.getPictureUrl() != null
				&& !userVo.getPictureUrl().equals(user.getPictureUrl())) {
			user.setPictureUrl(userVo.getPictureUrl());
			session.getTransaction().begin();
			userDao.update(session, user);
			session.getTransaction().commit();
		}
		userVo.setUserId(user.getUserId());
		CricketProfileDAO cricketProfileDao = new CricketProfileDAO();
		CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, user.getUserId());
		userVo.setProfileId(cricketProfile.getCricketProfileId());
	}

	/**
	 * Create a new User.
	 * 
	 * @param userVo
	 * @param session
	 * @throws InvalidRequestArgumentException
	 */
	private void createNewUser(UserVo userVo, Session session)
			throws InvalidRequestArgumentException {
		session.getTransaction().begin();
		userProfileProcess.createNewUser(userVo, session);
		session.getTransaction().commit();
	}

	public boolean unauthenticate(String authKey) {
		Session session = SessionManager.getSessionFactory().openSession();
		try {
			AuthKey userAuthEntry = userCacheMap.get(authKey);
			if (userAuthEntry == null) {
				userAuthEntry = getByAuthKey(authKey, session);
			}
			if (userAuthEntry != null) {
				userCacheMap.remove(authKey);
				deleteAuthKeyEntry(userAuthEntry);
				return true;
			}
		}catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		//Returning true for everything. Since logout is logout.
		return true;
	}

	public UserVo checkAuthKeyValidity(String authKey) {
		UserVo userVo = null;
		Session session = SessionManager.getSessionFactory().openSession();
		try {
			AuthKey userAuthEntry = userCacheMap.get(authKey);
			if (userAuthEntry == null) {
				userAuthEntry = getByAuthKey(authKey, session);
			}
			if (userAuthEntry != null) {
				//Check Auth expiry time
				Date timeNow = Calendar.getInstance().getTime();
				if (userAuthEntry.getExpiresOn().after(timeNow)) {
					userCacheMap.put(authKey, userAuthEntry);
					User user = userAuthEntry.getUser();
					userVo = new UserVo();
					ObjectTransformUtil.transformGivenFields(user, userVo, true, User.class.getDeclaredFields());
				}  else {
					userCacheMap.remove(authKey);
					deleteAuthKeyEntry(userAuthEntry);
				}
			}
		}catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userVo;
	}

	/**
	 * Check if the requested authkey exists or not
	 * @param authKeyEntry
	 * @return
	 */
	private AuthKey getByAuthKey(String authKey, Session session) {
		AuthKey authKeyEntry = null;
		AuthKeyDAO authKeyDAO = AuthKeyDAO.getInstance();
		authKeyEntry  = authKeyDAO.getByAuthKey(session, authKey);
		return authKeyEntry;
	}

	private void deleteAuthKeyEntry(AuthKey keyEntry) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			AuthKeyDAO authKeyDAO = AuthKeyDAO.getInstance();
			session.getTransaction().begin();
			authKeyDAO.delete(session, keyEntry);
			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
