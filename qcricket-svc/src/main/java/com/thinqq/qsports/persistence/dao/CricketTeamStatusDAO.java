package com.thinqq.qsports.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamStats;

public class CricketTeamStatusDAO extends AbstractDAO<CricketTeamStats> {

	private static CricketTeamStatusDAO instance = new CricketTeamStatusDAO(); 
	
	@Override
	public CricketTeamStats getByPrimaryKey(Session session, Integer pk) {
		return (CricketTeamStats)session.get(CricketTeamStats.class, pk);
	}
	
	public List<CricketTeamStats> getByTeamId(Session session, Integer teamId) {
		Criteria  findByUserId = session.createCriteria(CricketProfile.class);
		findByUserId.add(Restrictions.eq("cricketTeam.teamId", teamId ));
		List<CricketTeamStats> cricketProfile = (List<CricketTeamStats>)findByUserId.list();
		return cricketProfile;
	}
	
	public CricketTeamStats getByTeamAndFormat(Session session, Integer teamId, Integer format) {
		Criteria  findTeamStats = session.createCriteria(CricketTeamStats.class);
		findTeamStats.add(Restrictions.eq("cricketTeam.teamId", teamId ));
		findTeamStats.add(Restrictions.eq("cricketFormat", format ));
		CricketTeamStats teamStats = (CricketTeamStats)findTeamStats.uniqueResult();
		return teamStats;
	}
	
	static CricketTeamStatusDAO getInstance() {
		return instance;
	}
}
