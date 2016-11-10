package com.thinqq.qsports.persistence.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketTeam;

public class CricketTeamDAO extends AbstractDAO<CricketTeam> {

	private static CricketTeamDAO instance = new CricketTeamDAO(); 
	
	@Override
	public CricketTeam getByPrimaryKey(Session session, Integer pk) {
		return (CricketTeam)session.get(CricketTeam.class, pk);
	}
	
	public List<CricketTeam> getByTeamIds(Session session, Set<Integer> teamIds) {
		Criteria  findByTeam = session.createCriteria(CricketTeam.class);
		findByTeam.add(Restrictions.in("teamId", teamIds ));
		List teams = findByTeam.list();
		List<CricketTeam> cricketTeams = new ArrayList<CricketTeam>();
		 Iterator it = teams.iterator();
		while(it.hasNext()) {
			CricketTeam team = (CricketTeam)it.next();
			cricketTeams.add(team);
		}
		return cricketTeams;
	}
	
	static CricketTeamDAO getInstance() {
		return instance;
	}

}
