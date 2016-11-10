package com.thinqq.qsports.server.process;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.ServerGenie;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.UserStatusEnum;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.FieldingPosition;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.userprofile.BattingStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.BowlingStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserInfoRequestVo;
import com.thinqq.qsports.shared.userprofile.UserInfoResponseVo;
import com.thinqq.qsports.shared.validation.ErrorVo;

public class UserProfileProcess {
	private static UserProfileProcess s_instance = new UserProfileProcess();
	private static final Logger logger = Logger.getLogger(UserProfileProcess.class.getName());
	private static DecimalFormat twoDecimal = new DecimalFormat("#0.00");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	public UserProfileProcess(){}
	public static UserProfileProcess getInstance(){
		return s_instance;
	}
	
	public void getUserProfile(UserInfoRequestVo request, UserInfoResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		User user = null;
		if(request.getKey() !=null){
			user = userDAO.findUserByKey(request.getKey(), pm);
			
		} else if(request.getEmail() != null) {
			user = userDAO.findUserByEmail(request.getEmail(),pm);
		}
		if(user == null){
			ErrorVo error = new ErrorVo();
			error.setErrorId(ErrorConstants.USER_NOT_FOUND);
			error.setErrorMessage("User Not Found");
			List<ErrorVo> errors = new ArrayList<ErrorVo>();
			errors.add(error);
			response.setErrors(errors);
			return;
		}
		response.setPicture(user.getGooglePictureURL());
		response.setName(user.getGivenName());
		response.setLastName(user.getLastName());
		response.setGender(user.getGender());
		response.setEmail(user.getEmail());
		response.setUserKey(KeyFactory.keyToString(user.getKey()));
		DATE_FORMAT.setTimeZone(ServerGenie.getTimeZone(request.getTimeZone()));
		if(user.getDob()!=null) {
			response.setBirthday(DATE_FORMAT.format(user.getDob()));
		}
		
		response.setCity(user.getCity());
		response.setState(user.getState());
		response.setUserTimeZone(user.getTimezone());
		pm.close();
	}
	
	/**
	 * Get Cricket Profile
	 * @param response
	 * @param userKey
	 */
	public void getCricketProfile(CricketProfileResponseVo response, String userKey, boolean isMin){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
		CricketProfile cricProfile = cricketProfileDAO.findCricketProfileByUserKey(userKey,pm);
		
		response.setCricketProfileKey(KeyFactory.keyToString(cricProfile.getKey()));
		response.setBattingHandType(cricProfile.getBattingHandType());
		response.setBattingOrder(cricProfile.getBattingOrder());
		response.setBowlingHandType(cricProfile.getBowlingHandType());
		response.setBowlingMethod(cricProfile.getBowlingMethod());
		response.setFieldPosition(cricProfile.getFieldPosition());
		
		if (!isMin) {
			response.setBattingStats(getBattingStats(pm, cricProfile));
			response.setBowlingStats(getBowlingStats(pm, cricProfile));
		}
		
		pm.close();
	}
	
	/**
	 * Get Bowling Stats.
	 * @param pm
	 * @param cricProfile
	 * @return
	 */
	private List<BowlingStatisticsResponseVo> getBowlingStats(PersistenceManager pm,
			CricketProfile cricProfile) {
		List<BowlingStatisticsResponseVo> bowlingStatsVo = new ArrayList<BowlingStatisticsResponseVo>();
		BowlingStatisticsDAO bowlingStatDao = new BowlingStatisticsDAO();
		List<BowlingStatistics> bowlingStats = bowlingStatDao.getBowlingStatisticsForCricketProfile(KeyFactory.keyToString(cricProfile.getKey()), pm);
		for (BowlingStatistics bowlingStat: bowlingStats) {
			BowlingStatisticsResponseVo bowlingStatVo = new BowlingStatisticsResponseVo();
			bowlingStatVo.setAvg(bowlingStat.getAvg());
			bowlingStatVo.setBalls(bowlingStat.getBalls());
			bowlingStatVo.setBestBowling(bowlingStat.getBestBowling());
			bowlingStatVo.setCatches(bowlingStat.getCatches());
			bowlingStatVo.setCreatedTime(bowlingStat.getCreatedTime());
			bowlingStatVo.setCreatedUserKey(KeyFactory.keyToString(bowlingStat.getCreatedUserKey()));
			bowlingStatVo.setCricketFormat(bowlingStat.getCricketFormat());
			bowlingStatVo.setEconomy(bowlingStat.getEcon());
			bowlingStatVo.setInnings(bowlingStat.getInn());
			bowlingStatVo.setMatches(bowlingStat.getMat());
			bowlingStatVo.setNoOfFiveWickets(bowlingStat.getFiveWkts());
			bowlingStatVo.setNoOfThreeWickets(bowlingStat.getThreeWkts());
			bowlingStatVo.setNoOfWickets(bowlingStat.getWkts());
			bowlingStatVo.setRuns(bowlingStat.getRuns());
			bowlingStatVo.setStumps(bowlingStat.getStumps());
			bowlingStatVo.setUpdatedTime(bowlingStat.getUpdatedTime());
			bowlingStatVo.setUpdatedUserKey(KeyFactory.keyToString(bowlingStat.getUpdatedUserKey()));
			bowlingStatsVo.add(bowlingStatVo);
		}
		return bowlingStatsVo;
	}
	
	/**
	 * Getting Batting stats of a given Cricket Profile
	 * @param pm
	 * @param cricProfile
	 * @return
	 */
	private List<BattingStatisticsResponseVo> getBattingStats(PersistenceManager pm,
			CricketProfile cricProfile) {
		List<BattingStatisticsResponseVo> battingStatsVo = new ArrayList<BattingStatisticsResponseVo>();
		BattingStatisticsDAO battingStatsDao = new BattingStatisticsDAO();
		List<BattingStatistics> battingStats = battingStatsDao.getBattingStatisticsForCricketProfile(KeyFactory.keyToString(cricProfile.getKey()), pm);
		for (BattingStatistics battingStat : battingStats) {
			BattingStatisticsResponseVo statVo = new BattingStatisticsResponseVo();
			statVo.setAvg(battingStat.getAvg());
			statVo.setCreatedTime(battingStat.getCreatedTime());
			statVo.setCreatedUserKey(KeyFactory.keyToString(battingStat.getCreatedUserKey()));
			statVo.setCricketFormat(battingStat.getCricketFormat());
			statVo.setFifties(battingStat.getFifs());
			statVo.setFours(battingStat.getFours());
			statVo.setHighestScore(battingStat.getHs());
			statVo.setHundreds(battingStat.getHunds());
			statVo.setInningsPlayed(battingStat.getInn());
			statVo.setMatchesPlayed(battingStat.getMatches());
			statVo.setNotOuts(battingStat.getNo());
			statVo.setRuns(battingStat.getRuns());
			statVo.setSixes(battingStat.getSixes());
			statVo.setStrikeRate(battingStat.getSr());
			statVo.setThirties(battingStat.getThirts());
			statVo.setUpdatedUserKey(KeyFactory.keyToString(battingStat.getUpdatedUserKey()));
			statVo.setUpdateTime(battingStat.getUpdatedTime());
			battingStatsVo.add(statVo);
		}
		return battingStatsVo;
	}
	
	public void saveUserInfo(UserInfoRequestVo request) {
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findUserByKey(request.getSignedInUserKey(), pm);
		if (user != null) {
			user.setUpdatedTime(Calendar.getInstance().getTime());
			user.setUpdatedUserKey(KeyFactory.stringToKey(request
					.getSignedInUserKey()));
			if (request.getName() != null) {
				user.setGivenName(request.getName());
			}
			if (request.getLastName() != null) {
				user.setLastName(request.getLastName());
			}
			if (request.getIsoCountryCode() != null) {
				user.setIsoCountryCode(request.getIsoCountryCode());
			}
			SimpleDateFormat format = new SimpleDateFormat();
			if (request.getBirthday() != null) {
				try {
					user.setDob(format.parse(request.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (request.getGender() != null) {
				user.setGender(request.getGender());
			}
			if (user.getCity() != null) {
				user.setCity(request.getCity());
			}
			if (request.getState() != null) {
				user.setState(request.getState());
			}
			if (request.getTimeZone() != null) {
				user.setTimezone(request.getTimeZone());
			}
			pm.currentTransaction().begin();
			userDAO.update(user, pm);
			pm.currentTransaction().commit();
		}
	}
	public void saveUserProfile(SaveUserProfileRequestVo request, SaveUserProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findUserByKey(request.getSignedInUserKey(), pm);
		user.setGivenName(request.getName());
		user.setIsoCountryCode(request.getCountry());
		user.setDob(request.getDob());
		user.setGender(request.getSex());
		user.setCity(request.getCity());
		user.setState(request.getState());
		user.setIsoCountryCode(request.getCountry());
		user.setTimezone(request.getTimeZoneStr());
		CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
		CricketProfile cricProfile = cricketProfileDAO.getCricketProfileByUser(user,pm);
		cricProfile.setBattingHandType(request.getBattingHandType());
		cricProfile.setBattingOrder(request.getBattingOrder());
		cricProfile.setBowlingHandType(request.getBowlingHandType());
		cricProfile.setBowlingMethod(request.getBowlingMethod());
		cricProfile.setFieldPosition(request.getFieldPosition());
		response.setSavedUserKey(KeyFactory.keyToString(user.getKey()));
		response.setSavedCricketProfileKey(KeyFactory.keyToString(cricProfile.getKey()));
		pm.currentTransaction().begin();
		userDAO.update(user, pm);
		pm.currentTransaction().commit();
		pm.currentTransaction().begin();
		cricketProfileDAO.update(cricProfile, pm);
		pm.currentTransaction().commit();
		
		//Used to update the User object(if present) in the session
		response.setSavedUsername(user.getGivenName());
		response.setSavedUserTimeZoneId(user.getTimezone());
		pm.close();
	}
	
	/**
	 * Create CricketProfile
	 * @param userKey
	 * @return
	 */
	public String createCricketProfile(String userKey){
		
		logger.info("Creating Cricket Profile for user : "+userKey);
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
		BattingStatisticsDAO battingDAO = new BattingStatisticsDAO();
		BowlingStatisticsDAO bowlingDAO = new BowlingStatisticsDAO();

		Date date = Calendar.getInstance().getTime();

		//1. Get User
		User user = userDAO.findUserByKey(userKey, pm);
		if(user == null){
			logger.severe("User not found for key"+userKey);
		}
		//2. Create Default Cricket Profile
		CricketProfile cricketProfile = new CricketProfile();
		cricketProfile.setBattingHandType(HandTypeEnum.NOT_SPECIFIED.getId());
		cricketProfile.setBattingOrder(BattingOrder.NOT_SPECIFIED.getId());
		cricketProfile.setBowlingHandType(HandTypeEnum.NOT_SPECIFIED.getId());
		cricketProfile.setBowlingMethod(BowlingStyle.NOT_SPECIFIED.getId());
		cricketProfile.setFieldPosition(FieldingPosition.NOT_SPECIFIED.getId());
		cricketProfile.setCreatedTime(date);
		cricketProfile.setCreatedUserKey(user.getKey());
		cricketProfile.setUpdatedTime(date);
		cricketProfile.setUpdatedUserKey(user.getKey());
		cricketProfile.setUser(user.getKey());
		pm.currentTransaction().begin();
		cricketProfileDAO.insert(cricketProfile, pm);
		pm.currentTransaction().commit();

		//3. Create Batting Statistics 	
		BattingStatistics battingT20 = new BattingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, 0, cricketProfile.getKey(), MatchFormatEnum.T20.getId(), user.getKey(),date,user.getKey(),date);
		BattingStatistics battingOD = new BattingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, 0, cricketProfile.getKey(), MatchFormatEnum.OD.getId(), user.getKey(),date,user.getKey(),date);
		BattingStatistics battingTest = new BattingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, 0, cricketProfile.getKey(), MatchFormatEnum.TEST.getId(), user.getKey(),date,user.getKey(),date);
		pm.currentTransaction().begin();
		battingDAO.insert(battingT20, pm);
		pm.currentTransaction().commit();
		pm.currentTransaction().begin();
		battingDAO.insert(battingOD, pm);
		pm.currentTransaction().commit();
		pm.currentTransaction().begin();
		battingDAO.insert(battingTest, pm);
		pm.currentTransaction().commit();

		//4. Create Bowling Statistics
		BowlingStatistics bowlingT20 = new BowlingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, "", cricketProfile.getKey(), MatchFormatEnum.T20.getId(), user.getKey(),date,user.getKey(),date);
		BowlingStatistics bowlingOD = new BowlingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, "", cricketProfile.getKey(), MatchFormatEnum.OD.getId(), user.getKey(),date,user.getKey(),date);
		BowlingStatistics bowlingTest = new BowlingStatistics(0, 0, 0, 0, 0.0f, 0.0f, 0, 0, 0, 0, 0, "", cricketProfile.getKey(), MatchFormatEnum.TEST.getId(), user.getKey(),date,user.getKey(),date);
		pm.currentTransaction().begin();
		bowlingDAO.insert(bowlingT20, pm);
		pm.currentTransaction().commit();
		pm.currentTransaction().begin();
		bowlingDAO.insert(bowlingOD, pm);
		pm.currentTransaction().commit();
		pm.currentTransaction().begin();
		bowlingDAO.insert(bowlingTest, pm);
		pm.currentTransaction().commit();

		//6. Update Cricket Profile with the Statistics Key
		cricketProfile.setT20battingStat(battingT20.getKey());
		cricketProfile.setT20bowlingStatistics(bowlingT20.getKey());

		cricketProfile.setODbattingStat(battingOD.getKey());
		cricketProfile.setODbowlingStatistics(bowlingOD.getKey());

		cricketProfile.setTestbattingStat(battingTest.getKey());
		cricketProfile.setTestbowlingStatistics(bowlingTest.getKey());

		pm.currentTransaction().begin();
		cricketProfileDAO.update(cricketProfile, pm);
		pm.currentTransaction().commit();
		pm.close();
		return KeyFactory.keyToString(cricketProfile.getKey());

	}
	
	/**
	 * Get Cricket Statistics
	 * @param request
	 * @param response
	 */
	public void getCricketStatistics(CricketStatisticsRequestVo request, CricketStatisticsResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		String cricketProfileKey = request.getCricketProfileKey();
		response.setCricketProfileKey(cricketProfileKey);
		boolean isMinStats = request.isMinimumStatsReq();
		BattingStatisticsDAO battingDAO = new BattingStatisticsDAO();
		BowlingStatisticsDAO bowlingDAO = new BowlingStatisticsDAO();
		List<BattingStatistics> battingList = battingDAO.getBattingStatisticsForCricketProfile(cricketProfileKey, pm);
		List<BowlingStatistics> bowlingList = bowlingDAO.getBowlingStatisticsForCricketProfile(cricketProfileKey, pm);
		List<String> t20Batting = new ArrayList<String>();
		List<String> oDBatting = new ArrayList<String>();
		List<String> testBatting = new ArrayList<String>();
		List<String> t20Bowling = new ArrayList<String>();
		List<String> oDBowling = new ArrayList<String>();
		List<String> testBowling = new ArrayList<String>();

		for(BattingStatistics stats: battingList){
			if(MatchFormatEnum.T20.getId().equals(stats.getCricketFormat())){
				t20Batting.add(Integer.toString(stats.getMatches()));
				if(!isMinStats){
					t20Batting.add(Integer.toString(stats.getInn()));
					t20Batting.add(Integer.toString(stats.getNo()));
				}
				t20Batting.add(Integer.toString(stats.getRuns()));
				t20Batting.add(twoDecimal.format(stats.getSr()));
				t20Batting.add(twoDecimal.format(stats.getAvg()));
				t20Batting.add(Integer.toString(stats.getHs()));
				t20Batting.add(Integer.toString(stats.getHunds()));
				t20Batting.add(Integer.toString(stats.getFifs()));
				if(!isMinStats){
					t20Batting.add(Integer.toString(stats.getThirts()));
					t20Batting.add(Integer.toString(stats.getSixes()));
					t20Batting.add(Integer.toString(stats.getFours()));
				}
			}else if(MatchFormatEnum.OD.getId().equals(stats.getCricketFormat())){
				oDBatting.add(Integer.toString(stats.getMatches()));
				if(!isMinStats){
					oDBatting.add(Integer.toString(stats.getInn()));
					oDBatting.add(Integer.toString(stats.getNo()));
				}
				oDBatting.add(Integer.toString(stats.getRuns()));
				oDBatting.add(twoDecimal.format(stats.getSr()));
				oDBatting.add(twoDecimal.format(stats.getAvg()));
				oDBatting.add(Integer.toString(stats.getHs()));
				oDBatting.add(Integer.toString(stats.getHunds()));
				oDBatting.add(Integer.toString(stats.getFifs()));
				if(!isMinStats){
					oDBatting.add(Integer.toString(stats.getThirts()));
					oDBatting.add(Integer.toString(stats.getSixes()));
					oDBatting.add(Integer.toString(stats.getFours()));
				}
			}else if(MatchFormatEnum.TEST.getId().equals(stats.getCricketFormat())){
				testBatting.add(Integer.toString(stats.getMatches()));
				if(!isMinStats){
					testBatting.add(Integer.toString(stats.getInn()));
					testBatting.add(Integer.toString(stats.getNo()));
				}
				testBatting.add(Integer.toString(stats.getRuns()));
				testBatting.add(twoDecimal.format(stats.getSr()));
				testBatting.add(twoDecimal.format(stats.getAvg()));
				testBatting.add(Integer.toString(stats.getHs()));
				testBatting.add(Integer.toString(stats.getHunds()));
				testBatting.add(Integer.toString(stats.getFifs()));
				if(!isMinStats){
					testBatting.add(Integer.toString(stats.getThirts()));
					testBatting.add(Integer.toString(stats.getSixes()));
					testBatting.add(Integer.toString(stats.getFours()));
				}
			}
		}
		for(BowlingStatistics stats: bowlingList){
			if(MatchFormatEnum.T20.getId().equals(stats.getCricketFormat())){
				t20Bowling.add(Integer.toString(stats.getMat()));
				if(!isMinStats){
					t20Bowling.add(Integer.toString(stats.getInn()));
					t20Bowling.add(Integer.toString(stats.getBalls()));
				}
				t20Bowling.add(Integer.toString(stats.getWkts()));
				t20Bowling.add(twoDecimal.format(stats.getAvg()));
				t20Bowling.add(twoDecimal.format(stats.getEcon()));
				if(!isMinStats){
					t20Bowling.add(Integer.toString(stats.getRuns()));
					t20Bowling.add(Integer.toString(stats.getThreeWkts()));
				}
				t20Bowling.add(Integer.toString(stats.getFiveWkts()));
				t20Bowling.add(stats.getBestBowling());
				t20Bowling.add(Integer.toString(stats.getCatches()));
				if(!isMinStats){
					t20Bowling.add(Integer.toString(stats.getStumps()));
				}
			}else if(MatchFormatEnum.OD.getId().equals(stats.getCricketFormat())){
				oDBowling.add(Integer.toString(stats.getMat()));
				if(!isMinStats){
					oDBowling.add(Integer.toString(stats.getInn()));
					oDBowling.add(Integer.toString(stats.getBalls()));
				}
				oDBowling.add(Integer.toString(stats.getWkts()));
				oDBowling.add(twoDecimal.format(stats.getAvg()));
				oDBowling.add(twoDecimal.format(stats.getEcon()));
				if(!isMinStats){
					oDBowling.add(Integer.toString(stats.getRuns()));
					oDBowling.add(Integer.toString(stats.getThreeWkts()));
				}
				oDBowling.add(Integer.toString(stats.getFiveWkts()));
				oDBowling.add(stats.getBestBowling());
				oDBowling.add(Integer.toString(stats.getCatches()));
				if(!isMinStats){
					oDBowling.add(Integer.toString(stats.getStumps()));
				}
			}else if(MatchFormatEnum.TEST.getId().equals(stats.getCricketFormat())){
				testBowling.add(Integer.toString(stats.getMat()));
				if(!isMinStats){
					testBowling.add(Integer.toString(stats.getInn()));
					testBowling.add(Integer.toString(stats.getBalls()));
				}
				testBowling.add(Integer.toString(stats.getWkts()));
				testBowling.add(twoDecimal.format(stats.getAvg()));
				testBowling.add(twoDecimal.format(stats.getEcon()));
				if(!isMinStats){
					testBowling.add(Integer.toString(stats.getRuns()));
					testBowling.add(Integer.toString(stats.getThreeWkts()));
				}
				testBowling.add(Integer.toString(stats.getFiveWkts()));
				testBowling.add(stats.getBestBowling());
				testBowling.add(Integer.toString(stats.getCatches()));
				if(!isMinStats){
					testBowling.add(Integer.toString(stats.getStumps()));
				}
			}
		}
		pm.close();
		response.setT20Batting(t20Batting);
		response.setT20Bowling(t20Bowling);
		response.setoDBatting(oDBatting);
		response.setoDBowling(oDBowling);
		response.setTestBatting(testBatting);
		response.setTestBowling(testBowling);
	}

	/**
	 * Used during login. Updates only the picture URL
	 * @param userInfo
	 * @param response
	 * @return
	 */
	public boolean saveAndLogin(UserInfoRequestVo userInfo, UserInfoResponseVo response) {

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
			userInfo.setKey(KeyFactory.keyToString(user.getKey()));
			userInfo.setSignedInUserKey(KeyFactory.keyToString(user.getKey()));
			saveUserInfo(userInfo);
			logger.info("User information has been updated successfully");
		} else {
			logger.info("Creating new user");
			isNewUser = true;
			user = new User();
			
			user.setGivenName(userInfo.getGivenName());
			user.setLastName(userInfo.getLastName());
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
			User insertedUser = userDAO.insert(user, pm);
			pm.currentTransaction().commit();
			createCricketProfile(KeyFactory.keyToString(insertedUser.getKey()));
		}
		String userKey = KeyFactory.keyToString(user.getKey());
		userInfo.setKey(userKey);
		userInfo.setTimeZoneId(user.getTimezone());
		pm.close();
		response.setEmail(user.getEmail());
		response.setUserKey(userKey);
		return isNewUser;
	
	}
	
	/**
	 * Save Cricket Profile
	 * @param request
	 * @param response
	 */
	public void saveCricketProfile(CricketProfileRequestVo request, CricketProfileResponseVo response) {
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketProfileDAO profileDao = new CricketProfileDAO();
		if (request.getCricketProfileKey() != null) {
			Key cricketProfileKey = KeyFactory.stringToKey(request.getCricketProfileKey());
			CricketProfile updateCricketProfile = profileDao.getCricketProfileByKey(cricketProfileKey, pm);
			if (updateCricketProfile != null) {
				updateCricketProfile.setUpdatedTime(Calendar.getInstance().getTime());
				updateCricketProfile.setUpdatedUserKey(KeyFactory.stringToKey(request.getSignedInUserKey()));
				response.setCricketProfileKey(request.getCricketProfileKey());
				if (request.getBattingHandType() != null) {
					updateCricketProfile.setBattingHandType(request.getBattingHandType());
					response.setBattingHandType(request.getBattingHandType());
				}
				if (request.getBattingOrder() != null) {
					updateCricketProfile.setBattingOrder(request.getBattingOrder());
					response.setBattingOrder(request.getBattingOrder());
				}
				if (request.getBowlingHandType() != null) {
					updateCricketProfile.setBowlingHandType(request.getBowlingHandType());
					response.setBowlingHandType(request.getBowlingHandType());
				}
				if (request.getBowlingMethod() != null) {
					updateCricketProfile.setBowlingMethod(request.getBowlingMethod());
					response.setBowlingMethod(request.getBowlingMethod());
				}
				if (request.getFieldPosition() != null) {
					updateCricketProfile.setFieldPosition(request.getFieldPosition());
					response.setFieldPosition(request.getFieldPosition());
				}
				pm.currentTransaction().begin();
				profileDao.update(updateCricketProfile, pm);
				pm.currentTransaction().commit();
			} else {
				ErrorVo error = new ErrorVo();
				error.setErrorId(ErrorConstants.USER_NOT_FOUND);
				error.setErrorMessage("User Not Found");
				List<ErrorVo> errors = new ArrayList<ErrorVo>();
				errors.add(error);
				response.setErrors(errors);
				return;
			}
		}
		pm.close();
	}
	
}
