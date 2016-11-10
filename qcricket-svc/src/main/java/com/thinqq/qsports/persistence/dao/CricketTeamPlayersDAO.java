package com.thinqq.qsports.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.shared.cricket.TeamPlayerStatusEnum;

public class CricketTeamPlayersDAO  extends AbstractDAO<CricketTeamPlayers> {

	private static CricketTeamPlayersDAO instance = new CricketTeamPlayersDAO(); 
	
	@Override
	public CricketTeamPlayers getByPrimaryKey(Session session, Integer pk) {
		return (CricketTeamPlayers)session.get(CricketTeamPlayers.class, pk);
	}
	
	public List<CricketTeamPlayers> getByProfileId(Session session, Integer profileId) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", profileId ));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>)findByUserId.list();
		return cricketProfile;
	}
	
	public List<CricketTeamPlayers> getByTeamId(Session session, Integer teamId) {
		Criteria  findByTeamId = session.createCriteria(CricketTeamPlayers.class);
		findByTeamId.add(Restrictions.eq("cricketTeam.teamId", teamId));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>)findByTeamId.list();
		return cricketProfile;
	}
	
	public CricketTeamPlayers getByProfileIdForTeam(Session session, Integer profileId, Integer teamId) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", profileId ));
		findByUserId.add(Restrictions.eq("cricketTeam.teamId", teamId ));
		CricketTeamPlayers cricketProfile = (CricketTeamPlayers)findByUserId.uniqueResult();
		return cricketProfile;
	}
	
	public CricketTeamPlayers getByProfileIdForTeamWithStatus(Session session, Integer profileId, Integer teamId, String status) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", profileId));
		findByUserId.add(Restrictions.eq("cricketTeam.teamId", teamId));
		findByUserId.add(Restrictions.eq("status", status));
		findByUserId.add(Restrictions.eq("isActive", true));
		CricketTeamPlayers cricketProfile = (CricketTeamPlayers)findByUserId.uniqueResult();
		return cricketProfile;
	}
	
	
	public List<CricketTeamPlayers> getByProfileIdAndStatus(Session session, Integer profileId, String status) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", profileId ));
		findByUserId.add(Restrictions.eq("status", status ));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>) findByUserId.list();
		return cricketProfile;
	}
	
	public List<CricketTeamPlayers> getByTeamIdAndStatus(Session session, Integer teamId, String status) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketTeam.teamId", teamId));
		findByUserId.add(Restrictions.eq("status", status ));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>) findByUserId.list();
		return cricketProfile;
	}
	
	public List<CricketTeamPlayers> getMyOwnedTeam(Session session, Integer profileId) {
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", profileId ));
		findByUserId.add(Restrictions.eq("status", TeamPlayerStatusEnum.ACCEPTED.getId().toString()));
		findByUserId.add(Restrictions.eq("isModerator", Boolean.TRUE));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>) findByUserId.list();
		return cricketProfile;
	}
	
	/**
	 * 
	 * TODO: Implement in a better now firing Two queries
	 * @param session
	 * @param baseProfileId
	 * @param checkProfileId
	 * @return
	 */
	public List<CricketTeamPlayers> getCommonTeamPlayerRecord(Session session, Integer baseProfileId, Integer checkProfileId) {
		//Part one: get all the team players for the baseProfileId
		List<CricketTeamPlayers> allTeamsList = getByProfileIdAndStatus(session, checkProfileId, TeamPlayerStatusEnum.ACCEPTED.getId().toString());
		List<Integer> teamIds = new ArrayList<Integer>();
		for (CricketTeamPlayers playerTeam: allTeamsList) {
			teamIds.add(playerTeam.getCricketTeam().getTeamId());
		}
		//Part two: get CommonTeamPlayerRecord
		Criteria  findByUserId = session.createCriteria(CricketTeamPlayers.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", baseProfileId ));
		findByUserId.add(Restrictions.eq("status", TeamPlayerStatusEnum.ACCEPTED.getId().toString()));
		findByUserId.add(Restrictions.in("cricketTeam.teamId", teamIds));
		List<CricketTeamPlayers> cricketProfile = (List<CricketTeamPlayers>) findByUserId.list();
		return cricketProfile;
	}
	
		
	static CricketTeamPlayersDAO getInstance() {
		return instance;
	}

}