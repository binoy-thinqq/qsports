package com.thinqq.qsports.server.process;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.ServerGenie;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.FieldingPosition;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

public class UserProfileProcess {
	private static UserProfileProcess s_instance = new UserProfileProcess();
	private static final Logger logger = Logger.getLogger(UserProfileProcess.class.getName());
	private static DecimalFormat twoDecimal = new DecimalFormat("#0.00");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	private UserProfileProcess(){}
	public static UserProfileProcess getInstance(){
		return s_instance;
	}
	public void getUserProfile(UserProfileRequestVo request, UserProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		User user;
		if(request.isUseOnlyEmail() && request.getEmail()!=null){
			user = userDAO.findUserByEmail(request.getEmail(),pm);
		} else {
			user = userDAO.findUserByKey(request.getUserKey(), pm);
		}
		if(user == null){
			response.setUserNotFound(true);
			return;
		}
		response.setPictureURL(user.getGooglePictureURL());
		response.setName(user.getDisplayName());
		response.setSex(user.getGender());
		response.setEmail(user.getEmail());
		response.setUserKey(request.getUserKey());
		DATE_FORMAT.setTimeZone(ServerGenie.getTimeZone(request.getTimeZone()));
		if(user.getDob()!=null){
			response.setDobString(DATE_FORMAT.format(user.getDob()));
		}
		response.setDob(user.getDob());
		response.setSex(user.getGender());
		response.setCity(user.getCity());
		response.setState(user.getState());
		response.setIsoCountryCode(user.getIsoCountryCode());
		response.setTimeZone(user.getTimezone());
		CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
		CricketProfile cricProfile = cricketProfileDAO.findCricketProfileByUserKey(KeyFactory.keyToString(user.getKey()),pm);
		response.setCricketProfileKey(KeyFactory.keyToString(cricProfile.getKey()));
		response.setBattingHandType(cricProfile.getBattingHandType());
		response.setBattingOrder(cricProfile.getBattingOrder());
		response.setBowlingHandType(cricProfile.getBowlingHandType());
		response.setBowlingMethod(cricProfile.getBowlingMethod());
		response.setFieldPosition(cricProfile.getFieldPosition());
		
		if(request.getSignedInUserKey().equals(KeyFactory.keyToString(user.getKey()))){
			response.setEditable(true);
		}

		pm.close();
	}
	public void saveUserProfile(SaveUserProfileRequestVo request, SaveUserProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findUserByKey(request.getSignedInUserKey(), pm);
		user.setDisplayName(request.getName());
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
		response.setSavedUsername(user.getDisplayName());
		response.setSavedUserTimeZoneId(user.getTimezone());
		pm.close();
	}
	public void createCricketProfile(String userKey){
		
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

	}
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


}
