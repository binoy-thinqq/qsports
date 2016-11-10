package com.thinqq.qsports.persistence.dao;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.MatchFow;

public class MatchFowDAO  extends AbstractDAO<MatchFow>{

	private static MatchFowDAO instance = new MatchFowDAO(); 
	
	@Override
	public MatchFow getByPrimaryKey(Session session, Integer pk) {
		return (MatchFow)session.get(MatchFow.class, pk);
	}

	static MatchFowDAO getInstance() {
		return instance;
	}

}
