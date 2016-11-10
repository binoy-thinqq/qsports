package com.thinqq.qsports.server.process;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.BattingScorecardEntryDAO;
import com.thinqq.qsports.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.BowlingScorecardEntryDAO;
import com.thinqq.qsports.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.CricketMatchDAO;
import com.thinqq.qsports.persistence.dao.CricketMatchExtDAO;
import com.thinqq.qsports.persistence.dao.CricketMatchTeamPlayersDAO;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamPlayersDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamStatusDAO;
import com.thinqq.qsports.persistence.dao.InningsDAO;
import com.thinqq.qsports.persistence.dataobjects.BattingScorecardEntry;
import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.persistence.dataobjects.BowlingScorecardEntry;
import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.persistence.dataobjects.CricketMatch;
import com.thinqq.qsports.persistence.dataobjects.CricketMatchExt;
import com.thinqq.qsports.persistence.dataobjects.CricketMatchTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeam;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamStats;
import com.thinqq.qsports.persistence.dataobjects.Innings;
import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.AddPlayerToMatchResponse;
import com.thinqq.qsports.persistence.dto.BaseResponseVo;
import com.thinqq.qsports.persistence.dto.BattingScorecardEntryResponse;
import com.thinqq.qsports.persistence.dto.BattingScorecardEntryVo;
import com.thinqq.qsports.persistence.dto.BowlingScorecardEntryResponse;
import com.thinqq.qsports.persistence.dto.BowlingScorecardEntryVo;
import com.thinqq.qsports.persistence.dto.CricketMatchResponseVo;
import com.thinqq.qsports.persistence.dto.CricketMatchTeamPlayersRespVo;
import com.thinqq.qsports.persistence.dto.CricketMatchVo;
import com.thinqq.qsports.persistence.dto.CricketTeamPlayerMatchVo;
import com.thinqq.qsports.persistence.dto.InningsMinResponseVo;
import com.thinqq.qsports.persistence.dto.InningsResponseVo;
import com.thinqq.qsports.persistence.dto.MatchInningsDetailsResponse;
import com.thinqq.qsports.persistence.dto.ReviseMatchTargetVo;
import com.thinqq.qsports.persistence.dto.TossInfoVo;
import com.thinqq.qsports.persistence.validation.BasicValidatorAdapter;
import com.thinqq.qsports.persistence.validation.BattingScorecardEntryValidator;
import com.thinqq.qsports.persistence.validation.BowlingScorecardEntryValidator;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.InvalidRequestArgumentException;
import com.thinqq.qsports.server.error.QSportsServiceException;
import com.thinqq.qsports.server.error.UnauthorizedException;
import com.thinqq.qsports.shared.MatchStatus;
import com.thinqq.qsports.shared.OutTypeEnum;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.util.ObjectTransformUtil;

public class CricketMatchProcess {
	
	private static final String MATCH_COMPLETE = "Match Complete";
	
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 11;
	
	private static final int MINIMUM_NUMBER_OF_PLAYERS = 6;
	
	private static final int ELECTED_TO_BAT = 0;
	
	private static final int ELECTED_TO_BOWL = 1;
	
	private static final int TEAM_1_CONSTANT = 1;
	
	private static final int TEAM_2_CONSTANT = 2;

	private Logger logger = Logger.getLogger(CricketMatchProcess.class
			.getName());
	
	@Autowired
	CricketTeamDAO cricketTeamDao;
	
	@Autowired
	CricketMatchDAO cricketMatchDao;
	
	@Autowired
	InningsDAO inningsDao;
	
	@Autowired
	CricketProfileDAO cricketProfileDao;
	
	@Autowired
	CricketTeamPlayersDAO cricketTeamPlayerDao;
	
	@Autowired
	CricketMatchTeamPlayersDAO cricketMatchTeamPlayerDao;
	
	@Autowired
	BowlingScorecardEntryDAO bowlingEntryDao;
	
	@Autowired
	BattingScorecardEntryDAO battingEntryDao;
	
	@Autowired
	BattingStatisticsDAO battingStatsDao;
	
	@Autowired
	BowlingStatisticsDAO bowlingStatsDao;
	
	@Autowired
	CricketTeamStatusDAO teamStatsDao;
	
	@Autowired
	CricketMatchExtDAO cricketMatchExtDao;
	
	
	BasicValidatorAdapter validator;
	
	/**
	 * Create a new Team.
	 * 
	 * @param cricketTeamVo
	 */
	public void saveMatch(CricketMatchVo matchVo, CricketMatchResponseVo response) throws QSportsServiceException, UnauthorizedException {
		Session session = null;
		Date currentDate = Calendar.getInstance().getTime();
		boolean isNew = false;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			if (matchVo.getMatchId() != null && matchVo.getMatchId() > 0) {
				match = cricketMatchDao.getByPrimaryKey(session, matchVo.getMatchId());
				if (match == null) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
					errorInfo.setErrorDesc("Match Doesnt exist");
					response.getErrors().add(errorInfo);
					return;
				} 
				ObjectTransformUtil.transform(matchVo, match);
			} else {
				match = new CricketMatch();
				match.setMatchStatus(MatchStatus.PLANNED.getId());
				ObjectTransformUtil.transform(matchVo, match);
				isNew = true;
				match.setCreatedId(matchVo.getSignedInUserId());
				match.setCreatedTime(currentDate);
			}
			if (MatchStatus.PLANNED.getId() != match.getMatchStatus()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_STARTED);
				errorInfo.setErrorDesc("Match Cannot be edited as it is already started/aborted");
				response.getErrors().add(errorInfo);
				return;
			}
			validateMatchBasicRequirement(matchVo, response, match, session);
			if(!response.getErrors().isEmpty()) {
				return;
			}
			session.beginTransaction();
			CricketMatchExt matchExtension = null;
			if(isNew) {
				cricketMatchDao.insert(session, match);
			} else {
				match.setUpdatedId(matchVo.getSignedInUserId());
				match.setUpdatedTime(currentDate);
				cricketMatchDao.update(session, match);
				 matchExtension = cricketMatchExtDao.getByMatchId(session, match.getMatchId());
				
			}
			saveMatchExtInfo(matchExtension, matchVo, match, session, isNew, currentDate);
			session.getTransaction().commit();
			ObjectTransformUtil.transform(match, response);
			response.setTeamId(match.getTeam().getTeamId());
			response.setTeamName(match.getTeam().getTeamName());
			if (match.getOppTeam() != null) {
				response.setOppTeamId(match.getOppTeam().getTeamId());
				response.setOppTeamName(match.getOppTeam().getTeamName());
			} else {
				response.setOppTeamName(match.getOppTeamName());
			}
			
			ObjectTransformUtil.transformGivenFields(matchVo, response, true, CricketMatchExt.class.getDeclaredFields());
		} catch(UnauthorizedException uae){
			throw uae;
		}catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Internal Error occured" + e.getMessage());
			response.getErrors().add(errorInfo);
		} finally {
			closeSession(session);
		}
	}
	
	/**
	 * Save ExtensionInfo if present
	 * @param matchExtension
	 * @param matchVo
	 * @param session
	 * @param isNew
	 */
	private void saveMatchExtInfo(CricketMatchExt matchExtension, CricketMatchVo matchVo, CricketMatch match, Session session, boolean isNew, Date currentDate) {
		 if (matchExtension == null) {
			 matchExtension = new CricketMatchExt();
			 matchExtension.setCreatedId(matchVo.getSignedInUserId());
			 matchExtension.setCreatedTime(currentDate);
		 }
		 matchExtension.setUpdatedId(matchVo.getSignedInUserId());
		 matchExtension.setUpdatedTime(currentDate);
		ObjectTransformUtil.transformGivenFields(matchVo, matchExtension, true, CricketMatchExt.class.getDeclaredFields());
		if (matchExtension.getGroundCondition() == null && matchExtension.getPitchType() == null && matchExtension.getMatchOfficials() == null
				&& matchExtension.getWeatherCondition() == null) {
			return;
		}
		matchExtension.setMatch(match);
		if(isNew) {
			cricketMatchExtDao.insert(session, matchExtension);
		} else {
			cricketMatchExtDao.update(session, matchExtension);
		}
	}
	/**
	 * Validate
	 * @param matchVo
	 * @param response
	 * @param session
	 */
	public void validateMatchBasicRequirement(CricketMatchVo matchVo, BaseResponseVo response, CricketMatch match, Session session) throws UnauthorizedException{
		if (matchVo.getTeamId() == null || matchVo.getTeamId() < 0) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.MATCH_TEAMS_NOT_DEFINED);
			errorInfo.setErrorDesc("Please define team and oppTeam");
			response.getErrors().add(errorInfo);
		}
		CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, matchVo.getSignedInUserId());
		CricketTeamPlayers myTeam = cricketTeamPlayerDao.getByProfileIdForTeam(session, cricketProfile.getCricketProfileId(), matchVo.getTeamId());
		if (myTeam == null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.MATCH_TEAMS_NOT_DEFINED);
			errorInfo.setErrorDesc("The field My Team should be owned by the singed in user");
			response.getErrors().add(errorInfo);
			return;
		}
		if (!myTeam.getIsModerator()) {
			throw new UnauthorizedException("Only Team moderator can create/update match for that team");
		}
		match.setTeam(myTeam.getCricketTeam());
		if (matchVo.getOppTeamId() != null && matchVo.getOppTeamId() > 0) {
			CricketTeam oppTeam = cricketTeamDao.getByPrimaryKey(session, matchVo.getOppTeamId());
			if (oppTeam == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_TEAMS_NOT_DEFINED);
				errorInfo.setErrorDesc("Please define team and oppTeam");
				response.getErrors().add(errorInfo);
				return;
			}
			match.setOppTeam(oppTeam);
			match.setOppTeamName(null);
		} else if(!StringUtils.isEmpty(matchVo.getOppTeamName())){
			match.setOppTeamName(matchVo.getOppTeamName());
			match.setOppTeam(null);
		} else {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.MATCH_TEAMS_NOT_DEFINED);
			errorInfo.setErrorDesc("Please define team and oppTeam");
			response.getErrors().add(errorInfo);
			return;
		}
	}
	
	/**
	 * Remove the player from the match
	 * @param removePlayerId
	 * @param response
	 * @param signedInUserId
	 * @throws UnauthorizedException
	 */
	public void removePlayerFromMatch(Integer removePlayerId, BaseResponseVo response, Integer signedInUserId) throws UnauthorizedException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			
			CricketMatchTeamPlayers playerToRemove = cricketMatchTeamPlayerDao.getByPrimaryKey(session, removePlayerId);
			if (playerToRemove == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
				errorInfo.setErrorDesc("No Team with given ID found");
				response.getErrors().add(errorInfo);
				return;
			}
			CricketMatch match = playerToRemove.getMatch();
			//Validate if authorized to add players
			validateIsAuthorized(session, match, signedInUserId);
			if (match.getMatchStatus() != MatchStatus.PLANNED.getId()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_STARTED);
				errorInfo.setErrorDesc("Players cannot be deleted once match is started");
				response.getErrors().add(errorInfo);
				return;
			}
			session.beginTransaction();
			cricketMatchTeamPlayerDao.delete(session, playerToRemove);
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}
	
	/**
	 * Add a player to the match
	 * @param playerToMatch
	 * @param response
	 * @throws UnauthorizedException
	 */
	public void addPlayerToMatch(CricketTeamPlayerMatchVo playerToMatch, AddPlayerToMatchResponse response) throws UnauthorizedException {
		Session session = null;
		Date currentDate = Calendar.getInstance().getTime();
		try {
			session = SessionManager.getSessionFactory().openSession();
			
			//Validate if basic request parameters are available
			validatePlayerMatchRequest(playerToMatch, response);
			if (!response.getErrors().isEmpty()) {
				return;
			}
			
			CricketMatchTeamPlayers player = new CricketMatchTeamPlayers();
			
			//Validate if match exists
			CricketMatch cricketMatch = cricketMatchDao.getByPrimaryKey(session, playerToMatch.getMatchId());
			if (cricketMatch == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			}
			player.setMatch(cricketMatch);
			//Validate if authorized to add players
			validateIsAuthorized(session, cricketMatch, playerToMatch.getSignedInUserId());
			
			if (cricketMatch.getMatchStatus() != MatchStatus.PLANNED.getId()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_STARTED);
				errorInfo.setErrorDesc("Player cannot be added once match is started");
				response.getErrors().add(errorInfo);
				return;
			}
			CricketTeam team = null;
			Integer teamId = 0;
			if (playerToMatch.getTeamId() != null && playerToMatch.getTeamId() > 0) {
				//Case 1: Team Id is available
					//Check if Team is part of the match and proceed
				team = cricketTeamDao.getByPrimaryKey(session,	playerToMatch.getTeamId());
				if (team == null) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
					errorInfo.setErrorDesc("No Team with given ID found");
					response.getErrors().add(errorInfo);
					return;
				}
				
				if(!(cricketMatch.getTeam().getTeamId().equals(team.getTeamId())
					|| cricketMatch.getOppTeam().getTeamId().equals(team.getTeamId()))){
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
					errorInfo.setErrorDesc("This team is not part of the match");
					response.getErrors().add(errorInfo);
					return;
				}
				player.setTeam(team);
				teamId = team.getTeamId();
				
				if (playerToMatch.getProfileId()!= null && playerToMatch.getProfileId() > 0) {
					//Case 1.1: Team Id is available and player Profile Id is available
						//Check if player is part of the team 
						//Check if player is not part of the match(check in both teams) already then proceed
					CricketTeamPlayers addUserTeam = cricketTeamPlayerDao.getByProfileIdForTeam(session, playerToMatch.getProfileId(), teamId);
					if (addUserTeam == null) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.USER_NOT_PART_OF_TEAM);
						errorInfo.setErrorDesc("User not part of this team/team doesnt exist");
						response.getErrors().add(errorInfo);
						return;
					}
					if(!addUserTeam.getIsActive()) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.USER_NOT_ACTIVE_IN_TEAM);
						errorInfo.setErrorDesc("This user is not active in this team");
						response.getErrors().add(errorInfo);
						return;
					}
					CricketMatchTeamPlayers matchPlayer = cricketMatchTeamPlayerDao.getByMatchIdProfileId(session, playerToMatch.getMatchId(), playerToMatch.getProfileId());
					if (matchPlayer != null) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.PLAYER_ALREADY_ADDED_TO_MATCH);
						errorInfo.setErrorDesc("Player already added to the match");
						response.getErrors().add(errorInfo);
						return;
					}
					player.setProfile(addUserTeam.getCricketProfile());
				} else {
					//Case 1.2: Check Player Name is not null
					if(playerToMatch.getProfileName() !=  null) {
						player.setProfileName(playerToMatch.getProfileName());
					} else {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.INVALID_PLAYER);
						errorInfo.setErrorDesc("Player Name or Player Profile Id has to be given");
						response.getErrors().add(errorInfo);
						return;
					}
				}
			} else if (playerToMatch.getTeamName() != null && playerToMatch.getTeamName().length() > 0) {
				//Case 2: Check if Team Name is the opponent Team Name and make sure opponent team
				if(playerToMatch.getTeamName().equals(cricketMatch.getOppTeamName())) {
					player.setTeamName(playerToMatch.getTeamName());
				} else {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
					errorInfo.setErrorDesc("Given team name is not part of the match");
					response.getErrors().add(errorInfo);
					return;
				}
				//Case 2.1: Check if the player name is not null and add to the match opponent team
				if(playerToMatch.getProfileName() !=  null) {
					player.setProfileName(playerToMatch.getProfileName());
				} else {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.INVALID_PLAYER);
					errorInfo.setErrorDesc("Player Name has to be given. Player profile Id cannot be used in a unregistered team");
					response.getErrors().add(errorInfo);
					return;
				}
			} else {
				if (team == null) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
					errorInfo.setErrorDesc("Team Id or Team Name has to be given with the request");
					response.getErrors().add(errorInfo);
					return;
				}
			}
			
			
			player.setCreatedId(playerToMatch.getSignedInUserId());
			player.setCreatedTime(currentDate);
			session.getTransaction().begin();
			Integer addPlayerId = cricketMatchTeamPlayerDao.insert(session, player);
			ObjectTransformUtil.transform(playerToMatch, response);
			response.setMatchTeamPlayerId(addPlayerId);
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}

	private void validateIsAuthorized(Session session, CricketMatch cricketMatch, Integer signedInUserId)
			throws UnauthorizedException {
		boolean isAuthorized = isAuthorizedToEditMatch(signedInUserId, session,
				cricketMatch);
		if(!isAuthorized) {
			throw new UnauthorizedException("Not Autorized to create players to the match");
		}
	}

	private boolean isAuthorizedToEditMatch(Integer signedInUserId,
			Session session, CricketMatch cricketMatch) {
		boolean isAuthorized = false;
		Integer teamId = cricketMatch.getTeam().getTeamId();
		CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
		CricketTeamPlayers signedUserTeam = cricketTeamPlayerDao.getByProfileIdForTeam(session, signedIncricketProfile.getCricketProfileId(), teamId);
		if(signedUserTeam != null && signedUserTeam.getIsModerator()) {
			isAuthorized = true;
		}
		CricketTeam oppTeam = cricketMatch.getOppTeam();
		if (oppTeam != null && !isAuthorized) {
			CricketTeamPlayers oppTeamPlayer = cricketTeamPlayerDao.getByProfileIdForTeam(session, signedIncricketProfile.getCricketProfileId(), oppTeam.getTeamId());
			if(oppTeamPlayer != null && oppTeamPlayer.getIsModerator()) {
				isAuthorized = true;
			}
		}
		return isAuthorized;
	}

	private void validatePlayerMatchRequest (
			CricketTeamPlayerMatchVo playerToMatch, BaseResponseVo response) {
		if (StringUtils.isEmpty(playerToMatch.getProfileName()) && playerToMatch.getProfileId() == null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
			errorInfo.setErrorDesc("No Team with given ID found");
			response.getErrors().add(errorInfo);
			return;
		}
		
		if (StringUtils.isEmpty(playerToMatch.getTeamName()) && playerToMatch.getTeamId()== null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
			errorInfo.setErrorDesc("No Team with given ID found");
			response.getErrors().add(errorInfo);
			return;
		}
		if (playerToMatch.getTeamId() == null && playerToMatch.getProfileId() != null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.USER_NOT_PART_OF_TEAM);
			errorInfo.setErrorDesc("No Team with given ID found");
			response.getErrors().add(errorInfo);
			return;
		}
	}
	
	/**
	 * Get the match given the ID
	 * @param matchId
	 * @param response
	 * @throws QSportsServiceException
	 */
	public void getMatch(Integer matchId, Integer signedInUserId, CricketMatchResponseVo response) throws QSportsServiceException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			if (matchId == null) {
				matchId = 0;
			}
			match = cricketMatchDao.getByPrimaryKey(session, matchId);
			if (match == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match Doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			} 
			ObjectTransformUtil.transform(match, response);
			response.setTeamId(match.getTeam().getTeamId());
			response.setTeamName(match.getTeam().getTeamName());
			if (match.getOppTeam() != null) {
				response.setOppTeamId(match.getOppTeam().getTeamId());
				response.setOppTeamName(match.getOppTeam().getTeamName());
			}
			response.setApprovedById(match.getApprovedBy());
			response.setEditable(isAuthorizedToEditMatch(signedInUserId, session, match) && (match.getMatchStatus() == MatchStatus.PLANNED.getId()));
			List<CricketMatchTeamPlayers> team1Players = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, matchId, match.getTeam().getTeamId());
			response.setTeamPlayers(extractTeamPlayerResponse(team1Players));
			if (match.getOppTeam() != null) {
				List<CricketMatchTeamPlayers> team2Player  = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, matchId, match.getOppTeam().getTeamId());
				response.setTeamOppPlayers(extractTeamPlayerResponse(team2Player));
			} else {
				List<CricketMatchTeamPlayers> team2Player  = cricketMatchTeamPlayerDao.getByMatchIdTeamName(session, matchId, match.getOppTeamName());
				response.setTeamOppPlayers(extractTeamPlayerResponse(team2Player));
			}
			getMatchExtInfo(match, response, session);
		} finally {
			closeSession(session);
		}
			
	}

	/**
	 * @param match
	 * @param response
	 */
	private void getMatchExtInfo(CricketMatch match, CricketMatchResponseVo response, Session session) {
		CricketMatchExt matchExtension = cricketMatchExtDao.getByMatchId(session, match.getMatchId());
		if (matchExtension != null) {
			ObjectTransformUtil.transformGivenFields(matchExtension, response, true, CricketMatchExt.class.getDeclaredFields());
		}
	}
	
	/**
	 * 
	 * @param teamPlayers
	 * @return
	 */
	private List<CricketMatchTeamPlayersRespVo> extractTeamPlayerResponse(List<CricketMatchTeamPlayers> teamPlayers) {
		List<CricketMatchTeamPlayersRespVo> playersResponse = new ArrayList<CricketMatchTeamPlayersRespVo>();
		if (teamPlayers != null) {
			for (CricketMatchTeamPlayers player : teamPlayers) {
				CricketMatchTeamPlayersRespVo teamPlayerResponse = new CricketMatchTeamPlayersRespVo();
				ObjectTransformUtil.transform(player, teamPlayerResponse);
				if (player.getTeam() != null) {
					teamPlayerResponse.setTeamId(player.getTeam().getTeamId());
					teamPlayerResponse.setTeamName(player.getTeam().getTeamName());
				}
				if (player.getProfile() != null) {
					teamPlayerResponse.setProfileId(player.getProfile().getCricketProfileId());
					teamPlayerResponse.setProfileName(player.getProfile().getUser().getFirstName() + player.getProfile().getUser().getLastName());
				}
				playersResponse.add(teamPlayerResponse);
			}
		}
		return playersResponse;
	}
	
	/**
	 * Start Match
	 * @param request
	 * @param response
	 * @throws UnauthorizedException
	 */
	public void startMatch(TossInfoVo request, InningsMinResponseVo response) throws UnauthorizedException {
		//1. Do Mandatory Parameter validation
		//2. Do authorization check
		//3. Check state of the match is planned
		//4. Check number of players added in Team 1
		//5. Check number of players in opponent team
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			Integer matchId = request.getMatchId();
			if (matchId == null) {
				matchId = 0;
			}
			match = cricketMatchDao.getByPrimaryKey(session, matchId);
			if (match == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match Doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			} 
			if (match.getMatchStatus() != MatchStatus.PLANNED.getId()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_STATE);
				errorInfo.setErrorDesc("Match is has already started/completed.");
				response.getErrors().add(errorInfo);
				return;
			}
			Integer signedInUserId = request.getSignedInUserId();
			boolean isAuthorised = isAuthorizedToEditMatch(signedInUserId, session, match);
			if (!isAuthorised) {
				throw new UnauthorizedException("");
			}
			validateMatchToStart(match, session, response);
			if (request.getTossWonBy() == null && 
					!(request.getTossWonBy() == ELECTED_TO_BOWL || request.getTossWonBy() == ELECTED_TO_BAT)) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_TOSS_INFO);
				errorInfo.setErrorDesc("Invalid Toss Info");
				response.getErrors().add(errorInfo);
			}
			if (request.getElectedTo() == null && 
					!(request.getElectedTo() == TEAM_1_CONSTANT || request.getElectedTo() == TEAM_2_CONSTANT)) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_ELECTED_TO_INFO);
				errorInfo.setErrorDesc("Invalid Elected To Info");
				response.getErrors().add(errorInfo);
			}
			
			if (!response.getErrors().isEmpty()) {
				return;
			} else{
				Date currentDate = Calendar.getInstance().getTime();
				match.setMatchStatus(MatchStatus.STARTED.getId());
				match.setTossWonBy(request.getTossWonBy());
				match.setElectedTo(request.getElectedTo());
				match.setUpdatedId(request.getSignedInUserId());
				match.setUpdatedTime(currentDate);
				
				Innings inning = new Innings();
				CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
				inning.setCreatedId(signedIncricketProfile.getCricketProfileId());
				inning.setCreatedTime(currentDate);
				
				//First Innings
				inning.setOrder(1);
				if(request.getTossWonBy() == 1) {
					if(request.getElectedTo() == 1) {
						inning.setTeam(match.getTeam());
					} else {
						inning.setTeam(match.getOppTeam());
						if (match.getOppTeam() == null) {
							inning.setTeamName(match.getOppTeamName());
						}
					}
				} else {
					if(request.getElectedTo() == 1) {
						inning.setTeam(match.getOppTeam());
						if (match.getOppTeam() == null) {
							inning.setTeamName(match.getOppTeamName());
						}
						
					} else {
						inning.setTeam(match.getTeam());
					}
				}
				inning.setMatch(match);
				session.beginTransaction();
				cricketMatchDao.update(session, match);
				Integer inningsId = inningsDao.insert(session, inning);
				ObjectTransformUtil.transform(inning, response);
				response.setInningsId(inningsId);
				session.getTransaction().commit();
				
			}
		} finally {
			closeSession(session);
			
		}
	}
	
	/**
	 * validate match before start
	 * @param match
	 * @param session
	 * @param response
	 */
	private void validateMatchToStart(CricketMatch match, Session session, BaseResponseVo response) {
		int maxPlayers = match.getMaxPlayers() == null? DEFAULT_NUMBER_OF_PLAYERS : match.getMaxPlayers();
		CricketTeam team = match.getTeam();
		List<CricketMatchTeamPlayers> teamPlayers = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, match.getMatchId(), team.getTeamId());
		if (teamPlayers == null || teamPlayers.size() < maxPlayers) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.INVALID_NUMBER_OF_PLAYERS);
			errorInfo.setErrorDesc("Not enough players added to the team");
			response.getErrors().add(errorInfo);
		}
		match.setMaxPlayers(maxPlayers);
		CricketTeam oppTeam = match.getOppTeam();
		if (oppTeam == null) {
			String oppTeamName = match.getOppTeamName();
			List<CricketMatchTeamPlayers> oppTeamPlayers = cricketMatchTeamPlayerDao.getByMatchIdTeamName(session, match.getMatchId(), oppTeamName);
			if (oppTeamPlayers == null || oppTeamPlayers.size() < maxPlayers) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_NUMBER_OF_PLAYERS);
				errorInfo.setErrorDesc("Invalid Number of players");
				response.getErrors().add(errorInfo);
			}
		} else {
			List<CricketMatchTeamPlayers> oppTeamPlayers = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, match.getMatchId(), oppTeam.getTeamId());
			if (oppTeamPlayers == null || oppTeamPlayers.size() < maxPlayers) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_NUMBER_OF_PLAYERS);
				errorInfo.setErrorDesc("Not enough players added to the oppponent team");
				response.getErrors().add(errorInfo);
			}
		}
		if (match.getMatchDate() == null) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_DATE);
			errorInfo.setErrorDesc("Invalid match date");
			response.getErrors().add(errorInfo);
		}
		//TODO: Why is not set in Match creation?
		if (match.getMaxOvers() == null) {
			for(MatchFormatEnum format : MatchFormatEnum.values()) {
				if (format.getId() == match.getCricketFormat()) {
					match.setMaxOvers(format.getMaxOvers());
					break;
				}
			}
		}
		//List<ErrorInfo> errors = validator.validate(match);
		//response.getErrors().addAll(errors);
	}
	
	/**
	 * Get the innings for a given matchId.
	 * @param matchId
	 */
	public void getInnings(Integer matchId, Integer signedInUserId, MatchInningsDetailsResponse response) throws UnauthorizedException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			if (matchId == null) {
				matchId = 0;
			}
			match = cricketMatchDao.getByPrimaryKey(session, matchId);
			if (match == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match Doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			} 
			if (!response.getErrors().isEmpty()) {
				return;
			} 
			
			boolean isAuthorised = isAuthorizedToEditMatch(signedInUserId, session, match);
			boolean isEditable = isAuthorised && (match.getMatchStatus() == MatchStatus.STARTED.getId());
			List<Innings> innings = inningsDao.getInningsForMatch(session, matchId);
			List<InningsResponseVo> inningsResponses = new ArrayList<InningsResponseVo>();
			for (Innings inng : innings) {
				InningsResponseVo inningsResponse = new InningsResponseVo();
				ObjectTransformUtil.transform(inng, inningsResponse);
				if (inng.getTeam() != null) {
					inningsResponse.setTeamId(inng.getTeam().getTeamId());
					inningsResponse.setTeamName(inng.getTeam().getTeamName());
				} else {
					inningsResponse.setTeamName(inng.getTeamName());
				}
				inningsResponse.setBattingEntries(fillBattingScorecardResponse(session, inng));
				inningsResponse.setBowlingEntries(fillBowlingScorecardResponse(inng));
				inningsResponses.add(inningsResponse);
			}
			response.setInnings(inningsResponses);
			response.setMatchId(matchId);
			response.setEditable(isEditable);
		} finally {
			closeSession(session);
			
		}
		
	}

	/**
	 * Fill BowlingScorecard Response. Extract from Innings
	 * @param inng
	 * @return
	 */
	private Set<BowlingScorecardEntryResponse>  fillBowlingScorecardResponse(Innings inng) {
		Set<BowlingScorecardEntry> bowlingEntries = inng.getBowlingEntries();
		Set<BowlingScorecardEntryResponse> bowlingResponse = new HashSet<BowlingScorecardEntryResponse>();
		for (BowlingScorecardEntry bowlEntry : bowlingEntries) {
			BowlingScorecardEntryResponse bowlResponse = new BowlingScorecardEntryResponse();
			ObjectTransformUtil.transform(bowlEntry, bowlResponse);
			bowlResponse.setMatchPlayerId(bowlEntry.getPlayer().getMatchTeamPlayerId());
			CricketProfile profile = bowlEntry.getPlayer().getProfile();
			if (profile != null) {
				bowlResponse.setBowlerName(profile.getUser().getFirstName());
				bowlResponse.setBowlerId(profile.getCricketProfileId());
			} else {
				bowlResponse.setBowlerName(bowlEntry.getPlayer().getProfileName());
			}
			bowlingResponse.add(bowlResponse);
		}
		
		return bowlingResponse;
	}

	/**
	 * Fill the battingScorecard Response. Extract from Innings
	 * @param session
	 * @param inng
	 */
	private Set<BattingScorecardEntryResponse> fillBattingScorecardResponse(Session session, Innings inng) {
		Set<BattingScorecardEntry> battingEntries =   inng.getBattingEntries();
		Set<BattingScorecardEntryResponse> battingResponse = new HashSet<BattingScorecardEntryResponse>();
		for(BattingScorecardEntry entry : battingEntries) {
			BattingScorecardEntryResponse battingEntryResponse = new BattingScorecardEntryResponse();
			ObjectTransformUtil.transform(entry, battingEntryResponse);
			battingEntryResponse.setMatchTeamPlayerId(entry.getPlayer().getMatchTeamPlayerId());
			if (entry.getPlayer().getProfile() != null) {
				User user = entry.getPlayer().getProfile().getUser();
				battingEntryResponse.setPlayerName(user.getFirstName() + user.getLastName());
				battingEntryResponse.setPlayerId(entry.getPlayer().getProfile().getCricketProfileId());
			} else {
				battingEntryResponse.setPlayerName(entry.getPlayer().getProfileName());
			}
			
			Integer fielderMatchId = entry.getFielder();
			CricketMatchTeamPlayers plFielder = cricketMatchTeamPlayerDao.getByPrimaryKey(session, fielderMatchId);
			if (plFielder.getProfile() != null) {
				CricketProfile profile = plFielder.getProfile();
				battingEntryResponse.setFielderName(profile.getUser().getFirstName());
				battingEntryResponse.setFielderId(profile.getCricketProfileId());
			} else {
				battingEntryResponse.setFielderName(plFielder.getProfileName());
			}
			Integer bowlerMatchId = entry.getBowlerFielder();
			CricketMatchTeamPlayers plBowler = cricketMatchTeamPlayerDao.getByPrimaryKey(session, bowlerMatchId);
			if (plBowler.getProfile() != null) {
				CricketProfile profile = plBowler.getProfile();
				battingEntryResponse.setBowlerName(profile.getUser().getFirstName());
				battingEntryResponse.setBowlerId(profile.getCricketProfileId());
			} else {
				battingEntryResponse.setBowlerName(plBowler.getProfileName());
			}
			battingResponse.add(battingEntryResponse);
		}
		return battingResponse;
	}
	
	public void updateBattingEntries(List<BattingScorecardEntryVo> battingEntries,
			Integer inningsId,
			Integer signedInUserId,
			BaseResponseVo response) throws UnauthorizedException{
		Session session = SessionManager.getSessionFactory().openSession();
		try{
			Date createdTime = Calendar.getInstance().getTime();
			Innings innings = inningsDao.getByPrimaryKey(session, inningsId);
			if (innings == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INNINGS_NOT_FOUND);
				errorInfo.setErrorDesc("Innings not found");
				response.getErrors().add(errorInfo);
				return;
			}
			
			CricketMatch match = innings.getMatch();
			if(!isAuthorizedToEditMatch(signedInUserId, session, match)) {
				throw new UnauthorizedException("Not Authorized to update Bowling Entry");
			}
			CricketProfile signedCricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
			CricketTeam battingTeam = innings.getTeam();
			CricketTeam bowlingTeam = null;
			String bowlingTeamName = null;
			String battingTeamName = battingTeam.getTeamName();
			if (battingTeam == null || (battingTeam != null && battingTeam.getTeamId() != match.getTeam().getTeamId())) {
				bowlingTeam = match.getTeam();
			} else {
				if (match.getOppTeam() == null) {
					bowlingTeamName = match.getOppTeamName();
				} else {
					bowlingTeam = match.getOppTeam();
				}
			}
			Integer maxPlayers = innings.getMatch().getMaxPlayers();
			if (maxPlayers == null) {
				maxPlayers = 11;
			}
			List<BattingScorecardEntry> insertBattingEntries = new ArrayList<BattingScorecardEntry>();
			List<BattingScorecardEntry> updateBattingEntries = new ArrayList<BattingScorecardEntry>();
			List<BowlingScorecardEntry> insertBowlingEntries = new ArrayList<BowlingScorecardEntry>();
			List<BowlingScorecardEntry> updateBowlingEntries = new ArrayList<BowlingScorecardEntry>();
			List<BowlingScorecardEntry> deleteBowlingEntries = new ArrayList<BowlingScorecardEntry>();
			Map<Integer, BowlingScorecardEntry> bowlerData = new HashMap<Integer, BowlingScorecardEntry>();
						
			Set<Integer> playerIds = new HashSet<Integer>();

			int notOut = 0;
			int wickets = 0;
			int runsScoredExist = 0;
			int runsScoredNew = 0;
			int battingOrder = 1;
			BattingScorecardEntryValidator validtor = new BattingScorecardEntryValidator();
			for(BattingScorecardEntryVo battingEntry : battingEntries) {
				Integer batsmanId = battingEntry.getMatchTeamPlayerId();
				if (batsmanId == null || playerIds.contains(batsmanId)) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.INVALID_FIELDER);
					errorInfo.setErrorDesc("Invalid bowler");
					response.getErrors().add(errorInfo);
				}
				Integer bowlerId = battingEntry.getBowlerFielder();
				Integer fielderId = battingEntry.getFielder();
				CricketMatchTeamPlayers batsman = null;
				CricketMatchTeamPlayers bowler = null;
				Integer outType = battingEntry.getOutType();
				if (outType == null) {
					outType = -1;
				}
				//Validate Existence of player
				if (batsmanId != null) {
					batsman = validateIsPlayerPartOfMatch(response, session, battingTeam,
							battingTeamName, bowlerId);
				}
				if (fielderId != null) {
					validateIsPlayerPartOfMatch(response, session, bowlingTeam,
							bowlingTeamName, fielderId);
				}
				if (bowlerId != null) {
					bowler = validateIsPlayerPartOfMatch(response, session, bowlingTeam,
							bowlingTeamName, bowlerId);
				}
				
				response.getErrors().addAll(validtor.validate(battingEntry));
				 if (battingEntry.getOutType() == null || battingEntry.getOutType() < 0) {
					 notOut += notOut;
					 if (notOut > 2) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.NOT_OUT_COUNT);
						errorInfo.setErrorDesc("Not Out count should be 2");
						response.getErrors().add(errorInfo); 
					 }
				 } else {
					 wickets += wickets;
				 }
				 if (!response.getErrors().isEmpty()) {
					 break;
				 }
				 //Date createdTime = Calendar.getInstance().getTime();
				 if (OutTypeEnum.BOWLED.getOutTypeId() == outType
							|| OutTypeEnum.LEG_BEFORE_WICKET.getOutTypeId() == outType
							|| OutTypeEnum.HIT_WICKET.getOutTypeId() == outType
							|| OutTypeEnum.CAUGHT.getOutTypeId() == outType) {
					 BowlingScorecardEntry entry  = bowlerData.get(bowlerId) != null ? bowlerData.get(bowlerId) : bowlingEntryDao.getByPlayerAndInningsId(session, innings.getInningsId(), bowlerId);
					 if (entry == null) {
						 if (innings.getBowlingEntries().size() == maxPlayers) {
							 //error
							 ErrorInfo errorInfo = new ErrorInfo();
							 errorInfo.setErrorCode(ErrorConstants.MAX_ENTRIES_REACHED);
							 errorInfo.setErrorDesc("Maximum bowling entries reached");
							 response.getErrors().add(errorInfo);
							 return;
						 }
						 entry = new BowlingScorecardEntry();
						 entry.setCreatedTime(createdTime);
						 entry.setCreatedId(signedCricketProfile.getCricketProfileId());
						 insertBowlingEntries.add(entry);
						
					 } else if (bowlerData.get(bowlerId) != null) {
						 	 entry.setWickets(0);
							 updateBowlingEntries.add(entry);
					 }
					 entry.setUpdatedId(signedCricketProfile.getCricketProfileId());
					 entry.setUpdatedTime(createdTime);
					 entry.setPlayer(bowler);
					 entry.setWickets(entry.getWickets() + 1);
					 entry.setInnings(innings);
					 bowlerData.put(bowlerId, entry);
				 }
				 
				 BattingScorecardEntry entry  = battingEntryDao.getByPlayerAndInningsId(session, innings.getInningsId(), batsmanId);
				 if (entry == null) {
					 if (innings.getBattingEntries().size() == maxPlayers) {
						 //error
						 ErrorInfo errorInfo = new ErrorInfo();
						 errorInfo.setErrorCode(ErrorConstants.MAX_ENTRIES_REACHED);
						 errorInfo.setErrorDesc("Maximum bowling entries reached");
						 response.getErrors().add(errorInfo);
						 return;
					 }
					 entry = new BattingScorecardEntry();
					 entry.setCreatedTime(createdTime);
					 entry.setCreatedId(signedCricketProfile.getCricketProfileId());
					 insertBattingEntries.add(entry);
					 runsScoredNew += getRunsScored(battingEntry, entry);
				 } else {
					 runsScoredExist += getRunsScored(battingEntry, entry);
					 updateBattingEntries.add(entry);
				 }
				 ObjectTransformUtil.transform(battingEntry, entry);
				 entry.setUpdatedId(signedCricketProfile.getCricketProfileId());
				 entry.setUpdatedTime(createdTime);
				 entry.setInnings(innings);
				 entry.setPlayer(batsman);
				 entry.setBattingEntryId(battingOrder);
				 battingOrder++;
				 playerIds.add(battingEntry.getMatchTeamPlayerId());
			}
			if (!response.getErrors().isEmpty()) {
				
				//Fetch existing batting entries
				Set<BattingScorecardEntry> deleteBattingEntries = innings.getBattingEntries();
				Set<Integer> bowlerIds = new HashSet<Integer>();
				for(BattingScorecardEntry entry:deleteBattingEntries){
					boolean found = false;
					for(BattingScorecardEntry updateEntry : updateBattingEntries){
						if(entry.getBattingEntryId().equals(updateEntry.getBattingEntryId())){
							found = true;
							if(entry.getBowlerFielder() != null 
									&& !entry.getBowlerFielder().equals(updateEntry.getBowlerFielder())){
								//Adding bowler ids whose bowling entry in this innings to be updated or deleted
								bowlerIds.add(entry.getBowlerFielder());
							}
							break;
						}
					}
					if(found){
						//Removes from the list of batting entries to be deleted
						deleteBattingEntries.remove(entry);
					}
				}
				
				for(BattingScorecardEntry entry:deleteBattingEntries){
					
					Integer bowlerId = entry.getBowlerFielder();
					if(null != bowlerId){
						if(null != bowlerData.get(bowlerId)){
							//update 
							BowlingScorecardEntry bowlingEntry = bowlerData.get(bowlerId);
							Integer noOfWickets = bowlingEntry.getWickets();
							noOfWickets -=1;
							bowlingEntry.setWickets(noOfWickets);
						}
						else{
							//fetch and update/delete
							BowlingScorecardEntry bowlingEntry = bowlingEntryDao.getByPlayerAndInningsId(session,
									innings.getInningsId(),
									entry.getBowlerFielder());
							Integer noOfWickets = bowlingEntry.getWickets();
							noOfWickets -=1;
							bowlingEntry.setWickets(noOfWickets);
							
							if(noOfWickets <=0 && bowlingEntry.getOvers() <=1){
								deleteBowlingEntries.add(bowlingEntry);
							}
							else{
								bowlingEntry.setUpdatedId(signedCricketProfile.getCricketProfileId());
								bowlingEntry.setUpdatedTime(createdTime);
								bowlingEntry.setInnings(innings);
								updateBowlingEntries.add(bowlingEntry);
							}
						}
					}
					
				}
				
				
				int totalRuns = (innings.getTotalRuns() - runsScoredExist) + (runsScoredExist + runsScoredNew);
				innings.setTotalRuns(totalRuns);
				innings.setWickets(wickets);
				innings.setUpdatedId(signedCricketProfile.getCricketProfileId());
				innings.setUpdatedTime(Calendar.getInstance().getTime());
				
				session.beginTransaction();
				for (BowlingScorecardEntry entry : insertBowlingEntries) {
					session.save(entry);
				}
				for (BowlingScorecardEntry entry : updateBowlingEntries) {
					session.update(entry);
				}
				for (BowlingScorecardEntry entry : deleteBowlingEntries) {
					session.delete(entry);
				}
				for (BattingScorecardEntry entry : insertBattingEntries) {
					session.save(entry);
				}
				for (BattingScorecardEntry entry : updateBattingEntries) {
					session.update(entry);
				}
				for (BattingScorecardEntry entry : deleteBattingEntries) {
					session.delete(entry);
				}
				inningsDao.update(session, innings);
				session.getTransaction().commit();
			}
		}
		finally{
			closeSession(session);
		}
	}


	/**
	 * validate if player belongs to match for a particular team
	 * @param response
	 * @param session
	 * @param team
	 * @param teamName
	 * @param playerId
	 */
	private CricketMatchTeamPlayers validateIsPlayerPartOfMatch(BaseResponseVo response,
			Session session, CricketTeam team, String teamName,
			Integer playerId) {
		CricketMatchTeamPlayers fielder = null;
		if (team != null) {
			fielder = cricketMatchTeamPlayerDao.getByMatchPlayerIdAndTeamId(session, playerId, team.getTeamId());
		} else {
			fielder = cricketMatchTeamPlayerDao.getByMatchPlayerIdAndTeamName(session, playerId, teamName);
		}
		if (fielder == null) {
			//error player not part of match or specified team
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.PLAYER_NOT_PART_OF_MATCH);
			errorInfo.setErrorDesc("Player Not part of match");
			response.getErrors().add(errorInfo);
		}
		return fielder;
	}
	
	/**
	 * Get Runs
	 * @param battingEntry
	 * @param entry
	 * @return
	 */
	private int getRunsScored(BattingScorecardEntryVo battingEntry, BattingScorecardEntry entry){
		int runsScored = 0;
		int ballsFaced = 0;
		if (battingEntry.getDots() != null) {
			ballsFaced += battingEntry.getDots();
		}
		if (battingEntry.getSingles() != null) {
			ballsFaced += battingEntry.getSingles();
			runsScored += battingEntry.getSingles();
		}
		if (battingEntry.getDoubles() != null) {
			ballsFaced += battingEntry.getDoubles();
			runsScored += 2*battingEntry.getSingles();
		}
		if (battingEntry.getTripples() != null) {
			ballsFaced += battingEntry.getTripples();
			runsScored += 3*battingEntry.getSingles();
		}
		if (battingEntry.getFours() != null) {
			ballsFaced += battingEntry.getFours();
			runsScored += 4*battingEntry.getSingles();
		}
		if (battingEntry.getFive() != null) {
			ballsFaced += battingEntry.getFive();
			runsScored += 5*battingEntry.getSingles();
		}
		
		if (battingEntry.getSix() != null) {
			ballsFaced += battingEntry.getSix();
			runsScored += 6*battingEntry.getSingles();
		}
		if (battingEntry.getSeven() != null) {
			ballsFaced += battingEntry.getSeven();
			runsScored += 7*battingEntry.getSingles();
		}
		if (battingEntry.getEight() != null) {
			ballsFaced += battingEntry.getEight();
			runsScored += 8*battingEntry.getSingles();
		}
		if (battingEntry.getNine() != null) {
			ballsFaced += battingEntry.getNine();
			runsScored += 9*battingEntry.getSingles();
		}
		entry.setBallsFaced(ballsFaced);
		entry.setRuns(runsScored);
		return runsScored;
	}

	/**
	 * Update BowlingEntry for a given innings
	 * @param bowlEntry
	 * @throws UnauthorizedException
	 */
	public void updateBowlingEntries (List<BowlingScorecardEntryVo> bowlEntries, Integer inningsId, Integer signedInUserId, BaseResponseVo response) throws UnauthorizedException {
		Session session = null;
		try {
			
			session = SessionManager.getSessionFactory().openSession();
			Innings innings = inningsDao.getByPrimaryKey(session, inningsId);
			if (innings == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INNINGS_NOT_FOUND);
				errorInfo.setErrorDesc("Innings not found");
				response.getErrors().add(errorInfo);
				return;
			}
			if(!isAuthorizedToEditMatch(signedInUserId, session, innings.getMatch())) {
				throw new UnauthorizedException("Not Authorized to update Bowling Entry");
			}
			//isFirstTeam denotes the firstTeam in the match
			CricketTeam bowlingTeam = null;
			String bowlingTeamName = null;
			//If the team is not registered with QSports then that should be the opponentTeam in the match.
			// so for this current innings the bowlingTeam would be team in match
			if (innings.getTeam() == null) {
				bowlingTeam = innings.getMatch().getTeam();
			} else {
				if (innings.getTeam().getTeamId() != innings.getMatch().getTeam().getTeamId()) {
					bowlingTeam = innings.getMatch().getTeam();
				} else {
					if (innings.getMatch().getOppTeam() == null) {
						bowlingTeamName = innings.getMatch().getOppTeamName();
					} else {
						bowlingTeam = innings.getMatch().getOppTeam();
					}
				}
			}
			
			CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
			
			List<BowlingScorecardEntry> insertEntries = new ArrayList<BowlingScorecardEntry>();
			List<BowlingScorecardEntry> updateEntries = new ArrayList<BowlingScorecardEntry>();
			
			Integer maxPlayers = innings.getMatch().getMaxPlayers();
			if (maxPlayers == null) {
				maxPlayers = 11;
			}
			int wides = 0;
			int noBalls = 0;
			int completesOvers = 0;
			int completedBalls = 0;
			BowlingScorecardEntryValidator validator = new BowlingScorecardEntryValidator();
			for (BowlingScorecardEntryVo bowlEntry : bowlEntries) {
				updateBowlingEntry(response, session, innings, bowlingTeam,
						bowlingTeamName, signedIncricketProfile, insertEntries,
					updateEntries, maxPlayers, validator, bowlEntry);
				wides = wides + (bowlEntry.getWide() == null ? 0 : bowlEntry.getWide());
				noBalls = noBalls + (bowlEntry.getNoBalls() == null ? 0 : bowlEntry.getNoBalls());
				if (!response.getErrors().isEmpty()) {
					break;
				}
				if (bowlEntry.getOvers() != null) {
					String[] oversBowled = bowlEntry.getOvers().toString().split("\\.");
					completesOvers += Integer.parseInt(oversBowled[0]);
					if (oversBowled.length > 1 && Integer.parseInt(oversBowled[1]) > 5) {
						completedBalls += Integer.parseInt(oversBowled[1]);
					}
				}
			}
			completesOvers += completedBalls / 6;
			completedBalls = completedBalls - ((completedBalls / 6) * 6);
			double oversBowled = (completesOvers*10 + completedBalls)/10;
			MatchFormatEnum matchFormat = MatchFormatEnum.OD;
			CricketMatch match = innings.getMatch();
			for (MatchFormatEnum format : MatchFormatEnum.values()) {
				if (format.getId() == match.getCricketFormat()) {
					matchFormat = format;
					break;
				}
			}
			int maxOvers = innings.getMatch().getMaxOvers() == null ? matchFormat.getMaxOvers() : innings.getMatch().getMaxOvers();
			if (maxOvers > 0 && oversBowled > maxOvers) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MAX_OVERS_REACHED);
				errorInfo.setErrorDesc("Maximum allocated overs for the match reached");
				response.getErrors().add(errorInfo);
				return;
			}
			
			if (response.getErrors().isEmpty()) {
				int existWide = innings.getWide() == null ? 0 : innings.getWide();
				int existNoBalls = innings.getNoBall() == null ? 0: innings.getNoBall();
				int totalRuns = innings.getTotalRuns() == null ? 0 : innings.getTotalRuns();
				totalRuns = totalRuns - (existWide + existNoBalls);
				innings.setWide(wides);
				innings.setNoBall(noBalls);
				innings.setTotalRuns(totalRuns + wides + noBalls);
				innings.setUpdatedId(signedIncricketProfile.getCricketProfileId());
				innings.setUpdatedTime(Calendar.getInstance().getTime());
				session.beginTransaction();
				for (BowlingScorecardEntry entry : insertEntries) {
					session.save(entry);
				}
				for (BowlingScorecardEntry entry : updateEntries) {
					session.update(entry);
				}
				inningsDao.update(session, innings);
				session.getTransaction().commit();
			}
			//part of match
		} finally {
			if (session != null) {
				session.clear();
				session.flush();
			}
			closeSession(session);
		}
		
	}

	/**
	 * Update individual BowlingEntry
	 * @param response
	 * @param session
	 * @param innings
	 * @param bowlingTeam
	 * @param bowlingTeamName
	 * @param signedIncricketProfile
	 * @param insertEntries
	 * @param updateEntries
	 * @param maxPlayers
	 * @param validator
	 * @param bowlEntry
	 */
	private void updateBowlingEntry(BaseResponseVo response, Session session,
			Innings innings, CricketTeam bowlingTeam, String bowlingTeamName,
			CricketProfile signedIncricketProfile,
			List<BowlingScorecardEntry> insertEntries,
			List<BowlingScorecardEntry> updateEntries, Integer maxPlayers,
			BowlingScorecardEntryValidator validator,
			BowlingScorecardEntryVo bowlEntry) {
		CricketMatchTeamPlayers player = null;
		if (bowlingTeam == null) {
			player = cricketMatchTeamPlayerDao.getByMatchPlayerIdAndTeamName(session, bowlEntry.getMatchPlayerId(), bowlingTeamName);
		} else {
			player = cricketMatchTeamPlayerDao.getByMatchPlayerIdAndTeamId(session, bowlEntry.getMatchPlayerId(), bowlingTeam.getTeamId());
		}
		if (player == null) {
			//error player not part of match or specified team
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.PLAYER_NOT_PART_OF_MATCH);
			errorInfo.setErrorDesc("Player Not part of match");
			response.getErrors().add(errorInfo);
			return;
		}
		Date createdTime = Calendar.getInstance().getTime();
		List<ErrorInfo> errors = validator.validate(bowlEntry);
		if(!errors.isEmpty()) {
			response.getErrors().addAll(errors);
			return;
		}
		BowlingScorecardEntry entry  = bowlingEntryDao.getByPlayerAndInningsId(session, innings.getInningsId(), player.getMatchTeamPlayerId());
		if (entry == null) {
			if (innings.getBowlingEntries().size() == maxPlayers) {
				//error
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MAX_ENTRIES_REACHED);
				errorInfo.setErrorDesc("Maximum bowling entries reached");
				response.getErrors().add(errorInfo);
				return;
			}
			entry = new BowlingScorecardEntry();
			ObjectTransformUtil.transform(bowlEntry, entry);
			entry.setCreatedTime(createdTime);
			entry.setCreatedId(signedIncricketProfile.getCricketProfileId());
			entry.setUpdatedId(signedIncricketProfile.getCricketProfileId());
			entry.setUpdatedTime(createdTime);
			entry.setPlayer(player);
			entry.setInnings(innings);
			insertEntries.add(entry);
		} else {
			ObjectTransformUtil.transform(bowlEntry, entry);
			entry.setUpdatedId(signedIncricketProfile.getCricketProfileId());
			entry.setUpdatedTime(createdTime);
			updateEntries.add(entry);
		}
	}
	
	public void addInnings(Integer matchId, Integer signedInUserId, boolean isfon, InningsMinResponseVo response) throws UnauthorizedException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			if (matchId == null) {
				matchId = 0;
			}
			match = cricketMatchDao.getByPrimaryKey(session, matchId);
			if (match == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match Doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			} 
			
			if (match.getMatchStatus() != MatchStatus.STARTED.getId()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_STATE);
				errorInfo.setErrorDesc("Match is not in state to add Innings. Match is either complete or in planned state. Please start match");
				response.getErrors().add(errorInfo);
				return;
			}
			
			boolean isAuthorised = isAuthorizedToEditMatch(signedInUserId, session, match);
			if (!isAuthorised) {
				throw new UnauthorizedException("");
			}
			if (!response.getErrors().isEmpty()) {
				return;
			} else{
				Date currentDate = Calendar.getInstance().getTime();
				Innings inning = new Innings();
				CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
				inning.setCreatedId(signedIncricketProfile.getCricketProfileId());
				inning.setCreatedTime(currentDate);
				List<Innings> innings = inningsDao.getInningsForMatch(session, matchId);
				int inningsOrder = innings.size() + 1;
				MatchFormatEnum format = MatchFormatEnum.getEnumById(match.getCricketFormat());
				if (inningsOrder > format.getMaxInns()) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.MAX_INNINGS_FOR_MATCH);
					errorInfo.setErrorDesc("Maximum Innings for the format reached");
					response.getErrors().add(errorInfo);
					return;
				}
				//isFirstTeam denotes the firstTeam in the match
				boolean isFirstTeam = false;
				if(match.getTossWonBy() == 1) {
					if(match.getElectedTo() == 1) {
						isFirstTeam = !(inningsOrder%2 == 0);
					}
				} else {
					if(match.getElectedTo() == 1) {
						isFirstTeam = (inningsOrder%2 == 0);
					}
				}
				//if innings is follow on then reverse the selection
				if (inningsOrder == 3 && isfon) {
					isFirstTeam = !isFirstTeam;
				}
				if (isFirstTeam) {
					inning.setTeam(match.getTeam());
				} else {
					inning.setTeam(match.getOppTeam());
					if (match.getOppTeam() == null) {
						inning.setTeamName(match.getOppTeamName());
					}
				}
				
				inning.setFollowOn(isfon);
				inning.setOrder(inningsOrder);
				inning.setMatch(match);
				session.beginTransaction();
				Integer inningsId = inningsDao.insert(session, inning);
				ObjectTransformUtil.transform(innings, response);
				response.setInningsId(inningsId);
				session.getTransaction().commit();
			}
		} finally {
			closeSession(session);
			
		}
		
	}
	
	/**
	 * Abandon match
	 * @param matchId
	 * @param response
	 * @param signedInUserId
	 * @throws UnauthorizedException
	 */
	public void abandonMatch(Integer matchId,BaseResponseVo response, Integer signedInUserId) throws UnauthorizedException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
				match = cricketMatchDao.getByPrimaryKey(session, matchId);
				if (match == null) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
					errorInfo.setErrorDesc("Match Doesnt exist");
					response.getErrors().add(errorInfo);
					return;
				} 
			if(!isAuthorizedToEditMatch(signedInUserId, session, match)) {
				throw new UnauthorizedException("");
			}
			if (MatchStatus.COMPLETED.getId() == match.getMatchStatus()
					|| MatchStatus.ABANDONED.getId() == match.getMatchStatus()
					|| MatchStatus.REJECTED.getId() == match.getMatchStatus()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_STATE);
				errorInfo.setErrorDesc("Match cannot be cancelled");
				response.getErrors().add(errorInfo);
				return;
			}
			CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
			match.setMatchStatus(MatchStatus.ABANDONED.getId());
			match.setUpdatedTime(Calendar.getInstance().getTime());
			match.setUpdatedId(signedIncricketProfile.getCricketProfileId());
			session.beginTransaction();
			cricketMatchDao.update(session, match);
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}

	/**
	 * Complete the match
	 * @param matchId
	 * @param response
	 * @param signedInUserId
	 * @throws QSportsServiceException
	 */
	public void completeMatch(Integer matchId,BaseResponseVo response, Integer signedInUserId) throws QSportsServiceException, UnauthorizedException {
		Session session = null;
		Date currentDate = Calendar.getInstance().getTime();
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
				match = cricketMatchDao.getByPrimaryKey(session, matchId);
				if (match == null) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
					errorInfo.setErrorDesc("Match Doesnt exist");
					response.getErrors().add(errorInfo);
					return;
				} 
			if(!isAuthorizedToEditMatch(signedInUserId, session, match)) {
				throw new UnauthorizedException("");
			}
			if (MatchStatus.STARTED.getId() == match.getMatchStatus()) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_STATE);
				errorInfo.setErrorDesc("Match should be in Started State to Complete It");
				response.getErrors().add(errorInfo);
				return;
			}
			
			MatchFormatEnum matchFormat = MatchFormatEnum.OD;
			for (MatchFormatEnum format : MatchFormatEnum.values()) {
				if (format.getId() == match.getCricketFormat()) {
					matchFormat = format;
					break;
				}
			}
			
			List<Innings> innings = inningsDao.getInningsForMatch(session, matchId);
			if (MatchFormatEnum.OD.getId() == matchFormat.getId() 
				    || MatchFormatEnum.T20.getId() == matchFormat.getId()) {
					if (innings.size() < matchFormat.getMaxInns()) {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.INVALID_INNINGS_SIZE);
						errorInfo.setErrorDesc("There should be " + matchFormat.getMaxInns() + " Innings");
						response.getErrors().add(errorInfo);
						return;
					}
			}
			
			CricketTeamStats team1Stats = teamStatsDao.getByTeamAndFormat(session, match.getTeam().getTeamId(), matchFormat.getId());
			CricketTeamStats team2Stats = null;
			if (match.getOppTeam() != null) {
				team2Stats = teamStatsDao.getByTeamAndFormat(session, match.getOppTeam().getTeamId(), matchFormat.getId());
			}
			int maxPlayers = match.getMaxPlayers() != null ? match.getMaxPlayers() : 11;
			int maxOvers = match.getMaxOvers() != null ? match.getMaxOvers() : matchFormat.getMaxOvers();
			boolean isTargetRevised = false;
			String revisedReason = "";
			for (Innings inng : innings) {
				Set<BattingScorecardEntry> battingEntries = inng.getBattingEntries();
				int notOut = 0;
				for(BattingScorecardEntry battingEntry : battingEntries) {
					if(battingEntry.getOutType() == null) {
						notOut += 1;
						if (notOut > 2) {
							ErrorInfo errorInfo = new ErrorInfo();
							errorInfo.setErrorCode(ErrorConstants.NOT_OUT_COUNT);
							errorInfo.setErrorDesc("Innings cannot have more than two batsman notouts");
							response.getErrors().add(errorInfo);
							return;
						}
					}
				}
				Set<BowlingScorecardEntry> bowlingEntries = inng.getBowlingEntries();
				int totalRunsScored = 0;
				for(BowlingScorecardEntry bowlingEntry : bowlingEntries) {
					totalRunsScored += bowlingEntry.getRuns();
				}
				if (totalRunsScored > inng.getTotalRuns()) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.INVALID_TOTAL_RUNS);
					errorInfo.setErrorDesc("Total runs given by bowlers cannot be more than innings runs");
					response.getErrors().add(errorInfo);
					return;
				}
				if (inng.getRevisedTarget() != null && inng.getRevisedTarget() > 0) {
					isTargetRevised = true;
					if (inng.getRevisedReason() != null) {
						revisedReason =  inng.getRevisedReason();
					}
				}
				if (inng.getTeam() != null) {
					if(inng.getTeam().getTeamId() == match.getTeam().getTeamId()) {
						team1Stats.setRunsScored(team1Stats.getRunsScored() + inng.getTotalRuns());
					} else {
						team2Stats.setRunsScored(team2Stats.getRunsScored() + inng.getTotalRuns());
					}
				}
			}
			
			if (MatchFormatEnum.OD.getId() == matchFormat.getId() 
			    || MatchFormatEnum.T20.getId() == matchFormat.getId()) {
				if (innings.size() < matchFormat.getMaxInns()) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.INVALID_INNINGS_SIZE);
					errorInfo.setErrorDesc("There should be " + matchFormat.getMaxInns() + " Innings");
					response.getErrors().add(errorInfo);
					return;
				}
				Innings firstInnings = innings.get(0);
				Innings secondInnings = innings.get(1);
				Innings whoWon = null;
				int target = firstInnings.getRevisedTarget() != null ? firstInnings.getRevisedTarget() : firstInnings.getTotalRuns();
				double oversToPlay = firstInnings.getRevisedOvers() != null ? firstInnings.getRevisedOvers() : maxOvers;
				int winMargin  = 0;
				if (secondInnings.getWickets() < (maxPlayers -1) && secondInnings.getOvers() < oversToPlay && secondInnings.getTotalRuns() < target) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.OVERS_NOT_PLAYED);
					errorInfo.setErrorDesc("Overs not played completely. Please revise target and runs if required.");
					response.getErrors().add(errorInfo);
					return;
				}
				if (secondInnings.getTotalRuns() > target) {
					winMargin = maxPlayers - secondInnings.getWickets();
					whoWon = secondInnings;
				} else if (target > secondInnings.getTotalRuns()){
					winMargin = target - secondInnings.getTotalRuns();
					whoWon = firstInnings;
				} else if (target == secondInnings.getTotalRuns()){
					match.setMatchWonBy(0);
					team1Stats.setDraw(team1Stats.getDraw() + 1);
					team1Stats.setMatches(team1Stats.getMatches() + 1);
					if (team2Stats != null) {
						team2Stats.setDraw(team1Stats.getDraw() + 1);
						team2Stats.setMatches(team2Stats.getMatches() + 1);
					}
				}
				match.setMatchStatus(MatchStatus.COMPLETED.getId());
				if (whoWon != null) {
					if (whoWon.getTeam() != null && whoWon.getTeam().getTeamId() == match.getTeam().getTeamId()) {
						match.setMatchWonBy(1);
						team1Stats.setWon(team1Stats.getWon() + 1);
						team1Stats.setMatches(team1Stats.getMatches() + 1);
						if (team2Stats != null) {
							team2Stats.setLost(team2Stats.getLost() + 1);
							team2Stats.setMatches(team2Stats.getMatches() + 1);
						}
					} else {
						match.setMatchWonBy(2);
						if (team2Stats != null) {
							team2Stats.setWon(team2Stats.getWon() + 1);
							team2Stats.setMatches(team1Stats.getMatches() + 1);
						}
						team1Stats.setLost(team1Stats.getLost() + 1);
						team1Stats.setMatches(team1Stats.getMatches() + 1);
					}
					match.setWinByMargin(winMargin);
					match.setWinReason(MATCH_COMPLETE);
					if (isTargetRevised) {
						match.setWinReason(revisedReason);
					}
				}
			}
			Map<Integer, BowlingStatistics> bowlingStatsMap = new HashMap<Integer, BowlingStatistics>();
			//Update Batting Statistics
			Map<Integer, BattingStatistics> battingStatsMap = updateBattingStats(innings, session, matchFormat.getId(), match, bowlingStatsMap);
			
			//Update Bowling Statistics
			updateBowlingStats(innings, session, matchFormat.getId(), match, bowlingStatsMap);
			
			//Update Statistics for the players who are not part of any entries
			updateStatsForNonEntryPlayers(session, match, matchFormat, bowlingStatsMap, battingStatsMap);
			
			//Commit Statistics
			session.beginTransaction();
			CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
			for (BattingStatistics batStats : battingStatsMap.values()) {
				batStats.setUpdateTime(currentDate);
				session.update(batStats);
			}
			for (BowlingStatistics bowlStats : bowlingStatsMap.values()) {
				bowlStats.setUpdateTime(currentDate);
				session.update(bowlStats);
			}
			match.setMatchStatus(MatchStatus.COMPLETED.getId());
			match.setUpdatedTime(currentDate);
			match.setUpdatedId(signedIncricketProfile.getCricketProfileId());
			cricketMatchDao.update(session, match);
			teamStatsDao.update(session, team1Stats);
			teamStatsDao.update(session, team2Stats);
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}

	private void updateStatsForNonEntryPlayers(Session session,
			CricketMatch match, MatchFormatEnum matchFormat,
			Map<Integer, BowlingStatistics> bowlingStatsMap,
			Map<Integer, BattingStatistics> battingStatsMap) {
		List<CricketMatchTeamPlayers> matchPlayers = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, match.getMatchId(), match.getTeam().getTeamId());
		if (match.getOppTeam() != null) {
			List<CricketMatchTeamPlayers> oppTeamPlayers = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, match.getMatchId(), match.getOppTeam().getTeamId());
			matchPlayers.addAll(oppTeamPlayers);
		}
		for (CricketMatchTeamPlayers players : matchPlayers) {
			CricketProfile profile = players.getProfile();
			if (profile != null) {
				 if(battingStatsMap.get(profile.getCricketProfileId()) == null) {
					 BattingStatistics statistics = battingStatsDao.getByCricketProfileIdAndFormat(session, profile.getCricketProfileId(), matchFormat.getId());
					 statistics.setMatches(statistics.getMatches() + 1);
					 battingStatsMap.put(profile.getCricketProfileId(), statistics);
				 }
				 if (bowlingStatsMap.get(profile.getCricketProfileId()) == null) {
						BowlingStatistics statistics = bowlingStatsDao.getByCricketProfileIdAndFormat(session, profile.getCricketProfileId(), matchFormat.getId());
						statistics.setMatches(statistics.getMatches() + 1);
						bowlingStatsMap.put(profile.getCricketProfileId(), statistics);
				}
			}
			
		}
	}
	
	
	/**
	 * Update Batting Statistics
	 * @param innings
	 * @param session
	 * @param format
	 * @param match
	 * @return
	 */
	private Map<Integer, BattingStatistics> updateBattingStats(List<Innings>innings, Session session, Integer format, CricketMatch match, Map<Integer, BowlingStatistics> bowlingStatsMap) {
		Map<Integer, BattingStatistics> profileStatsMap = new HashMap<Integer, BattingStatistics>();
		for (Innings inng : innings) {
			Set<BattingScorecardEntry> battingEntries = inng.getBattingEntries();
			if (inng.getTeam() == null) {
				continue;
			}
			for(BattingScorecardEntry battingEntry : battingEntries) {
				CricketProfile profile = battingEntry.getPlayer().getProfile();
				if(profile != null ) {
					boolean isNewEntry = false;
					BattingStatistics statistics = profileStatsMap.get(profile.getCricketProfileId());
					if (statistics == null) {
					   statistics = battingStatsDao.getByCricketProfileIdAndFormat(session, profile.getCricketProfileId(), format);
					   isNewEntry = true;
					}
					// Runs Stats
					int runsScored =  battingEntry.getRuns() == null ? 0 : battingEntry.getRuns();
					statistics.setRuns(statistics.getRuns() + runsScored);
					 if (runsScored > 300) {
						 statistics.setTrippleHundreds(statistics.getTrippleHundreds() + 1);
					 } else if (runsScored > 200) {
						 statistics.setDoubleHundreds(statistics.getDoubleHundreds() + 1);
					 } else if (runsScored > 100) {
						 statistics.setHundreds(statistics.getHundreds() + 1);
					 } else if (runsScored > 50) {
						 statistics.setFifties(statistics.getFifties() + 1);
					 } else if (runsScored > 30) {
						 statistics.setThirties(statistics.getThirties() + 1);
					 }
					statistics.setInnings(statistics.getInnings() + 1);
					if (battingEntry.getOutType() == null) {
						statistics.setNotOuts(statistics.getNotOuts() + 1);
					}
					if(isNewEntry) {
						statistics.setMatches(statistics.getMatches() + 1);
					}
					//Averages
					statistics.setBallsFaced(statistics.getBallsFaced() + battingEntry.getBallsFaced());
					int totalOutMatches = statistics.getInnings() - statistics.getNotOuts();
					if (totalOutMatches > 0) {
						float average = statistics.getRuns()/totalOutMatches;
						statistics.setAverage(average);
					}
					float strikeRate = (statistics.getRuns()/statistics.getBallsFaced()) * 100;
					statistics.setStrikeRate(strikeRate);
					profileStatsMap.put(profile.getCricketProfileId(), statistics);
				}
				updateFieldingStats(session, format, bowlingStatsMap, battingEntry);
				
			}
		}
		return profileStatsMap;
	}

	/**
	 * Update fielding Stats
	 * @param session
	 * @param format
	 * @param bowlingStatsMap
	 * @param battingEntry
	 * @param profile
	 */
	private void updateFieldingStats(Session session, Integer format,
			Map<Integer, BowlingStatistics> bowlingStatsMap,
			BattingScorecardEntry battingEntry) {
		CricketProfile profile = null;
		if (battingEntry.getOutType() != null 
				&& (battingEntry.getOutType() == OutTypeEnum.CAUGHT.getOutTypeId()
				|| battingEntry.getOutType() == OutTypeEnum.STUMPTED.getOutTypeId())) {
			CricketMatchTeamPlayers fielder = cricketMatchTeamPlayerDao.getByPrimaryKey(session, battingEntry.getFielder());
			profile = fielder.getProfile();
			if (profile != null) {
				profile = fielder.getProfile();
				BowlingStatistics statistics = bowlingStatsMap.get(profile.getCricketProfileId());
				boolean isNewEntry = false;
				if (statistics == null) {
					statistics = bowlingStatsDao.getByCricketProfileIdAndFormat(session,
							profile.getCricketProfileId(),format);
					isNewEntry = true;
				}
				if (battingEntry.getOutType() == OutTypeEnum.CAUGHT.getOutTypeId()) {
					statistics.setCatches(statistics.getCatches() + 1);
				} else {
					statistics.setStumps(statistics.getStumps() + 1);
				}
				if (isNewEntry) {
					statistics.setMatches(statistics.getMatches() + 1);
				}
				bowlingStatsMap.put(profile.getCricketProfileId(), statistics);
			}
		}
	}
	
	/**
	 * Update Bowling Stats
	 * @param innings
	 * @param session
	 * @param format
	 * @param match
	 * @return
	 */
	private void updateBowlingStats(List<Innings>innings, Session session, Integer format, CricketMatch match, Map<Integer, BowlingStatistics> bowlingStatsMap) {
		for (Innings inng : innings) {
			
			if (inng.getTeam() != null
					&& inng.getTeam().getTeamId() == match.getTeam().getTeamId()
					&& match.getOppTeam() == null) {
				continue;
			}
			Set<BowlingScorecardEntry> bowlingEntries = inng.getBowlingEntries();
			for(BowlingScorecardEntry bowlingEntry : bowlingEntries) {
				CricketProfile profile = bowlingEntry.getPlayer().getProfile();
				if(profile != null ) {
					boolean isNewEntry = false;
					BowlingStatistics statistics = bowlingStatsMap.get(profile.getCricketProfileId());
					if (statistics == null) {
					   statistics = bowlingStatsDao.getByCricketProfileIdAndFormat(session, profile.getCricketProfileId(), format);
					   isNewEntry = true;
					}
					int ballTemp = bowlingEntry.getOvers().intValue() * 10;
					int totalBallsBowled = (ballTemp/10) * 6 + ballTemp % 10;
					
					statistics.setBalls(statistics.getBalls() + totalBallsBowled);
					
					if(bowlingEntry.getWickets() != null) {
						statistics.setWickets(statistics.getWickets() + bowlingEntry.getWickets());
						if (bowlingEntry.getWickets() > 5) {
							statistics.setFiveWickets(statistics.getFiveWickets() + 1);
						} else if (bowlingEntry.getWickets() > 3) {
							statistics.setThreeWickets(statistics.getThreeWickets() + 1);
						}
					}
					statistics.setInnings(statistics.getInnings() + 1);
					statistics.setRuns(statistics.getRuns() + bowlingEntry.getRuns());
					
					
					int totalCareerBalls = statistics.getBalls();
					float overs = ((totalCareerBalls/6) * 10 + (totalCareerBalls % 6))/10;
					if (overs > 0) {
						float economy = statistics.getRuns()/overs;
						statistics.setEconomy(economy);
					}
					if (statistics.getWickets() > 0) {
						float average = statistics.getRuns() / statistics.getWickets();
						statistics.setAverage(average);
					}
					//statistics.setBalls(bowlingEntry.get)
					// Runs Stats
					if(isNewEntry) {
						statistics.setMatches(statistics.getMatches() + 1);
					}
					
					if (statistics.getBestBowling() != null) {
						String[] bestBowlHolder = statistics.getBestBowling().split("/");
						if (bestBowlHolder.length == 2) {
							int wicketsTaken = Integer.parseInt(bestBowlHolder[0]);
							int runsGiven = Integer.parseInt(bestBowlHolder[1]);
							if (bowlingEntry.getWickets() > wicketsTaken) {
								statistics.setBestBowling(bowlingEntry.getWickets()+ "/" + bowlingEntry.getRuns());
							} else if (bowlingEntry.getRuns() < runsGiven) {
								statistics.setBestBowling(bowlingEntry.getWickets()+ "/" + bowlingEntry.getRuns());
							}
						}
						
					} else {
						statistics.setBestBowling(bowlingEntry.getWickets()+ "/" + bowlingEntry.getRuns());
					}
					//Averages
					bowlingStatsMap.put(profile.getCricketProfileId(), statistics);
				}
				
				
			}
		}
	}
	
	/**
	 * Close Session
	 * @param session
	 */
	private void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}	}
	
	/**
	 * Set the validator to use.
	 * @param validator
	 */
	public void setValidator(BasicValidatorAdapter validator) {
		this.validator = validator;
	}

	public void reviseTarget(ReviseMatchTargetVo reviseTarget, BaseResponseVo response)  throws InvalidRequestArgumentException,UnauthorizedException{
		if (reviseTarget.getMatchId() != null) {
			Session session = SessionManager.getSessionFactory().openSession();
			try {
				//Check if the match is in started state
				CricketMatch match = cricketMatchDao.getByPrimaryKey(session, reviseTarget.getMatchId());
				if(match!= null  && MatchStatus.STARTED.getId()== match.getMatchStatus()) {
					if(!isAuthorizedToEditMatch(reviseTarget.getSignedInUserId(), session, match)) {
						throw new UnauthorizedException("Not Authorized to revise Target for this match");
					}
					//Check the format is not test
					boolean isOD = MatchFormatEnum.OD.getId().equals(match.getCricketFormat());
					boolean isT20 = MatchFormatEnum.T20.getId().equals(match.getCricketFormat());
					if(isOD	|| isT20){
						//Make sure revised overs is not greater the format overs
						if (isOD && reviseTarget.getRevisedOvers()<= 50
								|| isT20 && reviseTarget.getRevisedOvers()<=20) {

							//update the revised target of innings one
							List<Innings> innings = inningsDao.getInningsForMatch(session, reviseTarget.getMatchId());
							Innings firstInnings = innings.get(0);
							firstInnings.setRevisedOvers(reviseTarget.getRevisedOvers());
							firstInnings.setRevisedTarget(reviseTarget.getRevisedTarget());
							firstInnings.setRevisedReason(reviseTarget.getRevisedReason());
							CricketProfile signedIncricketProfile = cricketProfileDao.getByUserId(session, reviseTarget.getSignedInUserId());
							firstInnings.setUpdatedId(signedIncricketProfile.getCricketProfileId());
							firstInnings.setUpdatedTime(Calendar.getInstance().getTime());
							session.beginTransaction();
							inningsDao.update(session, firstInnings);
							session.getTransaction().commit();
						} else {
							ErrorInfo errorInfo = new ErrorInfo();
							errorInfo.setErrorCode(ErrorConstants.CANNOT_REVISE_TARGET);
							errorInfo.setErrorDesc("Revised overs should not be greated 20(for a T20 match) and 50 (for a OD Match)");
							response.getErrors().add(errorInfo);
							return;
						}

					} else {
						ErrorInfo errorInfo = new ErrorInfo();
						errorInfo.setErrorCode(ErrorConstants.CANNOT_REVISE_TARGET);
						errorInfo.setErrorDesc("The innings target can be revised only for One Day format and T20 format");
						response.getErrors().add(errorInfo);
						return;
					}

				} else {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setErrorCode(ErrorConstants.INVALID_MATCH_STATE);
					errorInfo.setErrorDesc("Match should be in started state to revise the target");
					response.getErrors().add(errorInfo);
					return;
				}
			}
			finally {
				if (session != null) {
					session.clear();
					session.flush();
				}
				closeSession(session);
			}
		} else {
			InvalidRequestArgumentException invalidArgumentException =new InvalidRequestArgumentException();
			Map<String,String> invalidParams = new HashMap<String,String>();
			invalidParams.put("matchId", "null");
			invalidArgumentException.setInvalidParams(invalidParams);

		}
		return;

	}
	
	/**
	 * Get the match given the ID
	 * @param matchId
	 * @param response
	 * @throws QSportsServiceException
	 *//*
	public void getMatch(Integer teamId, Integer signedInUserId, CricketMatchResponseVo response) throws QSportsServiceException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketMatch match = null;
			if (matchId == null) {
				matchId = 0;
			}
			match = cricketMatchDao.getByPrimaryKey(session, matchId);
			if (match == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.MATCH_NOT_EXIST);
				errorInfo.setErrorDesc("Match Doesnt exist");
				response.getErrors().add(errorInfo);
				return;
			} 
			ObjectTransformUtil.transform(match, response);
			response.setTeamId(match.getTeam().getTeamId());
			response.setTeamName(match.getTeam().getTeamName());
			if (match.getOppTeam() != null) {
				response.setOppTeamId(match.getOppTeam().getTeamId());
				response.setOppTeamName(match.getOppTeam().getTeamName());
			}
			response.setApprovedById(match.getApprovedBy());
			response.setEditable(isAuthorizedToEditMatch(signedInUserId, session, match) && (match.getMatchStatus() == MatchStatus.PLANNED.getId()));
			List<CricketMatchTeamPlayers> team1Players = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, matchId, match.getTeam().getTeamId());
			response.setTeamPlayers(extractTeamPlayerResponse(team1Players));
			if (match.getOppTeam() != null) {
				List<CricketMatchTeamPlayers> team2Player  = cricketMatchTeamPlayerDao.getByMatchIdTeamId(session, matchId, match.getOppTeam().getTeamId());
				response.setTeamOppPlayers(extractTeamPlayerResponse(team2Player));
			} else {
				List<CricketMatchTeamPlayers> team2Player  = cricketMatchTeamPlayerDao.getByMatchIdTeamName(session, matchId, match.getOppTeamName());
				response.setTeamOppPlayers(extractTeamPlayerResponse(team2Player));
			}
			getMatchExtInfo(match, response, session);
		} finally {
			closeSession(session);
		}
			
	}*/
}
