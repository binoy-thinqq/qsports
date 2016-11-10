package com.thinqq.qsports.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketMatchExt;

public class CricketMatchExtDAO  extends AbstractDAO<CricketMatchExt>{

	private static CricketMatchExtDAO instance = new CricketMatchExtDAO(); 
	
	@Override
	public CricketMatchExt getByPrimaryKey(Session session, Integer pk) {
		return (CricketMatchExt)session.get(CricketMatchExt.class, pk);
	}
	
	
	public CricketMatchExt getByMatchId(Session session, Integer matchId) {
		Criteria findEntry = session.createCriteria(CricketMatchExt.class);
		findEntry.add(Restrictions.eq("match.matchId", matchId));
		CricketMatchExt entry = (CricketMatchExt)findEntry.uniqueResult();
		return entry;
	}


	static CricketMatchExtDAO getInstance() {
		return instance;
	}

}
