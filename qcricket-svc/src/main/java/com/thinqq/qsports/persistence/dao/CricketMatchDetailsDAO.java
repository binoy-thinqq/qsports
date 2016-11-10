package com.thinqq.qsports.persistence.dao;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.CricketInningsDetails;

public class CricketMatchDetailsDAO extends AbstractDAO<CricketInningsDetails> {
private static CricketMatchDetailsDAO instance = new CricketMatchDetailsDAO(); 
	
	@Override
	public CricketInningsDetails getByPrimaryKey(Session session, Integer pk) {
		return (CricketInningsDetails)session.get(CricketInningsDetails.class, pk);
	}
	
	static CricketMatchDetailsDAO getInstance() {
		return instance;
	}
}
