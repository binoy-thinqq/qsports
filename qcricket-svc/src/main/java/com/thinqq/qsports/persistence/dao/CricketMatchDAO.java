package com.thinqq.qsports.persistence.dao;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.CricketMatch;

public class CricketMatchDAO extends AbstractDAO<CricketMatch>{

	private static CricketMatchDAO instance = new CricketMatchDAO(); 
	
	@Override
	public CricketMatch getByPrimaryKey(Session session, Integer pk) {
		return (CricketMatch)session.get(CricketMatch.class, pk);
	}

	static CricketMatchDAO getInstance() {
		return instance;
	}

}