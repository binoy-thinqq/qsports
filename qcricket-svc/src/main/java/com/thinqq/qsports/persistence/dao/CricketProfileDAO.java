package com.thinqq.qsports.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketProfile;

public class CricketProfileDAO extends AbstractDAO<CricketProfile>{

	private static CricketProfileDAO instance = new CricketProfileDAO(); 
	
	@Override
	public CricketProfile getByPrimaryKey(Session session, Integer pk) {
		return (CricketProfile)session.get(CricketProfile.class, pk);
	}
	
	public CricketProfile getByUserId(Session session, Integer userId) {
		Criteria  findByUserId = session.createCriteria(CricketProfile.class);
		findByUserId.add(Restrictions.eq("user.userId", userId ));
		CricketProfile cricketProfile = (CricketProfile)findByUserId.uniqueResult();
		return cricketProfile;
	}

	static CricketProfileDAO getInstance() {
		return instance;
	}

}
