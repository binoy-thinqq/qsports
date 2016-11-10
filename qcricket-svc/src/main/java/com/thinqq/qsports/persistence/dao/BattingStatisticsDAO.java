package com.thinqq.qsports.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;

public class BattingStatisticsDAO extends AbstractDAO<BattingStatistics>{

	private static BattingStatisticsDAO instance = new BattingStatisticsDAO(); 
	
	@Override
	public BattingStatistics getByPrimaryKey(Session session, Integer pk) {
		return (BattingStatistics)session.get(BattingStatistics.class, pk);
	}
	
	public List<BattingStatistics> getByCricketProfileId(Session session, Integer cricketProfileId) {
		Criteria  findByUserId = session.createCriteria(BattingStatistics.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", cricketProfileId ));
		List<BattingStatistics> battingStats = findByUserId.list();
		return battingStats;
	}
	
	public BattingStatistics getByCricketProfileIdAndFormat(Session session, Integer cricketProfileId, Integer format) {
		Criteria  findByUserId = session.createCriteria(BattingStatistics.class);
		findByUserId.add(Restrictions.eq("cricketProfile.cricketProfileId", cricketProfileId ));
		findByUserId.add(Restrictions.eq("cricketFormat", format ));
		BattingStatistics battingStat = (BattingStatistics)findByUserId.uniqueResult();
		return battingStat;
	}

	static BattingStatisticsDAO getInstance() {
		return instance;
	}

}
