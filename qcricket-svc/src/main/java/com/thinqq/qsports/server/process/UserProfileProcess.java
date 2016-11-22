package com.thinqq.qsports.server.process;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamPlayersDAO;
import com.thinqq.qsports.persistence.dao.UserDAO;
import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.BattingStatisticsVo;
import com.thinqq.qsports.persistence.dto.BowlingStatisticsVo;
import com.thinqq.qsports.persistence.dto.CricketProfileMinVo;
import com.thinqq.qsports.persistence.dto.CricketProfileVo;
import com.thinqq.qsports.persistence.dto.SaveUserVo;
import com.thinqq.qsports.persistence.dto.UserInfoVo;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.InvalidRequestArgumentException;
import com.thinqq.qsports.server.error.UnauthorizedException;
import com.thinqq.qsports.shared.cricket.ContactsSharePreferenceEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.cricket.UserStatusEnum;
import com.thinqq.qsports.util.ObjectTransformUtil;


public class UserProfileProcess {
	
	Logger logger = Logger.getLogger(UserProfileProcess.class.getName());
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	CricketProfileDAO cricketProfileDao;
	
	@Autowired
	BattingStatisticsDAO battingStatsDao;
	
	@Autowired
	BowlingStatisticsDAO bowlingStatsDao;
	/**
	 * Return the user given userEmail
	 * @param email
	 * @return
	 */
	public UserVo getUserByEmail(final String email, final Integer signedInUserId, final Integer signedInProfileId) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			User user = userDao.getByEmail(session, email);
			UserVo userVo = new UserVo();
			if (user == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with email " + email + " Not Found");
				userVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(user, userVo);
			}
			return userVo;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot Get User", e);
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return null;
	}
	
	/**
	 * Return the user given userEmail
	 * @param email
	 * @return
	 */
	public UserVo getUserById(final Integer id, final Integer signedInUserId, final Integer signedInProfileId) {
		Session session = null;
		UserVo userVo = new UserVo();
		try {
			session = SessionManager.getSessionFactory().openSession();
			User user = userDao.getByPrimaryKey(session, id);
			
			if (user == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with ID " + id + " Not Found");
				userVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(user, userVo);
				boolean showContactsToUser = showContacts(session, user, signedInProfileId);
				boolean isEditable = user.getUserId().equals(signedInUserId);
				userVo.setEditable(isEditable);
				userVo.setContactShown(showContactsToUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
			errorInfo.setErrorDesc("User with ID " + id + " Not Found");
			userVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userVo;
	}
	
	/**
	 * Return the user given userEmail
	 * @param email
	 * @return
	 */
	public UserVo getUserById(final Integer id) {
		Session session = null;
		UserVo userVo = new UserVo();
		try {
			session = SessionManager.getSessionFactory().openSession();
			User user = userDao.getByPrimaryKey(session, id);
			
			if (user == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with ID " + id + " Not Found");
				userVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(user, userVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
			errorInfo.setErrorDesc("User with ID " + id + " Not Found");
			userVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userVo;
	}
	
	private boolean showContacts(final Session session, final User user, final Integer signedInProfileId) {
		if (user.getContactShown() != null) {
			if (user.getContactShown().equals(ContactsSharePreferenceEnum.SHARED_TO_NONE)) {
				return false;
			} else if (user.getContactShown().equals(ContactsSharePreferenceEnum.SHARED_TO_ALL_USERS)) {
				return true;
			} else if (user.getContactShown().equals(ContactsSharePreferenceEnum.SHARED_TO_TEAM_MATES)) {
				CricketTeamPlayersDAO teamPlayersDAO = new CricketTeamPlayersDAO();
				List<CricketTeamPlayers> listCommonTeams = teamPlayersDAO.getCommonTeamPlayerRecord(session, user.getUserId(), signedInProfileId);
				if (listCommonTeams.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * Save a user
	 * @param userVo
	 * @return
	 * @throws InvalidRequestArgumentException
	 */
	public UserVo saveUser(final SaveUserVo saveUserVo) throws InvalidRequestArgumentException, UnauthorizedException {
		
		/*if (userVo.getUserId() == null || userVo.getEmail() == null) {
			throw new InvalidRequestArgumentException();
		}
		if (!userVo.getUserId().equals(userVo.getSignedInUserId())) {
			throw new UnauthorizedException("Not Authorized to updated another users details");
		}*/
		UserVo userVo = new UserVo();
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			User user = userDao.getByPrimaryKey(session, saveUserVo.getSignedInUserId());
			ObjectTransformUtil.transform(saveUserVo, user);
			
			//Setting the values which will not be transformed
			user.setUserId(saveUserVo.getSignedInUserId());
			user.setUpdatedId(saveUserVo.getSignedInUserId());
			user.setUpdatedTime(Calendar.getInstance().getTime());
			if(user.getStatus() == null 
					|| UserStatusEnum.FIRST_LOGIN_DONE.getName().equals(user.getStatus())){
				user.setStatus(UserStatusEnum.SETUP.getName());
			}
			session.beginTransaction();
			userDao.update(session, user);
			session.getTransaction().commit();
			ObjectTransformUtil.transform(user, userVo, false);
			
		} catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			logger.log(Level.SEVERE, "Exception is saving user", e);
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			userVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userVo;
	}
	
	/**
	 * 
	 * Save a user
	 * @param userVo
	 * @return
	 * @throws InvalidRequestArgumentException
	 */
	public UserVo createNewUser(final UserVo userVo, final Session session) throws InvalidRequestArgumentException {
		if (userVo.getEmail() == null) {
			throw new InvalidRequestArgumentException();
		}
		try {
			User user = new User();
			ObjectTransformUtil.transform(userVo, user);
			//Setting the values which will not be transformed
			Date createdTime = Calendar.getInstance().getTime();
			user.setStatus(UserStatusEnum.FIRST_LOGIN_DONE.getName());
			user.setCreatedId(-1);
			user.setCreatedTime(createdTime);
			user.setUpdatedId(userVo.getSignedInUserId());
			user.setUpdatedTime(createdTime);
			userDao.insert(session, user);
			/**Default Cricket Profile*/
			CricketProfile cktProfile = createDefaultCricketProfile(createdTime, user);
			cktProfile.setUser(user);
			cricketProfileDao.insert(session, cktProfile);
			/**Default Batting Stats*/
			for(MatchFormatEnum format : MatchFormatEnum.values()) {
				BattingStatistics defaultBatStat = createBattingStatistics(format.getId());
				BowlingStatistics defaultBowlStat = createBowlingStatistics(format.getId());
				defaultBatStat.setCricketProfile(cktProfile);
				defaultBatStat.setCreatedTime(createdTime);
				defaultBatStat.setUpdateTime(createdTime);
				defaultBowlStat.setCreatedTime(createdTime);
				defaultBowlStat.setUpdateTime(createdTime);
				defaultBowlStat.setCricketProfile(cktProfile);
				battingStatsDao.insert(session, defaultBatStat);
				bowlingStatsDao.insert(session, defaultBowlStat);
			}
			userVo.setUserId(user.getUserId());
			userVo.setProfileId(cktProfile.getCricketProfileId());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Service Error in createNewUser" + e.getMessage());
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			userVo.getErrors().add(errorInfo);
		} 
		return userVo;
	}
	
	/**
	 * Create Default Cricket Profile
	 * @param createdTime
	 * @return
	 */
	private CricketProfile createDefaultCricketProfile(Date createdTime , User user) {
		CricketProfile cricketProfile = new CricketProfile();
		cricketProfile.setWicketKeeper(false);
		cricketProfile.setCreatedId(user.getUserId());
		cricketProfile.setCreatedTime(createdTime);
		cricketProfile.setUpdatedId(user.getUserId());
		cricketProfile.setUpdatedTime(createdTime);
		return cricketProfile;
	}
	
	/**
	 * Create Default Batting Stats
	 * @param cricketFormat
	 * @return
	 */
	private BattingStatistics createBattingStatistics(int cricketFormat) {
		BattingStatistics battingStats = new BattingStatistics();
		battingStats.setAverage(0F);
		battingStats.setStrikeRate(0F);
		battingStats.setCricketFormat(cricketFormat);
		return battingStats;
	}
	
	/**
	 * Create Default Bowling Stats
	 * @param cricketFormat
	 * @return
	 */
	private BowlingStatistics createBowlingStatistics(int cricketFormat) {
		BowlingStatistics bowlingStats = new BowlingStatistics();
		bowlingStats.setCricketFormat(cricketFormat);
		return bowlingStats;
	}

	/**
	 * Get Cricket Profile of a user
	 * @param userId
	 * @param isStatsRequired
	 * @return
	 */
	public CricketProfileVo getCricketProfileByUserId(final Integer userId, final boolean isStatsRequired) {
		Session session = null;
		CricketProfileVo profileVo = new CricketProfileVo();
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, userId);
			if (cricketProfile == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with ID " + userId + " Not Found");
				profileVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(cricketProfile, profileVo);
				if (isStatsRequired) {
					profileVo.setBattingStats(fillBattingStats(session, cricketProfile));
					profileVo.setBowlingStats(fillBowlingStats(session, cricketProfile));
				}
				UserInfoVo userInfo = new UserInfoVo();
				User user = cricketProfile.getUser();
				ObjectTransformUtil.transformGivenFields(user, userInfo, true, User.class.getDeclaredFields());
				profileVo.setUserInfo(userInfo);
			}
			
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Service Error in getCricketProfile" + e.getMessage());
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			profileVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return profileVo;
	}

	/**
	 * Get Cricket Profile 
	 * @param userId
	 * @param isStatsRequired
	 * @return
	 */
	public CricketProfileVo getCricketProfile(final Integer profileId, final boolean isStatsRequired) {
		Session session = null;
		CricketProfileVo profileVo = new CricketProfileVo();
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketProfile cricketProfile = cricketProfileDao.getByPrimaryKey(session, profileId);
			if (cricketProfile == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with ID " + profileId + " Not Found");
				profileVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(cricketProfile, profileVo);
				if (isStatsRequired) {
					profileVo.setBattingStats(fillBattingStats(session, cricketProfile));
					profileVo.setBowlingStats(fillBowlingStats(session, cricketProfile));
				}
				UserInfoVo userInfo = new UserInfoVo();
				User user = cricketProfile.getUser();
				ObjectTransformUtil.transformGivenFields(user, userInfo, true, User.class.getDeclaredFields());
				profileVo.setUserInfo(userInfo);
			}
			
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Service Error in getCricketProfile" + e.getMessage());
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			profileVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return profileVo;
	}
	
	/**
	 * Fill bowling Stats
	 * @param session
	 * @param profileVo
	 * @param cricketProfile
	 */
	public Set<BowlingStatisticsVo> fillBowlingStats(Session session,CricketProfile cricketProfile) {
		Set<BowlingStatisticsVo> bowlingStatsVo = new HashSet<BowlingStatisticsVo>();
		List<BowlingStatistics> bowlingStats = bowlingStatsDao.getByCricketProfileId(session, cricketProfile.getCricketProfileId());
		if (bowlingStats != null && !bowlingStats.isEmpty()) {
			
			for(BowlingStatistics bowlingStat : bowlingStats) {
				BowlingStatisticsVo bowlingStatVo = new BowlingStatisticsVo();
				try {
					ObjectTransformUtil.transform(bowlingStat, bowlingStatVo);
				} catch (Exception e){
					logger.log(Level.SEVERE, "Cannot Create Bowling Statistics", e);
				}
				bowlingStatsVo.add(bowlingStatVo);
			}
		}
		return bowlingStatsVo;
	}

	/**
	 * Fill Batting Stats
	 * @param session
	 * @param profileVo
	 * @param cricketProfile
	 */
	public Set<BattingStatisticsVo> fillBattingStats(Session session, CricketProfile cricketProfile) {
		Set<BattingStatisticsVo> battingStatsVo = new HashSet<BattingStatisticsVo>();
		List<BattingStatistics> battingStats = battingStatsDao.getByCricketProfileId(session, cricketProfile.getCricketProfileId());
		if (battingStats != null && !battingStats.isEmpty()) {
			
			for(BattingStatistics battingStat : battingStats) {
				BattingStatisticsVo battingStatVo = new BattingStatisticsVo();
				try{
					ObjectTransformUtil.transform(battingStat, battingStatVo);
				} catch (Exception e){
					logger.log(Level.SEVERE, "Cannot Create Batting Statistics", e);
				}	
				battingStatsVo.add(battingStatVo);
			}
		}
		return battingStatsVo;
	}
	
	
	/**
	 * Get Cricket Profile of a user
	 * @param userId
	 * @param isStatsRequired
	 * @return
	 */
	public void saveCricketProfile(final CricketProfileMinVo profileVo) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketProfile cricketProfile = cricketProfileDao.getByPrimaryKey(session, profileVo.getCricketProfileId());
			if (cricketProfile == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("User with ID " + profileVo.getCricketProfileId() + " Not Found");
				profileVo.getErrors().add(errorInfo);
			} else {
				ObjectTransformUtil.transform(profileVo, cricketProfile);
				cricketProfile.setUpdatedId(profileVo.getSignedInUserId());
				cricketProfile.setUpdatedTime(Calendar.getInstance().getTime());
				session.beginTransaction();
				cricketProfileDao.update(session, cricketProfile);
				session.getTransaction().commit();
				ObjectTransformUtil.transform(cricketProfile,profileVo, false);
			}
			
			
		} catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			logger.log(Level.SEVERE, "Service Error in saveCricketProfile" + e.getMessage());
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			profileVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
	}
	
	/**
	 * 
	 * Save a user
	 * @param userVo
	 * @return
	 * @throws InvalidRequestArgumentException
	 */
	public UserVo saveUser(final UserVo userVo) throws InvalidRequestArgumentException, UnauthorizedException {
		
		/*if (userVo.getUserId() == null || userVo.getEmail() == null) {
			throw new InvalidRequestArgumentException();
		}
		if (!userVo.getUserId().equals(userVo.getSignedInUserId())) {
			throw new UnauthorizedException("Not Authorized to updated another users details");
		}*/
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			User user = userDao.getByPrimaryKey(session, userVo.getSignedInUserId());
			ObjectTransformUtil.transform(userVo, user);
			
			//Setting the values which will not be transformed
			user.setUserId(userVo.getSignedInUserId());
			user.setUpdatedId(userVo.getSignedInUserId());
			user.setUpdatedTime(Calendar.getInstance().getTime());
			if(user.getStatus() == null 
					|| UserStatusEnum.FIRST_LOGIN_DONE.getName().equals(user.getStatus())){
				user.setStatus(UserStatusEnum.SETUP.getName());
			}
			session.beginTransaction();
			userDao.update(session, user);
			session.getTransaction().commit();
			ObjectTransformUtil.transform(user, userVo, false);
			
		} catch (Exception e) {
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			logger.log(Level.SEVERE, "Exception in saving user", e);
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service error please check logs");
			userVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return userVo;
	}
	
	
}
