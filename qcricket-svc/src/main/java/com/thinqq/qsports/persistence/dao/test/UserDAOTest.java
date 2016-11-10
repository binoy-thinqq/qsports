package com.thinqq.qsports.persistence.dao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamPlayersDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamStatusDAO;
import com.thinqq.qsports.persistence.dao.DAOFactory;
import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeam;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamStats;
import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.cricket.TeamPlayerStatusEnum;

public class UserDAOTest {
	private Integer insertTest(Session session, User user) {
		return (Integer)DAOFactory.getUserDAO().insert(session, user);
	}
	
	private User getByPKTest(Session session, int pk){
		return DAOFactory.getUserDAO().getByPrimaryKey(session, pk);
	}
	
	private void updateTest(Session session, User user) {
		user.setCity("Thrissur");
		user.setConfirmationCode("0000");
		user.setContactShown("A");
		user.setCreatedId(user.getUserId());
		user.setCreatedTime(Calendar.getInstance().getTime());
		user.setDob(Calendar.getInstance().getTime());
		user.setEmail("binoybt@gmail.com");
		user.setFirstName("Bin");
		user.setGender("M");
		user.setIsoCountryCode("IN");
		user.setLastName("Baby");
		user.setLocale("en_IN");
		user.setMobileNumber("+91 9840090988");
		user.setNotes("I am a great cricketer");
		user.setPassword("passme");
		user.setPictureUrl("http://binoypickrureurl.com/sdfsdaqewfr/asdfwer");
		user.setRegistrationType("GOOGLE");
		user.setState("Kerala");
		user.setStatus("ACTIVE");
		user.setTimezone("UTC");
		user.setUpdatedId(user.getUserId());
		user.setUpdatedTime(Calendar.getInstance().getTime());
		DAOFactory.getUserDAO().update(session, user);
	}
	
	public static void main(String args[]){
		
		List<User> userIds = new ArrayList<User>();
		List<CricketProfile> profileIds = new ArrayList<CricketProfile>();
		String cities[] = {"Thrissur", "Chennai", "Tirunelveli"};
		/***** User & Cricket Profile Creation - START ****************/
		CricketProfileDAO profiledao = new CricketProfileDAO();
		BattingStatisticsDAO battingStatsDao = new BattingStatisticsDAO();
		BowlingStatisticsDAO bowlingStatsDao = new BowlingStatisticsDAO();
		
		System.out.print(Arrays.asList(TimeZone.getAvailableIDs()));
		UserDAOTest daoTest = new UserDAOTest();
		
		Session session = SessionManager.getSessionFactory().openSession();
		session.getTransaction().begin();
		
		Date createdTime = Calendar.getInstance().getTime();
		for(int i=0;i<120;i++) {
			User user = new User();
			user.setCity("Chennai");
			user.setFirstName("Binoy" + i);
			user.setRegistrationType("GOOGLE");
			user.setLastName("Baby");
			user.setDob(new Date(1982, 6, 17));
			user.setGender("M");
			user.setEmail("binoybt" + i +  "@gmail.com");
			Integer pk = daoTest.insertTest(session, user);
			userIds.add(user);
			CricketProfile cktProfile = createDefaultCricketProfile(createdTime, user);
			cktProfile.setUser(user);
			Integer profileId = profiledao.insert(session, cktProfile);
			profileIds.add(cktProfile);
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
			
		}
		session.getTransaction().commit();
		/***** User & Cricket Profile Creation - END ****************/
		
		/***** Team & Team Player Creation - START *****************/
		
		CricketTeamDAO teamDao = new CricketTeamDAO();
		CricketTeamPlayersDAO cricketTeamPlayerDao = new CricketTeamPlayersDAO();
		CricketTeamStatusDAO cricketTeamStatusDao = new CricketTeamStatusDAO();
		
		for(int i=0;i<10;i++) {
			session.getTransaction().begin();
			CricketTeam team = new CricketTeam();
			team.setCity(cities[i%3]);
			team.setCountry("India");
			team.setCreatedTime(createdTime);
			int start = i * 12;
			int end = start + 11;
			CricketProfile profile = profileIds.get(start);
			User user = userIds.get(start);
			
			team.setCreatedId(profile.getCricketProfileId());
			team.setDescription("Test Team");
			team.setState("TamilNadu");
			team.setTeamName("FXI" + i);
			teamDao.insert(session, team);
			//Team Status Creation
				for(MatchFormatEnum format : MatchFormatEnum.values()) {
					CricketTeamStats teamStatus = new CricketTeamStats();
					teamStatus.setCricketFormat(format.getId());
					teamStatus.setCreatedTime(createdTime);
					teamStatus.setUpdatedTime(createdTime);
					teamStatus.setCricketTeam(team);
					cricketTeamStatusDao.insert(session, teamStatus);
				}
			
			//Team Player Creation
			CricketTeamPlayers teamOwner = new CricketTeamPlayers();
			teamOwner.setCricketProfile(profile);
			teamOwner.setCricketTeam(team);
			teamOwner.setCreatedTime(createdTime);
			teamOwner.setUpdatedId(user.getUserId());
			teamOwner.setUpdatedTime(createdTime);
			teamOwner.setCreatedId(user.getUserId());
			teamOwner.setIsModerator(true);
			teamOwner.setIsActive(true);
			teamOwner.setStatus(TeamPlayerStatusEnum.ACCEPTED.getId().toString());
			cricketTeamPlayerDao.insert(session, teamOwner);
			for (int j=start + 1;j<=end ;j++) {
				CricketProfile profilePlayer = profileIds.get(j);
				User userPlayer = userIds.get(start);
				CricketTeamPlayers teamPlayer = new CricketTeamPlayers();
				teamPlayer.setCricketProfile(profilePlayer);
				teamPlayer.setCricketTeam(team);
				teamPlayer.setCreatedTime(createdTime);
				teamPlayer.setUpdatedId(userPlayer.getUserId());
				teamPlayer.setUpdatedTime(createdTime);
				teamPlayer.setCreatedId(userPlayer.getUserId());
				teamPlayer.setIsModerator(false);
				teamPlayer.setIsActive(true);
				teamPlayer.setStatus(TeamPlayerStatusEnum.ACCEPTED.getId().toString());
				cricketTeamPlayerDao.insert(session, teamPlayer);
			}
			session.getTransaction().commit();
		}
		/***** Team & Team Player Creation - END *****************/
		
		
		session.close();	
	}
	

	private static BattingStatistics createBattingStatistics(int cricketFormat) {
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
	private static BowlingStatistics createBowlingStatistics(int cricketFormat) {
		BowlingStatistics bowlingStats = new BowlingStatistics();
		bowlingStats.setCricketFormat(cricketFormat);
		return bowlingStats;
	}
	
	private static CricketProfile createDefaultCricketProfile(Date createdTime , User user) {
		CricketProfile cricketProfile = new CricketProfile();
		cricketProfile.setWicketKeeper(false);
		cricketProfile.setCreatedId(user.getUserId());
		cricketProfile.setCreatedTime(createdTime);
		cricketProfile.setUpdatedId(user.getUserId());
		cricketProfile.setUpdatedTime(createdTime);
		return cricketProfile;
	}

	private User getByEmailTest(Session session, String email) {
		return DAOFactory.getUserDAO().getByEmail(session, email);
	}
}
