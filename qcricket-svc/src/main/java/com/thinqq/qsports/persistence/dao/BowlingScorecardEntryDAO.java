package com.thinqq.qsports.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.BowlingScorecardEntry;

public class BowlingScorecardEntryDAO extends AbstractDAO<BowlingScorecardEntry>{

	private static BowlingScorecardEntryDAO instance = new BowlingScorecardEntryDAO(); 
	
	@Override
	public BowlingScorecardEntry getByPrimaryKey(Session session, Integer pk) {
		return (BowlingScorecardEntry)session.get(BowlingScorecardEntry.class, pk);
	}
	
	public BowlingScorecardEntry getByPlayerAndInningsId(Session session, Integer inningsId, Integer matchPlayerId) {
		Criteria findEntry = session.createCriteria(BowlingScorecardEntry.class);
		findEntry.add(Restrictions.eq("player.matchTeamPlayerId", matchPlayerId));
		findEntry.add(Restrictions.eq("innings.inningsId", inningsId));
		BowlingScorecardEntry entry = (BowlingScorecardEntry)findEntry.uniqueResult();
		return entry;
	}

	static BowlingScorecardEntryDAO getInstance() {
		return instance;
	}

}