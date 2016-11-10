package com.thinqq.qsports.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;

public class BowlingStatisticsDAO extends AbstractDAO<BowlingStatistics>{

	private static BowlingStatisticsDAO instance = new BowlingStatisticsDAO(); 
	
	@Override
	public BowlingStatistics getByPrimaryKey(Session session, Integer pk) {
		return (BowlingStatistics)session.get(BowlingStatistics.class, pk);
	}
	
	public List<BowlingStatistics> getByCricketProfileId(Session session, Integer cricketProfileId) {
		Criteria  findByUserId = session.createCriteria(BowlingStatistics.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", cricketProfileId ));
		List<BowlingStatistics> battingStats = findByUserId.list();
		return battingStats;
	}
	
	public BowlingStatistics getByCricketProfileIdAndFormat(Session session, Integer cricketProfileId, Integer format) {
		Criteria  findByUserId = session.createCriteria(BowlingStatistics.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", cricketProfileId ));
		findByUserId.add(Restrictions.eq("cricketFormat", format ));
		BowlingStatistics bowlingStat = (BowlingStatistics)findByUserId.uniqueResult();
		return bowlingStat;
	}

	static BowlingStatisticsDAO getInstance() {
		return instance;
	}

}
