package com.thinqq.qsports.server.process;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.BattingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.BowlingStatisticsDAO;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamPlayersDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamStatusDAO;
import com.thinqq.qsports.persistence.dao.UserDAO;
import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeam;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamStats;
import com.thinqq.qsports.persistence.dataobjects.User;
import com.thinqq.qsports.persistence.dto.AddMemberToTeamVo;
import com.thinqq.qsports.persistence.dto.BaseResponseVo;
import com.thinqq.qsports.persistence.dto.BattingStatisticsVo;
import com.thinqq.qsports.persistence.dto.BowlingStatisticsVo;
import com.thinqq.qsports.persistence.dto.CricketProfileVo;
import com.thinqq.qsports.persistence.dto.CricketTeamListResponse;
import com.thinqq.qsports.persistence.dto.CricketTeamListVo;
import com.thinqq.qsports.persistence.dto.CricketTeamMinStatsVo;
import com.thinqq.qsports.persistence.dto.CricketTeamPlayersVo;
import com.thinqq.qsports.persistence.dto.CricketTeamResponseVo;
import com.thinqq.qsports.persistence.dto.CricketTeamStatsVo;
import com.thinqq.qsports.persistence.dto.CricketTeamVo;
import com.thinqq.qsports.persistence.dto.UserInfoVo;
import com.thinqq.qsports.server.error.ErrorConstants;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.QSportsServiceException;
import com.thinqq.qsports.server.error.UnauthorizedException;
import com.thinqq.qsports.shared.cricket.MatchFormatEnum;
import com.thinqq.qsports.shared.cricket.TeamPlayerStatusEnum;
import com.thinqq.qsports.util.ObjectTransformUtil;

/**
 *All Team related services needs to go here 
 *
 */
public class CricketTeamProcess {
	
	private Logger logger = Logger.getLogger(CricketTeamProcess.class
			.getName());
	
	@Autowired
	CricketTeamDAO cricketTeamDao;
	
	@Autowired
	CricketTeamStatusDAO cricketTeamStatusDao;
	
	@Autowired
	CricketProfileDAO cricketProfileDao;
	
	@Autowired
	CricketTeamPlayersDAO cricketTeamPlayerDao;
	
	@Autowired
	private UserDAO userDao;

	
	@Autowired
	private UserProfileProcess userProfileProcess;

	
	/**
	 * Create a new Team.
	 * 
	 * @param cricketTeamVo
	 */
	public void createNewTeam(CricketTeamVo cricketTeamVo, CricketTeamResponseVo response) throws QSportsServiceException {
		CricketTeam team = new CricketTeam();
		ObjectTransformUtil.transform(cricketTeamVo, team);
		Date createdTime = Calendar.getInstance().getTime();
		team.setCreatedId(cricketTeamVo.getSignedInUserId());
		team.setUpdatedId(cricketTeamVo.getSignedInUserId());
		team.setUpdatedTime(createdTime);
		team.setCreatedTime(createdTime);
		/* Ignore any teamId sent in request
		To avoid abusing the API to force an Id to save the team */
		team.setTeamId(null);
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			cricketTeamDao.insert(session, team);
			//Create Owner as Signed in user
			CricketTeamPlayers owner = insertTeamOwnerRecord(cricketTeamVo, team, createdTime, session);
			List<CricketTeamPlayers> players = new ArrayList<CricketTeamPlayers>();
			players.add(owner);
			//Hard-coding since the creator can edit the team anyway!
			response.setEditable(true);
			//Create Default Team Status
			Set<CricketTeamStats> status = insertTeamStatus(team, createdTime, session);
			
			session.getTransaction().commit();
			team.setTeamPlayers(players);
			team.setTeamStats(status);
			ObjectTransformUtil.transform(team, response);
			getTeamMetaInfo(response, team, session);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot create Team", e);
			if (session != null && session.isOpen()) {
				session.getTransaction().rollback();
			}
			throw new QSportsServiceException("Create Team Error" + e.getMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Create a new Team.
	 * 
	 * @param cricketTeamVo
	 */
	public void saveTeam(CricketTeamVo cricketTeamVo, CricketTeamResponseVo response) throws QSportsServiceException {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketTeam team = cricketTeamDao.getByPrimaryKey(session, cricketTeamVo.getTeamId());
			if (team == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
				errorInfo.setErrorDesc("Team with ID " + cricketTeamVo.getTeamId() + " Not Found");
				response.getErrors().add(errorInfo);
				return;
			} 
			boolean isAuthorizedToUpdate = false;
			CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, cricketTeamVo.getSignedInUserId());
			CricketTeamPlayers teamPlayer = cricketTeamPlayerDao.getByProfileIdForTeam(session, cricketProfile.getCricketProfileId(), team.getTeamId());
			isAuthorizedToUpdate = teamPlayer != null && teamPlayer.getIsModerator();
			
			if (isAuthorizedToUpdate) {
				session.getTransaction().begin();
				updateTeam(cricketTeamVo, session, team, response);
				session.getTransaction().commit();
			} else {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.UNAUTHORIZED);
				errorInfo.setErrorDesc("Not authorized to update team");
				response.getErrors().add(errorInfo);
				return;
			}
			
		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Internal Error occured" + e.getMessage());
			response.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}


	/**
	 * update the team
	 * @param cricketTeamVo
	 * @param session
	 * @param team
	 */
	private void updateTeam(CricketTeamVo cricketTeamVo, Session session,
			CricketTeam team,  CricketTeamResponseVo response) {
		if (!StringUtils.isEmpty(cricketTeamVo.getCity())) {
			team.setCity(cricketTeamVo.getCity());
		}
		if (!StringUtils.isEmpty(cricketTeamVo.getCountry())) {
			team.setCountry(cricketTeamVo.getCountry());
		}
		if (!StringUtils.isEmpty(cricketTeamVo.getDescription())) {
			team.setDescription(cricketTeamVo.getDescription());
		}
		if (!StringUtils.isEmpty(cricketTeamVo.getTeamName())) {
			team.setTeamName(cricketTeamVo.getTeamName());
		}
		if (!StringUtils.isEmpty(cricketTeamVo.getState())) {
			team.setState(cricketTeamVo.getState());
		}
	
		Date nowTime = Calendar.getInstance().getTime();
		team.setUpdatedId(cricketTeamVo.getSignedInUserId());
		team.setUpdatedTime(nowTime);
		cricketTeamDao.update(session, team);
		ObjectTransformUtil.transform(team, response);
	}

	/**
	 * Insert Default Team Status.
	 * @param team
	 * @param createdTime
	 * @param session
	 */
	private Set<CricketTeamStats> insertTeamStatus(CricketTeam team, Date createdTime,
			Session session) {
		 Set<CricketTeamStats> status = new HashSet<CricketTeamStats>();
		for(MatchFormatEnum format : MatchFormatEnum.values()) {
			CricketTeamStats teamStatus = new CricketTeamStats();
			teamStatus.setCricketFormat(format.getId());
			teamStatus.setCreatedTime(createdTime);
			teamStatus.setUpdatedTime(createdTime);
			teamStatus.setCricketTeam(team);
			cricketTeamStatusDao.insert(session, teamStatus);
			status.add(teamStatus);
		}
		return status;
	}

	/**
	 * Insert the Team owner record to player list.
	 * @param cricketTeamVo
	 * @param team
	 * @param createdTime
	 * @param session
	 */
	private CricketTeamPlayers insertTeamOwnerRecord(CricketTeamVo cricketTeamVo,
			CricketTeam team, Date createdTime, Session session) {
		CricketTeamPlayers teamOwner = new CricketTeamPlayers();
		CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, cricketTeamVo.getSignedInUserId());
		teamOwner.setCricketProfile(cricketProfile);
		teamOwner.setCricketTeam(team);
		teamOwner.setCreatedTime(createdTime);
		teamOwner.setUpdatedId(cricketTeamVo.getSignedInUserId());
		teamOwner.setUpdatedTime(createdTime);
		teamOwner.setCreatedId(cricketTeamVo.getSignedInUserId());
		teamOwner.setIsModerator(true);
		teamOwner.setIsActive(true);
		teamOwner.setStatus(TeamPlayerStatusEnum.ACCEPTED.getId().toString());
		cricketTeamPlayerDao.insert(session, teamOwner);
		return teamOwner;
	}
	
	/**
	 * Return the user given userEmail
	 * @param email
	 * @return
	 */
	public CricketTeamResponseVo getTeamById(final Integer id, final boolean isStatsRequired, Integer signedInUserId) {
		Session session = null;
		CricketTeamResponseVo teamVo = new CricketTeamResponseVo();
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketTeam team = cricketTeamDao.getByPrimaryKey(session, id);
			
			if (team == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
				errorInfo.setErrorDesc("Team with ID " + id + " Not Found");
				teamVo.getErrors().add(errorInfo);
			} else {
				CricketProfile signedInCricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
				CricketTeamPlayers player = cricketTeamPlayerDao.getByProfileIdForTeam(session, signedInCricketProfile.getCricketProfileId(), team.getTeamId());
				ObjectTransformUtil.transform(team, teamVo);
				if (player != null) {
					teamVo.setEditable(player.getIsModerator());
					teamVo.setTeamAssociation(TeamPlayerStatusEnum.getEnumById(Integer.parseInt(player.getStatus())).getName());
				}
				if (isStatsRequired) {
					getTeamMetaInfo(teamVo, team, session);
				}
			}
		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("SEVERE ERROR");
			teamVo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return teamVo;
	}

	/**
	 * Return the user given userEmail
	 * @param email
	 * @return
	 */
	public List<CricketTeamListVo> getTeamByPlayer(final Integer id) {
		Session session = null;
		List<CricketTeamListVo> teamVos = new ArrayList<CricketTeamListVo>();
		try {
			session = SessionManager.getSessionFactory().openSession();
			List<CricketTeamPlayers> teamPlayers = cricketTeamPlayerDao.getByProfileIdAndStatus(session, id, TeamPlayerStatusEnum.ACCEPTED.getId().toString());
			
			if (teamPlayers != null && !teamPlayers.isEmpty()) {
				Set<Integer> teamIds = new HashSet<Integer>();
				for (CricketTeamPlayers teamPlayer : teamPlayers) {
					teamIds.add(teamPlayer.getCricketTeam().getTeamId());
				}
				List<CricketTeam> teams = cricketTeamDao.getByTeamIds(session, teamIds);
				if (teams != null && !teams.isEmpty()) {
					for (CricketTeam team : teams) {
						CricketTeamListVo teamVo = new CricketTeamListVo();
						ObjectTransformUtil.transformGivenFields(team, teamVo, true, CricketTeam.class.getDeclaredFields());
						for (CricketTeamStats stats : team.getTeamStats()) {
							CricketTeamMinStatsVo statsVo = new CricketTeamMinStatsVo();
							ObjectTransformUtil.transformGivenFields(stats, statsVo, true, CricketTeamMinStatsVo.class.getDeclaredFields());
							teamVo.getStatus().add(statsVo);
						}
						teamVos.add(teamVo);
					}
				}
			}
		} catch (Exception e) {
			return teamVos;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return teamVos;
	}


	private void getTeamMetaInfo(CricketTeamResponseVo teamVo, CricketTeam team, Session session) {
		Set<CricketTeamStatsVo> teamtStatusVos =  fillTeamStatus(team);
		List<CricketTeamPlayersVo> teamPlayersVo =  fillTeamPlayers(team, session);
		teamVo.setTeamStatsVo(teamtStatusVos);
		teamVo.setTeamPlayersVo(teamPlayersVo);
	}

	/**
	 * Fill the Team Status
	 * @param team
	 * @return
	 */
	private Set<CricketTeamStatsVo> fillTeamStatus(CricketTeam team) {
		Set<CricketTeamStats> teamStatusList = team.getTeamStats();
		if (teamStatusList != null && !teamStatusList.isEmpty()) {
			Set<CricketTeamStatsVo> teamtStatusVos = new HashSet<CricketTeamStatsVo>();
			for (CricketTeamStats teamStatus : teamStatusList) {
				CricketTeamStatsVo teamtStatusVo = new CricketTeamStatsVo();
				ObjectTransformUtil.transform(teamStatus, teamtStatusVo);
				teamtStatusVo.setTeamId(team.getTeamId());
				int winPercent = 0;
				if (teamStatus.getMatches() > 0) {
					winPercent = (teamStatus.getWon()/teamStatus.getMatches()) * 100;
				}
				teamtStatusVo.setWinPercent(winPercent);
				teamtStatusVos.add(teamtStatusVo);
			}
			return teamtStatusVos;
		}
		return null;
	}
	
	/**
	 * Fill the Team Status
	 * @param team
	 * @return
	 */
	private List<CricketTeamPlayersVo> fillTeamPlayers(CricketTeam team, Session session) {
		List<CricketTeamPlayers> teamPlayersList = team.getTeamPlayers();
		if (teamPlayersList != null && !teamPlayersList.isEmpty()) {
			List<CricketTeamPlayersVo> teamtPlayerVos = new ArrayList<CricketTeamPlayersVo>();
			for (CricketTeamPlayers teamPlayer : teamPlayersList) {
				if (teamPlayer.getStatus() != null && TeamPlayerStatusEnum.ACCEPTED.getId().toString().equals(teamPlayer.getStatus())) {
					CricketTeamPlayersVo teamPlayerVo = new CricketTeamPlayersVo();
					ObjectTransformUtil.transform(teamPlayer, teamPlayerVo);
					teamPlayerVo.setTeamId(team.getTeamId());
					UserInfoVo userInfo = new UserInfoVo();
					CricketProfile profile = teamPlayer.getCricketProfile();
					teamPlayerVo.setCricketProfileId(profile.getCricketProfileId());
					User user = profile.getUser();
					ObjectTransformUtil.transformGivenFields(user, userInfo, true, User.class.getDeclaredFields());
					teamPlayerVo.setUserInfo(userInfo);
					teamPlayerVo.setBattingStats(userProfileProcess.fillBattingStats(session, profile));
					teamPlayerVo.setBowlingStats(userProfileProcess.fillBowlingStats(session, profile));
					teamtPlayerVos.add(teamPlayerVo);
				}
			}
			return teamtPlayerVos;
		}
		return null;
	}
	
	/**
	 * Add Member to a Team
	 * @param memberInfo
	 * @param joinTeam if send true then action was performed by user to join a team
	 * if the value is false then team owner/moderator initiated an addition of user to their team
	 * @return
	 */
	public AddMemberToTeamVo addMemberToTeam(AddMemberToTeamVo memberInfo, boolean joinTeam) throws UnauthorizedException {
		
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketTeam team = cricketTeamDao.getByPrimaryKey(session, memberInfo.getTeamId());
			CricketProfile profile = null;
			if (memberInfo.getProfileId() != null && memberInfo.getProfileId() > 0) {
				profile = cricketProfileDao.getByPrimaryKey(session, memberInfo.getProfileId());
			} else if (memberInfo.getEmail() != null){
				User user = userDao.getByEmail(session, memberInfo.getEmail());
				if (user != null) {
					profile = cricketProfileDao.getByUserId(session, user.getUserId());
				}
			}
			if (team == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
				errorInfo.setErrorDesc("Team with ID " + memberInfo.getTeamId() + " Not Found");
				memberInfo.getErrors().add(errorInfo);
			} 
			
			if (profile == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("Profile with ID " + memberInfo.getProfileId() + " Not Found");
				memberInfo.getErrors().add(errorInfo);
			} 
			if (!memberInfo.getErrors().isEmpty()) {
				return memberInfo;
			}
			
			if (joinTeam) {
				CricketProfile signedInCricketProfile = cricketProfileDao.getByUserId(session, memberInfo.getSignedInUserId());
				Integer profileId = signedInCricketProfile.getCricketProfileId();
				if (!memberInfo.getProfileId().equals(profileId)) {
					throw new UnauthorizedException("Unauthorized to make this action");
				}
				
				addMemberByPlayer(memberInfo, session,team, profile);
			} else {
				validateSignedinUserAuthorization(memberInfo, session);
				if (!memberInfo.getErrors().isEmpty()) {
					return memberInfo;
				}
				addMemberByTeam(memberInfo, session,team, profile);
			}
			
		} catch  (UnauthorizedException e){
			throw e;
		} catch (Exception e) {
			
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service Error" + e.getMessage());
			memberInfo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return memberInfo;
		
	}

	/**
	 * Addition requested by Team
	 * @param memberInfo
	 * @param session
	 * @param team
	 * @param profile
	 */
	private void addMemberByTeam(AddMemberToTeamVo memberInfo,
			 Session session,CricketTeam team, CricketProfile profile) {
		Integer teamId = memberInfo.getTeamId();
		Integer profileId = memberInfo.getProfileId();
		CricketTeamPlayers teamProfileInfo = cricketTeamPlayerDao.getByProfileIdForTeam(session, profileId, teamId);
		if (teamProfileInfo != null) {
			if (TeamPlayerStatusEnum.ACCEPTED.getId().toString().equals(teamProfileInfo.getStatus())) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_ALREADY_PART_OF_TEAM);
				errorInfo.setErrorDesc("User is already added");
				memberInfo.getErrors().add(errorInfo);
				return;
			}
			if (TeamPlayerStatusEnum.BLOCKED_BY_PLAYER.getId().toString().equals(teamProfileInfo.getStatus())) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.BLOCKED_BY_USER);
				errorInfo.setErrorDesc("The request profile has blocked the team");
				memberInfo.getErrors().add(errorInfo);
				return;
			}
		   
			if (TeamPlayerStatusEnum.REQ_BY_PLAYER.getId().toString().equals(teamProfileInfo.getStatus())) {
				if (memberInfo.isBlocked()) {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.BLOCKED_BY_TEAM.getId().toString());
				} else if (memberInfo.isRejected()) {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.REJECTED_BY_TEAM.getId().toString());
				} else {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.ACCEPTED.getId().toString());
				}
			} else {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.REQ_BY_TEAM.getId().toString());
			}
			teamProfileInfo.setUpdatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setUpdatedTime(Calendar.getInstance().getTime());
			session.getTransaction().begin();
			cricketTeamPlayerDao.update(session, teamProfileInfo);
			session.getTransaction().commit();
		} else {
			teamProfileInfo = new CricketTeamPlayers();
			Date createdTime = Calendar.getInstance().getTime();
			teamProfileInfo.setCreatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setUpdatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setCreatedTime(createdTime);
			teamProfileInfo.setUpdatedTime(createdTime);
			teamProfileInfo.setCricketProfile(profile);
			teamProfileInfo.setCricketTeam(team);
			teamProfileInfo.setIsActive(true);
			teamProfileInfo.setIsModerator(false);
			if (memberInfo.isBlocked()) {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.BLOCKED_BY_TEAM.getId().toString());
			} else {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.REQ_BY_TEAM.getId().toString());
			}
			session.getTransaction().begin();
			cricketTeamPlayerDao.insert(session, teamProfileInfo);
			session.getTransaction().commit();
		}
	}
	
	
	/**
	 * Addition requested by Player
	 * @param memberInfo
	 * @param session
	 * @param team
	 * @param profile
	 */
	private void addMemberByPlayer(AddMemberToTeamVo memberInfo,
			 Session session,CricketTeam team, CricketProfile profile) {
		Integer teamId = memberInfo.getTeamId();
		Integer profileId = memberInfo.getProfileId();
		CricketTeamPlayers teamProfileInfo = cricketTeamPlayerDao.getByProfileIdForTeam(session, profileId, teamId);
		if (teamProfileInfo != null) {
			if (TeamPlayerStatusEnum.ACCEPTED.getId().toString().equals(teamProfileInfo.getStatus())) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_ALREADY_PART_OF_TEAM);
				errorInfo.setErrorDesc("User is already added");
				memberInfo.getErrors().add(errorInfo);
				return;
			}
		   
			if (TeamPlayerStatusEnum.BLOCKED_BY_TEAM.getId().toString().equals(teamProfileInfo.getStatus())) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.BLOCKED_BY_TEAM);
				errorInfo.setErrorDesc("The requested team has blocked the user");
				memberInfo.getErrors().add(errorInfo);
				return;
			}
		   
			if (TeamPlayerStatusEnum.REQ_BY_TEAM.getId().toString().equals(teamProfileInfo.getStatus())) {
				if (memberInfo.isBlocked()) {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.BLOCKED_BY_PLAYER.getId().toString());
				} else if (memberInfo.isRejected()) {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.REJECTED_BY_PLAYER.getId().toString());
				} else {
					teamProfileInfo.setStatus(TeamPlayerStatusEnum.ACCEPTED.getId().toString());
				}
				
			} else {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.REQ_BY_PLAYER.getId().toString());
			}
			teamProfileInfo.setUpdatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setUpdatedTime(Calendar.getInstance().getTime());
			session.getTransaction().begin();
			cricketTeamPlayerDao.update(session, teamProfileInfo);
			session.getTransaction().commit();
		} else {
			teamProfileInfo = new CricketTeamPlayers();
			Date createdTime = Calendar.getInstance().getTime();
			teamProfileInfo.setCreatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setUpdatedId(memberInfo.getSignedInUserId());
			teamProfileInfo.setCreatedTime(createdTime);
			teamProfileInfo.setUpdatedTime(createdTime);
			teamProfileInfo.setCricketProfile(profile);
			teamProfileInfo.setCricketTeam(team);
			teamProfileInfo.setIsActive(true);
			teamProfileInfo.setIsModerator(false);
			if (memberInfo.isBlocked()) {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.BLOCKED_BY_PLAYER.getId().toString());
			} else {
				teamProfileInfo.setStatus(TeamPlayerStatusEnum.REQ_BY_PLAYER.getId().toString());
			}
			session.getTransaction().begin();
			cricketTeamPlayerDao.insert(session, teamProfileInfo);
			session.getTransaction().commit();
		}
	}
	
	/**
	 * get My teams
	 * @param signedUserId
	 * @return
	 */
	public List<CricketTeamListResponse> getTeams(Integer pid, boolean isModOnly) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			if (isModOnly) {
				return getMyOwnedTeams(pid, session);
			} else {
				return getTeams(pid, session);
			}
		} catch(Exception e) {
			
		}
		return null;
	}
	
	/**
	 * get Teams for a specific profile Id
	 * @param profileId
	 * @param session
	 * @return
	 */
	public List<CricketTeamListResponse> getMyOwnedTeams(Integer profileId,
			Session session) {
		List<CricketTeamListResponse> teams = new ArrayList<CricketTeamListResponse>();
		List<CricketTeamPlayers> teamPlayers = cricketTeamPlayerDao
				.getMyOwnedTeam(session, profileId);
		for (CricketTeamPlayers team : teamPlayers) {
			CricketTeamListResponse teamVo = new CricketTeamListResponse();
			CricketTeam cricTeam = team.getCricketTeam();
			ObjectTransformUtil.transformGivenFields(cricTeam, teamVo, true, CricketTeam.class.getDeclaredFields());
			teamVo.setEditable(team.getIsModerator());
			Set<CricketTeamStatsVo> teamtStatusVos =  fillTeamStatus(cricTeam);
			
			for (CricketTeamStatsVo stats : teamtStatusVos) {
				teamVo.setTotalMatches(teamVo.getTotalMatches() + stats.getMatches());
				teamVo.setMatchWon(teamVo.getMatchWon() + stats.getWon());
				teamVo.setMatchLost(teamVo.getMatchLost() + stats.getLost());
				
			}
			if (teamVo.getTotalMatches() > 0) {
				teamVo.setWinPercentage((teamVo.getMatchWon()/teamVo.getTotalMatches()) * 100);
			}
			teamVo.setTeamStatsVo(teamtStatusVos);
			teams.add(teamVo);
		}
		return teams;
	}

	/**
	 * get Teams for a specific profile Id
	 * @param profileId
	 * @param session
	 * @return
	 */
	public List<CricketTeamListResponse> getTeams(Integer profileId,
			Session session) {
		List<CricketTeamListResponse> teams = new ArrayList<CricketTeamListResponse>();
		List<CricketTeamPlayers> teamPlayers = cricketTeamPlayerDao
				.getByProfileIdAndStatus(session, profileId,
						TeamPlayerStatusEnum.ACCEPTED.getId().toString());
		for (CricketTeamPlayers team : teamPlayers) {
			CricketTeamListResponse teamVo = new CricketTeamListResponse();
			CricketTeam cricTeam = team.getCricketTeam();
			ObjectTransformUtil.transformGivenFields(cricTeam, teamVo, true, CricketTeam.class.getDeclaredFields());
			teamVo.setEditable(team.getIsModerator());
			Set<CricketTeamStatsVo> teamtStatusVos =  fillTeamStatus(cricTeam);
			teamVo.setTeamStatsVo(teamtStatusVos);
			teams.add(teamVo);
		}
		return teams;
	}

	/**
	 * Check if the current logged in user has authority to approve request
	 * @param memberInfo
	 * @param session
	 * @param teamId
	 * @param profileId
	 */
	private void validateSignedinUserAuthorization(AddMemberToTeamVo memberInfo,
			Session session) throws UnauthorizedException {
		CricketProfile signedInCricketProfile = cricketProfileDao.getByUserId(session, memberInfo.getSignedInUserId());
		Integer teamId = memberInfo.getTeamId();
		Integer profileId = signedInCricketProfile.getCricketProfileId();
		CricketTeamPlayers signedUserTeamPlayerInfo = cricketTeamPlayerDao.getByProfileIdForTeam(session, profileId, teamId);
		if (signedUserTeamPlayerInfo == null) {
			throw new UnauthorizedException("Unauthorized to make this action");
		}
		if (signedUserTeamPlayerInfo != null && !signedUserTeamPlayerInfo.getIsModerator()) {
			throw new UnauthorizedException("Unauthorized to make this action");
		}
	}
	
	
	public AddMemberToTeamVo addAsModerator(AddMemberToTeamVo memberInfo) throws UnauthorizedException {

		
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			/*CricketTeam team = cricketTeamDao.getByPrimaryKey(session, memberInfo.getTeamId());
			CricketProfile profile = cricketProfileDao.getByPrimaryKey(session, memberInfo.getProfileId());
			if (team == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.TEAM_NOT_FOUND);
				errorInfo.setErrorDesc("Team with ID " + memberInfo.getTeamId() + " Not Found");
				memberInfo.getErrors().add(errorInfo);
			} 
			
			if (profile == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_FOUND);
				errorInfo.setErrorDesc("Profile with ID " + memberInfo.getProfileId() + " Not Found");
				memberInfo.getErrors().add(errorInfo);
			} 
			if (!memberInfo.getErrors().isEmpty()) {
				return memberInfo;
			}
			*/
			validateSignedinUserAuthorization(memberInfo, session);
			if (!memberInfo.getErrors().isEmpty()) {
					return memberInfo;
				}
			
			Integer teamId = memberInfo.getTeamId();
			Integer profileId = memberInfo.getProfileId();
			CricketTeamPlayers teamProfileInfo = cricketTeamPlayerDao.getByProfileIdForTeamWithStatus(session, profileId, teamId, TeamPlayerStatusEnum.ACCEPTED.getId().toString());
			if (teamProfileInfo == null) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorCode(ErrorConstants.USER_NOT_PART_OF_TEAM);
				errorInfo.setErrorDesc("Profile with ID " + memberInfo.getProfileId() + " is Not Part of " + teamId);
				memberInfo.getErrors().add(errorInfo);
			}
			teamProfileInfo.setIsModerator(true);
			session.getTransaction().begin();
			cricketTeamPlayerDao.update(session, teamProfileInfo);
			session.getTransaction().commit();
			
		} catch  (UnauthorizedException e){
			throw e;
		} catch (Exception e) {
			
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Service Error" + e.getMessage());
			memberInfo.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		
		return memberInfo;
		
	}
	
	/**
	 * Remove a Team
	 * @param signedInUser
	 * @param teamId
	 * @param response
	 */
	public void removeTeam(Integer signedInUser, Integer teamId, BaseResponseVo response) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, signedInUser);
			CricketTeamPlayers teamPlayer = cricketTeamPlayerDao.getByProfileIdForTeam(session, cricketProfile.getCricketProfileId(), teamId);
			if (teamPlayer == null || !teamPlayer.getIsModerator()) {
				throw new UnauthorizedException("Not Authorized to remove the team");
			}
			session.getTransaction().begin();
			cricketTeamDao.delete(session, teamPlayer.getCricketTeam());
			session.getTransaction().commit();
			
		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(ErrorConstants.SERVICE_ERROR);
			errorInfo.setErrorDesc("Internal Error occured" + e.getMessage());
			response.getErrors().add(errorInfo);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
