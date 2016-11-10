package com.thinqq.qsports.server.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.CricketProfileDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.persistence.dao.CricketTeamPlayersDAO;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dto.NotificationVo;
import com.thinqq.qsports.server.constants.NotificationDataKeyConstants;
import com.thinqq.qsports.server.constants.NotificationTypeEnum;
import com.thinqq.qsports.shared.cricket.TeamPlayerStatusEnum;

public class NotificationProcess {
	
	

	private Logger logger = Logger.getLogger(NotificationProcess.class
			.getName());
	
	@Autowired
	CricketTeamPlayersDAO cricketTeamPlayersDao;
	
	@Autowired
	CricketProfileDAO cricketProfileDao;
	
	@Autowired
	CricketTeamDAO cricketTeamDao;
	
	public List<NotificationVo> getNotifications(Integer signedInUserId) {
		Session session = null;
		try {
			session = SessionManager.getSessionFactory().openSession();
			CricketProfile cricketProfile = cricketProfileDao.getByUserId(session, signedInUserId);
			List<NotificationVo> notificationResult = new ArrayList<NotificationVo>();
			//get Team Request Notification
			getRequestedByTeamNotificationData(session, cricketProfile.getCricketProfileId(), notificationResult);
			
			//get Member approval Request Notification
			getRequestForAdditionRequest(session, cricketProfile.getCricketProfileId(), notificationResult);
			
		    return notificationResult;
		} catch (Exception e) {
			logger.severe("Failed to fetch notifications" + e.getStackTrace());
		}
		return null;
	}
	
	/**
	 * Get the notifications requested by team to join the team.
	 * @param signedInUserId
	 * @param notificationResult
	 */
	private void getRequestedByTeamNotificationData(Session session, Integer profileId, List<NotificationVo> notificationResult) {
		List<CricketTeamPlayers> requestList = cricketTeamPlayersDao.getByProfileIdAndStatus(session, profileId, TeamPlayerStatusEnum.REQ_BY_TEAM.getId().toString());
		for (CricketTeamPlayers teamPlayer : requestList) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(NotificationDataKeyConstants.TEAM_ID, teamPlayer.getCricketTeam().getTeamId());
			data.put(NotificationDataKeyConstants.TEAM_NAME, teamPlayer.getCricketTeam().getTeamName());
			NotificationVo notificationVo = new NotificationVo();
			notificationVo.setNotificationType(NotificationTypeEnum.JOIN_TEAM_REQUEST.getType());
			notificationVo.setNotificationData(data);
			notificationResult.add(notificationVo);
		}
	}
	
	/**
	 * Get the notifications requested by team to join the team.
	 * @param signedInUserId
	 * @param notificationResult
	 */
	private void getRequestForAdditionRequest(Session session, Integer profileId, List<NotificationVo> notificationResult) {
		List<CricketTeamPlayers> ownedTeamList = cricketTeamPlayersDao.getMyOwnedTeam(session, profileId);
		for (CricketTeamPlayers ownedTeam : ownedTeamList) {
			List<CricketTeamPlayers> joinTeamRequestList = cricketTeamPlayersDao.getByTeamIdAndStatus(session, ownedTeam.getCricketTeam().getTeamId(), TeamPlayerStatusEnum.REQ_BY_PLAYER.getId().toString());
			for (CricketTeamPlayers joinTeamRequest : joinTeamRequestList) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(NotificationDataKeyConstants.TEAM_ID, joinTeamRequest.getCricketTeam().getTeamId());
				data.put(NotificationDataKeyConstants.TEAM_NAME, joinTeamRequest.getCricketTeam().getTeamName());
				data.put(NotificationDataKeyConstants.PROFILE_ID, joinTeamRequest.getCricketProfile().getCricketProfileId());
				data.put(NotificationDataKeyConstants.PROFILE_NAME,	joinTeamRequest.getCricketProfile().getUser().getFirstName()
								+ joinTeamRequest.getCricketProfile().getUser()	.getLastName());
				NotificationVo notificationVo = new NotificationVo();
				notificationVo.setNotificationType(NotificationTypeEnum.ADD_TO_TEAM_REQUEST.getType());
				notificationVo.setNotificationData(data);
				notificationResult.add(notificationVo);
			}
		}
	}

}
