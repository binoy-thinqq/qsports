package com.thinqq.qsports.persistence.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketTeam;
import com.thinqq.qsports.persistence.dataobjects.Innings;

public class InningsDAO extends AbstractDAO<Innings>{

	private static InningsDAO instance = new InningsDAO(); 
	
	@Override
	public Innings getByPrimaryKey(Session session, Integer pk) {
		return (Innings)session.get(Innings.class, pk);
	}
	
	public List<Innings> getInningsForMatch(Session session, Integer matchId) {
		Criteria findByMatchId = session.createCriteria(Innings.class);
		findByMatchId.add(Restrictions.eq("match.matchId", matchId));
		findByMatchId.addOrder(Order.asc("order"));
		List inningsDo = findByMatchId.list();
		List<Innings> innings = new ArrayList<Innings>();
		Iterator it = inningsDo.iterator();
		while (it.hasNext()) {
			Innings inning = (Innings) it.next();
			innings.add(inning);
		}
		return innings;
	}

	static InningsDAO getInstance() {
		return instance;
	}

}