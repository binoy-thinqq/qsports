package com.thinqq.qsports.server.process;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.server.persistence.dao.CricketTeamStatisticsDAO;
import com.thinqq.qsports.server.persistence.dao.TeamProfileMapDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeamStatistics;
import com.thinqq.qsports.server.pesistence.dataobjects.TeamProfileMap;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.cricket.BattingOrder;
import com.thinqq.qsports.shared.cricket.BowlingStyle;
import com.thinqq.qsports.shared.cricket.HandTypeEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.AddTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamPlayerProfileResponseVo.TeamPlayerMinDetails;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamStatisticsResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.GetTeamsForPlayerResponseVo.TeamProfileMinDetails;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerRequestVo;
import com.thinqq.qsports.shared.teamprofile.RemoveTeamPlayerResponseVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.SaveTeamProfileResponseVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileRequestVo;
import com.thinqq.qsports.shared.teamprofile.TeamProfileResponseVo;

public class TeamProfileProcess {
	private static TeamProfileProcess s_instance = new TeamProfileProcess();
	private static DecimalFormat TWO_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	//private Logger logger = Logger.getLogger(TeamProfileProcess.class.getCanonicalName());
	private TeamProfileProcess(){}
	public static TeamProfileProcess getInstance(){
		return s_instance;
	}
	public void getTeamProfile(TeamProfileRequestVo request,TeamProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketTeamDAO teamDAO = new CricketTeamDAO();
		CricketTeam team = teamDAO.findTeamByKey(request.getTeamProfileKey(), pm);
		if(team!=null){
			response.setTeamKey(request.getTeamProfileKey());
			response.setName(team.getName());
			response.setDescription(team.getDescription());
			response.setDateOfEstd(team.getDateOfEstd());
			if(team.getDateOfEstd()!=null){
				response.setDateOfEstdString(DATE_FORMAT.format(team.getDateOfEstd()));
			}
			response.setCity(team.getCity());
			response.setState(team.getState());
			response.setCountry(team.getIsoCountryCode());
			TeamProfileMapDAO teamPlayerDAO = new TeamProfileMapDAO();
			List<TeamProfileMap> ownersMap = teamPlayerDAO.findTeamProfileMapofOwners(team.getKey(), pm);
			List<NameAndKey> owners = new ArrayList<NameAndKey>();
			CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
			UserDAO userDAO = new UserDAO();
			boolean allowEdit = false;
			for(TeamProfileMap ownerMap: ownersMap){
				CricketProfile ownerProfileObj = cricketProfileDAO.getCricketProfileByKey(ownerMap.getProfile(), pm);
				User user = userDAO.findUserByKey(ownerProfileObj.getUser(), pm);
				String userKey = KeyFactory.keyToString(ownerProfileObj.getUser());
				if(userKey.equals(request.getSignedInUserKey())){
					allowEdit = true;
				}
				owners.add(new NameAndKey(user.getGivenName(), userKey,KeyFactory.keyToString(ownerProfileObj.getKey())));
			}
			response.setAllowEdit(allowEdit);
			response.setOwners(owners);
		}else{
			response.setTeamNotFound(true);
		}
		pm.close();
	}
	
	public void getAllTeamPlayers(GetTeamPlayerProfileRequestVo request, GetTeamPlayerProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		TeamProfileMapDAO teamProfileDAO = new TeamProfileMapDAO();
		CricketProfileDAO cricProfileDAO = new CricketProfileDAO();
		BattingStatisticsDAO battingStatsDAO = new BattingStatisticsDAO();
		BowlingStatisticsDAO bowlingStatsDAO = new BowlingStatisticsDAO();
		UserDAO userDAO = new UserDAO();
		List<TeamProfileMap> teamPlayerMapList = teamProfileDAO.getTeamProfilesMapByTeam(request.getTeamKey(), pm);
		List<TeamPlayerMinDetails>  playerDetailsList = new ArrayList<TeamPlayerMinDetails>();
		for(TeamProfileMap teamProfileMap : teamPlayerMapList){
			if(teamProfileMap.getDeleted()!=null && teamProfileMap.getDeleted()){
				continue;
			}
			TeamPlayerMinDetails player = new TeamPlayerMinDetails();
			if(teamProfileMap.getOwner() != null){
				player.setOwner(teamProfileMap.getOwner());
			}
			CricketProfile profile = cricProfileDAO.getCricketProfileByKey(teamProfileMap.getProfile(), pm);
			player.setCricketProfileKey(KeyFactory.keyToString(profile.getKey()));
			User user = userDAO.findUserByKey(profile.getUser(), pm);
			player.setPlayer(new NameAndKey(user.getGivenName(),KeyFactory.keyToString(user.getKey())));
			player.setPictureURL(user.getGooglePictureURL());
			player.setBatting(HandTypeEnum.getById(profile.getBattingHandType()).getScreenName() 
					+ " " + BattingOrder.getById(profile.getBattingOrder()).getScreenName());
			player.setBowling(HandTypeEnum.getById(profile.getBowlingHandType()).getScreenName()
					+ " " + BowlingStyle.getById(profile.getBowlingMethod()).getScreenName());
			List<BattingStatistics> battingList = battingStatsDAO.getBattingStatisticsForCricketProfile(profile.getKey(), pm);
			List<BowlingStatistics> bowlingList = bowlingStatsDAO.getBowlingStatisticsForCricketProfile(profile.getKey(), pm);
			int totalMatches = 0;
			int totalRuns = 0;
			int totalWkts = 0;
			for(BattingStatistics stats: battingList){
				totalMatches += stats.getMatches();
				totalRuns += stats.getRuns();
			}
			for(BowlingStatistics stats: bowlingList){
				totalWkts += stats.getWkts();
			}
			player.setMatches(totalMatches);
			player.setRuns(totalRuns);
			player.setWkts(totalWkts);
			playerDetailsList.add(player);
		}
		response.setPlayersList(playerDetailsList);
		Collections.sort(playerDetailsList, new Comparator<TeamPlayerMinDetails>() {

			@Override
			public int compare(TeamPlayerMinDetails o1, TeamPlayerMinDetails o2) {
				return o1.getPlayer().getDisplayName().compareTo(o2.getPlayer().getDisplayName());
			}
		});
		pm.close();
	}
	public void getTeamStatistics(GetTeamStatisticsRequestVo request, GetTeamStatisticsResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketTeamStatisticsDAO statsDAO = new CricketTeamStatisticsDAO();
		List<CricketTeamStatistics> teamStatsList = statsDAO.getCricketTeamStatisticsByTeamKey(request.getTeamProfileKey(), pm);
		List<String> t20Statistics = new ArrayList<String>();
		List<String> oDStatistics = new ArrayList<String>();
		List<String> testStatistics = new ArrayList<String>();
		int totalMatches = 0;
		int totalWin = 0;
		for(CricketTeamStatistics stats: teamStatsList){
			totalMatches+=stats.getMat();
			totalWin+=stats.getWin();
			if(MatchFormatEnum.T20.getId().equals(stats.getCricketFormat())){
				t20Statistics.add(Integer.toString(stats.getMat()));
				t20Statistics.add(Integer.toString(stats.getWin()));
				t20Statistics.add(Integer.toString(stats.getLoss()));
				t20Statistics.add(Integer.toString(stats.getTied()));
				t20Statistics.add(Integer.toString(stats.getNoResult()));
				t20Statistics.add(Integer.toString(stats.getHs()));
			}else if(MatchFormatEnum.OD.getId().equals(stats.getCricketFormat())){
				oDStatistics.add(Integer.toString(stats.getMat()));
				oDStatistics.add(Integer.toString(stats.getWin()));
				oDStatistics.add(Integer.toString(stats.getLoss()));
				oDStatistics.add(Integer.toString(stats.getTied()));
				oDStatistics.add(Integer.toString(stats.getNoResult()));
				oDStatistics.add(Integer.toString(stats.getHs()));
			}else if(MatchFormatEnum.TEST.getId().equals(stats.getCricketFormat())){
				testStatistics.add(Integer.toString(stats.getMat()));
				testStatistics.add(Integer.toString(stats.getWin()));
				testStatistics.add(Integer.toString(stats.getLoss()));
				testStatistics.add(Integer.toString(stats.getTied()));
				testStatistics.add(Integer.toString(stats.getNoResult()));
				testStatistics.add(Integer.toString(stats.getHs()));
			}
		}
		
		float winPercentage = 0.0f;
		if(totalMatches > 0){
			winPercentage = ((float)totalWin/totalMatches)*100.0f;
		}
		response.setWinPercentage(TWO_DECIMAL_FORMAT.format(winPercentage));
		response.setT20Statistics(t20Statistics);
		response.setoDStatistics(oDStatistics);
		response.setTestStatistics(testStatistics);
		pm.close();
	}
	public void addTeamProfile(AddTeamPlayerRequestVo request,AddTeamPlayerResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		TeamProfileMapDAO teamPlayerDAO = new TeamProfileMapDAO();
		Key signedInUserKey = KeyFactory.stringToKey(request.getSignedInUserKey());
		CricketProfileDAO profileDAO = new CricketProfileDAO();
		CricketProfile ownerProfile = profileDAO.findCricketProfileByUserKey(signedInUserKey, pm);;
		
		Key playerCricProfileKey = KeyFactory.stringToKey(request.getProfileKey());
		Key teamKey = KeyFactory.stringToKey(request.getTeamKey());
		
		boolean isOwner = false;
		if (ownerProfile!=null){
			TeamProfileMap owner = teamPlayerDAO.getTeamProfileMapByTeamAndProfile(ownerProfile.getKey(), teamKey, pm);
			if(owner!=null && owner.getOwner()){
				isOwner = true;
			}
		}
		if(isOwner){
			TeamProfileMap alreadyExists = teamPlayerDAO.getTeamProfileMapByTeamAndProfile(playerCricProfileKey, teamKey, pm);
			if(alreadyExists!=null && request.isAddAsOwner()){
				if(alreadyExists.getOwner() !=null && !alreadyExists.getOwner()){
					alreadyExists.setOwner(true);
					pm.currentTransaction().begin();
					teamPlayerDAO.update(alreadyExists, pm);
					pm.currentTransaction().commit();
					response.setAddedSuccessfully(true);
					return;
				} else {
					response.setAddedSuccessfully(false);
					response.setAlreadyExists(true);
					return;
				}
			}  else if (alreadyExists !=null && alreadyExists.getDeleted()!=null && !alreadyExists.getDeleted()) {
				alreadyExists.setDeleted(false);
				pm.currentTransaction().begin();
				teamPlayerDAO.update(alreadyExists, pm);
				pm.currentTransaction().commit();
				response.setAddedSuccessfully(true);
				return;
			} else if (alreadyExists!=null){
				response.setAddedSuccessfully(false);
				response.setAlreadyExists(true);
				return;
			}
			TeamProfileMap map = new TeamProfileMap();
			map.setProfile(playerCricProfileKey);
			map.setTeam(teamKey);
			map.setOwner(request.isAddAsOwner());
			Date createdTime = Calendar.getInstance().getTime();
			map.setCreatedTime(createdTime);
			map.setCreatedUserKey(signedInUserKey);
			map.setUpdatedTime(createdTime);
			map.setUpdatedUserKey(signedInUserKey);
			pm.currentTransaction().begin();
			teamPlayerDAO.insert(map, pm);
			pm.currentTransaction().commit();
			response.setAddedSuccessfully(true);

		}else{
			response.setAddedSuccessfully(false);
			response.setNoOwnerPrivilege(true);
		}
		pm.close();
	}
	
	public void removeTeamProfile(RemoveTeamPlayerRequestVo request,RemoveTeamPlayerResponseVo response){
		
		PersistenceManager pm = DAO.getPersistenceManager();
		TeamProfileMapDAO teamPlayerDAO = new TeamProfileMapDAO();
		Key signedInUserKey = KeyFactory.stringToKey(request.getSignedInUserKey());
		CricketProfileDAO profileDAO = new CricketProfileDAO();
		CricketProfile ownerProfile = profileDAO.findCricketProfileByUserKey(signedInUserKey, pm);;
		
		Key playerCricProfileKey = KeyFactory.stringToKey(request.getProfileKey());
		Key teamKey = KeyFactory.stringToKey(request.getTeamKey());
		
		boolean isOwner = false;
		if (ownerProfile!=null){
			TeamProfileMap owner = teamPlayerDAO.getTeamProfileMapByTeamAndProfile(ownerProfile.getKey(), teamKey, pm);
			if(owner!=null && owner.getOwner()){
				isOwner = true;
			}
		}
		if(isOwner){
			TeamProfileMap profileMap = teamPlayerDAO.getTeamProfileMapByTeamAndProfile(playerCricProfileKey, teamKey, pm);
			List<TeamProfileMap> ownersMap = teamPlayerDAO.findTeamProfileMapofOwners(teamKey, pm);
			//Do let user remove ownership or delete the the only owner of the team
			boolean cannotDelete= ownersMap.size()==1 && profileMap.getKey().equals(ownersMap.get(0).getKey());
			if(cannotDelete){
				response.setRemovedSuccessfully(false);
				response.setCannotRemoveTheOnlyOwner(true);
				return;
			}
			if(profileMap != null){
				if(request.isRemoveOnlyOwnership()){
					profileMap.setOwner(false);
				}else{
					profileMap.setOwner(false);
					profileMap.setDeleted(true);
				}
				pm.currentTransaction().begin();
				teamPlayerDAO.update(profileMap, pm);
				pm.currentTransaction().commit();
				response.setRemovedSuccessfully(true);
			}
		}else{
			response.setRemovedSuccessfully(false);
			response.setNoOwnerPrivilege(true);
		}
		pm.close();
		
	}
	public void saveTeamProfile(SaveTeamProfileRequestVo request,SaveTeamProfileResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketTeamDAO teamDAO = new CricketTeamDAO();
		CricketTeam team;
		Date createdTime = Calendar.getInstance().getTime();
		if(request.getNewTeam()!=null && request.getNewTeam()){
			//Create Team
			team = new CricketTeam();
			team.setName(request.getName());
			team.setDescription(request.getDescription());
			team.setDateOfEstd(request.getDateOfEstd());
			team.setCity(request.getCity());
			team.setState(request.getState());
			team.setIsoCountryCode(request.getCountry());
			team.setCreatedTime(createdTime);
			team.setCreatedUserKey(KeyFactory.stringToKey(request.getSignedInUserKey()));
			team.setUpdatedTime(createdTime);
			team.setUpdatedUserKey(KeyFactory.stringToKey(request.getSignedInUserKey()));
			pm.currentTransaction().begin();
			teamDAO.insert(team, pm);
			pm.currentTransaction().commit();
			//Create Team Statistics
			CricketTeamStatistics teamStatsT20 = new CricketTeamStatistics(0, 0, 0, 0, 0, 0, null, team.getKey(), MatchFormatEnum.T20.getId(), KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime, KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime);
			CricketTeamStatistics teamStatsOD = new CricketTeamStatistics(0, 0, 0, 0, 0, 0, null, team.getKey(), MatchFormatEnum.OD.getId(), KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime, KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime);
			CricketTeamStatistics teamStatsTest = new CricketTeamStatistics(0, 0, 0, 0, 0, 0, null, team.getKey(), MatchFormatEnum.TEST.getId(), KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime, KeyFactory.stringToKey(request.getSignedInUserKey()), createdTime);
			CricketTeamStatisticsDAO teamStatsDAO = new CricketTeamStatisticsDAO();
			pm.currentTransaction().begin();
			teamStatsDAO.insert(teamStatsT20, pm);
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			teamStatsDAO.insert(teamStatsOD, pm);
			pm.currentTransaction().commit();
			pm.currentTransaction().begin();
			teamStatsDAO.insert(teamStatsTest, pm);
			pm.currentTransaction().commit();
			//Add Owner as a Team Player and Owner
			TeamProfileMapDAO teamPlayerDAO = new TeamProfileMapDAO();
			TeamProfileMap map = new TeamProfileMap();
			CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
			CricketProfile profile = cricketProfileDAO.findCricketProfileByUserKey(request.getSignedInUserKey(), pm);
			map.setProfile(profile.getKey());
			map.setTeam(team.getKey());
			map.setOwner(true);
			map.setCreatedTime(createdTime);
			map.setCreatedUserKey(KeyFactory.stringToKey(request.getSignedInUserKey()));
			map.setUpdatedTime(createdTime);
			map.setUpdatedUserKey(KeyFactory.stringToKey(request.getSignedInUserKey()));
			pm.currentTransaction().begin();
			teamPlayerDAO.insert(map, pm);
			pm.currentTransaction().commit();
			response.setSavedSuccessfully(true);
			response.setTeamProfileKey(KeyFactory.keyToString(team.getKey()));
		}else if(request.getTeamKey()!=null){

			team = teamDAO.findTeamByKey(request.getTeamKey(), pm);
			team.setName(request.getName());
			team.setDescription(request.getDescription());
			team.setDateOfEstd(request.getDateOfEstd());
			team.setCity(request.getCity());
			team.setState(request.getState());
			team.setIsoCountryCode(request.getCountry());
			pm.currentTransaction().begin();
			teamDAO.update(team, pm);
			pm.currentTransaction().commit();
			response.setSavedSuccessfully(true);
			response.setTeamProfileKey(KeyFactory.keyToString(team.getKey()));
		}else{
			response.setSavedSuccessfully(false);
		}

		pm.close();
	}
	
	public void getAllTeamsForPlayer(GetTeamsForPlayerRequestVo request, GetTeamsForPlayerResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		TeamProfileMapDAO teamProfileDAO = new TeamProfileMapDAO();
		CricketTeamDAO teamDAO = new CricketTeamDAO();
		CricketTeamStatisticsDAO statsDAO = new CricketTeamStatisticsDAO();
		List<TeamProfileMap> teamProfileList = teamProfileDAO.getTeamProfileMapByProfile(request.getCricketProfileKey(), pm);
		List<TeamProfileMinDetails> teamDetailsList = new ArrayList<TeamProfileMinDetails>();
		if(teamProfileList != null){
			for(TeamProfileMap teamProfile: teamProfileList){
				if(request.isGetOwnedTeamsOnly() && (teamProfile.getOwner()==null || !teamProfile.getOwner())){
					//SKIP Teams that not owned by the player, If only owned teams are requested.
					continue;
				}
				TeamProfileMinDetails teamData = new TeamProfileMinDetails();
				CricketTeam team = teamDAO.findTeamByKey(teamProfile.getTeam(), pm);
				teamData.setName(team.getName());
				teamData.setTeamKey(KeyFactory.keyToString(team.getKey()));
				teamData.setCityState(team.getCity()+", "+team.getState());
				teamData.setCountry(team.getIsoCountryCode());
				List<CricketTeamStatistics> teamStatsList = statsDAO.getCricketTeamStatisticsByTeamKey(team.getKey(), pm);
				int totalMatches = 0;
				int totalWon = 0;
				int totalLost = 0;
				for(CricketTeamStatistics stats: teamStatsList){
					totalMatches+=stats.getMat();
					totalWon+=stats.getWin();
					totalLost+=stats.getLoss();
				}
				teamData.setMatches(totalMatches);
				teamData.setWon(totalWon);
				teamData.setLost(totalLost);
				float winPercentage = 0.0f;
				if(totalMatches > 0){
					winPercentage = ((float)totalWon/totalMatches)*100.0f;
				}
				teamData.setWinPercent(TWO_DECIMAL_FORMAT.format(winPercentage));
				teamDetailsList.add(teamData);
			}
		}
		response.setTeams(teamDetailsList);
		pm.close();
	}
}
