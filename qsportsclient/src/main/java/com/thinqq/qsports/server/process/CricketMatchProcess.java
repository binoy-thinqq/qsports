package com.thinqq.qsports.server.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.ServerGenie;
import com.thinqq.qsports.server.match.CricketMatchWrapper;
import com.thinqq.qsports.server.match.MatchStateMachine;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.CricketMatchDAO;
import com.thinqq.qsports.server.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.server.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.server.persistence.dao.TeamProfileMapDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketMatch;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;
import com.thinqq.qsports.server.pesistence.dataobjects.TeamProfileMap;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.IdUtil;
import com.thinqq.qsports.shared.MatchStatus;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.cricket.TossChoiceEnum;
import com.thinqq.qsports.shared.match.GetMatchDetailsRequestVo;
import com.thinqq.qsports.shared.match.GetMatchDetailsResponseVo;
import com.thinqq.qsports.shared.match.MatchStateChangeRequestVo;
import com.thinqq.qsports.shared.match.MatchStateChangeResponseVo;
import com.thinqq.qsports.shared.match.SaveMatchRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchResponseVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamRequestVo;
import com.thinqq.qsports.shared.match.SaveMatchTeamResponseVo;
import com.thinqq.qsports.shared.validation.ErrorRepository;
import com.thinqq.qsports.shared.validation.ErrorVo;

public class CricketMatchProcess {
	private static CricketMatchProcess s_instance = new CricketMatchProcess();
	//private static DecimalFormat TWO_DECIMAL_FORMAT = new DecimalFormat("#0.00");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	private CricketMatchProcess(){}
	public static CricketMatchProcess getInstance(){
		return s_instance;
	}

	public void saveMatch(SaveMatchRequestVo request, SaveMatchResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketMatchDAO matchDAO = new CricketMatchDAO();
		String matchKey = request.getMatchKey();
		if(matchKey != null){ //Save match

			CricketMatch match = matchDAO.findMatchByKey(matchKey, pm);
			if(!isMatchOwner(match, request.getSignedInUserKey())){
				return;
			}

			match.setTeam1(request.getOwnTeamKey());
			match.setTeam2(request.getOpponentTeamKey());
			Date date = request.getMatchDate();
			match.setDate(ServerGenie.convertDateToUTC(date, ServerGenie.getTimeZone(request.getTimeZone())));
			match.setFormat(request.getMatchFormat());
			match.setVenue(request.getGroundName());
			match.setCity(request.getCity());
			match.setState(request.getState());
			match.setCountry(request.getCountryCode());
			Date createdDate = Calendar.getInstance().getTime();
			Key userKey = KeyFactory.stringToKey(request.getSignedInUserKey());
			match.setUpdatedTime(createdDate);
			match.setUpdatedUserKey(userKey);
			match.setVersion(match.getVersion()+1);
			pm.currentTransaction().begin();
			matchDAO.update(match, pm);
			pm.currentTransaction().commit();
			response.setMatchKey(KeyFactory.keyToString(match.getKey()));
			response.setMatchSavedSuccessfully(true);

		} else { //Create New Match
			CricketMatch match = new CricketMatch();
			match.setTeam1(request.getOwnTeamKey());
			match.setTeam2(request.getOpponentTeamKey());
			Date date = request.getMatchDate();
			match.setDate(ServerGenie.convertDateToUTC(date, ServerGenie.getTimeZone(request.getTimeZone())));
			match.setFormat(request.getMatchFormat());
			match.setVenue(request.getGroundName());
			match.setCity(request.getCity());
			match.setState(request.getState());
			match.setCountry(request.getCountryCode());
			match.setStatus(MatchStatus.PLANNED.getId());
			Date createdDate = Calendar.getInstance().getTime();
			Key userKey = KeyFactory.stringToKey(request.getSignedInUserKey());
			match.setCreatedTime(createdDate);
			match.setCreatedUserKey(userKey);
			match.setUpdatedTime(createdDate);
			match.setUpdatedUserKey(userKey);
			match.setMatchOwner(userKey);
			match.setVersion(1);
			pm.currentTransaction().begin();
			matchDAO.insert(match, pm);
			pm.currentTransaction().commit();
			response.setMatchKey(KeyFactory.keyToString(match.getKey()));
			response.setMatchSavedSuccessfully(true);
		}
		pm.close();
	}

	public void saveMatchTeamDetails(SaveMatchTeamRequestVo request, SaveMatchTeamResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		Key signedInUserKey = KeyFactory.stringToKey(request.getSignedInUserKey());
		String matchKey = request.getMatchKey();
		String teamKey = request.getTeamKey();
		List<String> players = request.getPlayers();
		CricketMatchDAO matchDAO = new CricketMatchDAO();
		CricketTeamDAO cricketTeamDAO = new CricketTeamDAO();
		UserDAO userDAO = new UserDAO();
		TeamProfileMapDAO teamPlayerMapDAO = new TeamProfileMapDAO();
		CricketProfileDAO profileDAO = new CricketProfileDAO();
		CricketMatch match = matchDAO.findMatchByKey(matchKey, pm);
		if (match != null) {
			// Get Team Object
			CricketTeam team = null;
			if (teamKey != null && !IdUtil.isJustName(teamKey)) {
				team = cricketTeamDAO.findTeamByKey(teamKey, pm);
			}
			List<String> playersList = new ArrayList<String>();
			for (String playerKey : players) {

				User player = null;
				if(!IdUtil.isJustName(playerKey)){
					player  = userDAO.findUserByKey(playerKey, pm);
				} else {
					playersList.add(playerKey);
					continue;
				}

				if (team != null && player != null) {
					CricketProfile profile = profileDAO.findCricketProfileByUserKey(player.getKey(), pm);
					// Check if the player is part of the team,
					TeamProfileMap teamPlayermap = teamPlayerMapDAO.getTeamProfileMapByTeamAndProfile(profile.getKey(), team.getKey(), pm);
					// If not, add player to the team
					if(teamPlayermap == null){
						TeamProfileMap map = new TeamProfileMap();
						map.setProfile(profile.getKey());
						map.setTeam(team.getKey());
						map.setOwner(false);
						Date createdTime = Calendar.getInstance().getTime();
						map.setCreatedTime(createdTime);
						map.setCreatedUserKey(signedInUserKey);
						map.setUpdatedTime(createdTime);
						map.setUpdatedUserKey(signedInUserKey);
						pm.currentTransaction().begin();
						teamPlayerMapDAO.insert(map, pm);
						pm.currentTransaction().commit();
					}

				}
				boolean isPlayerInOtherTeam = false;
				if(player != null){
					// Check if the player is part of the other team !
					List<String> teamPlayersList = null;
					if(teamKey.equals(match.getTeam1())){
						teamPlayersList = match.getTeam2Players();
					} else if(teamKey.equals(match.getTeam2())){
						teamPlayersList = match.getTeam1Players();
					}
					if(teamPlayersList != null){
						isPlayerInOtherTeam = teamPlayersList.contains(playerKey);
					}
				}
				if(!isPlayerInOtherTeam){
					playersList.add(playerKey);
				}
			}
			if(teamKey.equals(match.getTeam1())){
				match.setTeam1Players(playersList);
			} else if(teamKey.equals(match.getTeam2())){
				match.setTeam2Players(playersList);
			}
			pm.currentTransaction().begin();
			matchDAO.update(match, pm);
			pm.currentTransaction().commit();
		}
		pm.close();

	}

	public void changeMatchState(MatchStateChangeRequestVo request,
			MatchStateChangeResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketMatchDAO matchDAO = new CricketMatchDAO();
		String matchKey = request.getMatchKey();
		int stateId = request.getStateId();
		String userKey = request.getSignedInUserKey();
		List<ErrorVo> errors = new ArrayList<ErrorVo>();
		if(matchKey != null){
			CricketMatch match = matchDAO.findMatchByKey(matchKey, pm);
			if(match != null){
				//Do Match Owner check
				boolean isMatchOwner = isMatchOwner(match, userKey);
				if(isMatchOwner){
					String tossWonTeamKey = request.getTossWonTeamKey();
					int tossWonBy = request.getTossChoiceTaken();
					if(tossWonTeamKey != null){
						if(match.getTeam1().equals(tossWonTeamKey)){
							match.setTossWonByTeamKey(tossWonTeamKey);
							if(tossWonBy == TossChoiceEnum.BAT_FIRST.getId()){
								match.setBattingFirstTeamKey(tossWonTeamKey);
							} else if(tossWonBy == TossChoiceEnum.BOWL_FIRST.getId()){
								match.setBattingFirstTeamKey(match.getTeam2());
							}
						} else if(match.getTeam2().equals(tossWonTeamKey)){
							match.setTossWonByTeamKey(tossWonTeamKey);
							if(tossWonBy == TossChoiceEnum.BAT_FIRST.getId()){
								match.setBattingFirstTeamKey(tossWonTeamKey);
							} else if(tossWonBy == TossChoiceEnum.BOWL_FIRST.getId()){
								match.setBattingFirstTeamKey(match.getTeam1());
							}
						} else {
							errors.add(ErrorRepository.oppsSomethingBroke);
							return;
						}
					} else {
						errors.add(ErrorRepository.oppsSomethingBroke);
						return;
					}


					CricketMatchWrapper matchWrapper = new CricketMatchWrapper(match);
					errors = MatchStateMachine.getInstance().gotoState(matchWrapper, stateId);
					response.setMatchStateChangedSuccessfully(errors == null || errors.isEmpty());
					response.setErrors(errors);
				} else {
					errors.add(ErrorRepository.oppsSomethingBroke);
					response.setErrors(errors);
				}
			}
		}
		pm.close();
	}

	public boolean isMatchOwner(CricketMatch match, String userKey){
		Key signedInUserKey = KeyFactory.stringToKey(userKey);
		if(match.getMatchOwner() != null){
			if(match.getMatchOwner().equals(signedInUserKey)){
				return true;
			}
		} 
		return false;
	}

	public void getMatchDetails(GetMatchDetailsRequestVo request, GetMatchDetailsResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketMatchDAO matchDAO = new CricketMatchDAO();
		CricketTeamDAO teamDAO = new CricketTeamDAO();
		String matchKey = request.getMatchKey();
		Key team1Key = null;
		Key team2Key = null;
		if(matchKey != null){
			response.setMatchKey(matchKey);
			CricketMatch match = matchDAO.findMatchByKey(matchKey, pm);
			response.setMatchFormat(match.getFormat());
			if(!IdUtil.isJustName(match.getTeam1())){
				CricketTeam team = teamDAO.findTeamByKey(match.getTeam1(), pm);
				response.setTeam1(new NameAndKey(team.getName(),match.getTeam1()));
				team1Key = KeyFactory.stringToKey(match.getTeam1());
			} else {
				response.setTeam1(new NameAndKey(match.getTeam1(), null));
			}
			if(!IdUtil.isJustName(match.getTeam2())){
				CricketTeam team = teamDAO.findTeamByKey(match.getTeam2(), pm);
				response.setTeam2(new NameAndKey(team.getName(),match.getTeam2()));
				team2Key = KeyFactory.stringToKey(match.getTeam2());
			} else {
				response.setTeam2(new NameAndKey(match.getTeam2(), null));
			}
			Date localisedDate = ServerGenie.convertDateFromUTC(match.getDate(), 
					TimeZone.getTimeZone(request.getTimeZone()));
			response.setDate(DATE_FORMAT.format(localisedDate));
			response.setGround(match.getVenue());
			response.setCity(match.getCity());
			response.setState(match.getState());
			response.setCountry(match.getCountry());
			response.setVersion(match.getVersion());

			//Check if logged in user is owner of the match
			Key signedInUserKey = KeyFactory.stringToKey(request.getSignedInUserKey());
			if(match.getMatchOwner() != null){
				UserDAO userDAO = new UserDAO();
				User user = userDAO.findUserByKey(match.getMatchOwner(), pm);
				response.setMatchOwnerKey(new NameAndKey(user.getDisplayName(), KeyFactory.keyToString(user.getKey()),user.getEmail()));
				if(match.getMatchOwner().equals(signedInUserKey)){
					response.setMatchEditAllowed(true);
				}
			}
			//Check if signed in user is a Team Owner
			CricketProfileDAO cricketProfileDAO = new CricketProfileDAO();
			TeamProfileMapDAO tpMapDAO = new TeamProfileMapDAO();
			CricketProfile profileObject = cricketProfileDAO.findCricketProfileByUserKey(signedInUserKey, pm);
			if(team1Key != null){
				TeamProfileMap tpMap = tpMapDAO.getTeamProfileMapByTeamAndProfile(profileObject.getKey(),team1Key, pm);
				response.setTeam1EditAllowed(tpMap.getOwner()!= null && tpMap.getOwner());
			}
			if(team2Key != null){
				TeamProfileMap tpMap = tpMapDAO.getTeamProfileMapByTeamAndProfile(profileObject.getKey(),team2Key, pm);
				response.setTeam2EditAllowed(tpMap.getOwner()!= null && tpMap.getOwner());
			}

			//Set Team Players Name and Key
			List<NameAndKey> team1Players = new ArrayList<NameAndKey>(15);
			if(match.getTeam1Players() != null){
				for(String player: match.getTeam1Players()){
					if(IdUtil.isJustName(player)){
						team1Players.add(new NameAndKey(player, null));
					} else {
						UserDAO userDAO = new UserDAO();
						User user = userDAO.findUserByKey(player, pm);
						team1Players.add(new NameAndKey(user.getDisplayName(), 
								KeyFactory.keyToString(user.getKey()), user.getEmail()));
					}
				}
			}
			response.setTeam1Players(team1Players);

			List<NameAndKey> team2Players = new ArrayList<NameAndKey>(15);
			if(match.getTeam2Players() != null){
				for(String player: match.getTeam2Players()){
					if(IdUtil.isJustName(player)){
						team2Players.add(new NameAndKey(player, null));
					} else {
						UserDAO userDAO = new UserDAO();
						User user = userDAO.findUserByKey(player, pm);
						team2Players.add(new NameAndKey(user.getDisplayName(), 
								KeyFactory.keyToString(user.getKey()), user.getEmail()));
					}
				}
			}
			response.setTeam2Players(team2Players);
			CricketMatchWrapper matchWrapper = new CricketMatchWrapper(match);
			//Check eligibility to start match
			List<ErrorVo> errors = MatchStateMachine.getInstance().isEligible(matchWrapper, MatchStatus.STARTED.getId());
			response.setEligibleToStart(errors == null || errors.isEmpty());
			response.setErrors(errors);
		}
		pm.close();
	}
}
