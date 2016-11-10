package com.thinqq.qsports.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.BattingScorecardEntry;
import com.thinqq.qsports.persistence.dataobjects.BowlingScorecardEntry;

public class BattingScorecardEntryDAO extends AbstractDAO<BattingScorecardEntry>{

	private static BattingScorecardEntryDAO instance = new BattingScorecardEntryDAO(); 
	
	@Override
	public BattingScorecardEntry getByPrimaryKey(Session session, Integer pk) {
		return (BattingScorecardEntry)session.get(BattingScorecardEntry.class, pk);
	}
	
	public BattingScorecardEntry getByPlayerAndInningsId(Session session, Integer inningsId, Integer matchPlayerId) {
		Criteria findEntry = session.createCriteria(BattingScorecardEntry.class);
		findEntry.add(Restrictions.eq("player.matchTeamPlayerId", matchPlayerId));
		findEntry.add(Restrictions.eq("innings.inningsId", inningsId));
		BattingScorecardEntry entry = (BattingScorecardEntry)findEntry.uniqueResult();
		return entry;
	}

	static BattingScorecardEntryDAO getInstance() {
		return instance;
	}

}