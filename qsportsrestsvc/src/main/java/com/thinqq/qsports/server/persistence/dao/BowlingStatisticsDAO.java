package com.thinqq.qsports.server.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BowlingStatistics;

/**
 * @author Subramaniam
 * 
 */
public class BowlingStatisticsDAO extends DAO{
	private static Logger logger = Logger.getLogger(BowlingStatisticsDAO.class.getName());

	public BowlingStatistics insert(BowlingStatistics bowlingStatistics, PersistenceManager pm) {
		try {
			BowlingStatistics bowlingStatisticsAfterInsertion = pm.makePersistent(bowlingStatistics);
			BowlingStatistics detachedCopy = (BowlingStatistics) pm.detachCopy(bowlingStatisticsAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingStatistics Insertion Failed", ex);
		} 
		return null;
	}

	public BowlingStatistics getBowlingStatisticsByBowlingStatisticsId(Long id, PersistenceManager pm) {
		try {
			BowlingStatistics user = pm.getObjectById(BowlingStatistics.class, id);
			BowlingStatistics detachedCopy = (BowlingStatistics) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingStatistics Not Found", ex);
		} 
		return null;
	}

	public void delete(BowlingStatistics bowlingStatistics, PersistenceManager pm) {
		try {
			pm.deletePersistent(bowlingStatistics);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingStatistics Deletion Failed", ex);
		} 
	}

	public BowlingStatistics update(BowlingStatistics bowlingStatistics, PersistenceManager pm) {		
		try {
			BowlingStatistics bowlingStatisticsAfterUpdate = pm.makePersistent(bowlingStatistics);
			BowlingStatistics detachedCopy = (BowlingStatistics) pm.detachCopy(bowlingStatisticsAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingStatistics Updation Failed", ex);
		} 
		return null;
	}
	
	public List<BowlingStatistics> getBowlingStatisticsForCricketProfile(String cricketProfile, PersistenceManager pm){
		return getBowlingStatisticsForCricketProfile(KeyFactory.stringToKey(cricketProfile),pm);
	}
		
	public List<BowlingStatistics> getBowlingStatisticsForCricketProfile(Key cricketProfile, PersistenceManager pm){
		try {
			Query q = pm.newQuery(BowlingStatistics.class);
			q.setFilter("cricketProfile == cricketProfileParam");
			q.declareParameters(Key.class.getName() + " cricketProfileParam");
			@SuppressWarnings(value = { "unchecked" })
			List<BowlingStatistics> allBattingStatistics = (List<BowlingStatistics>) q.execute(cricketProfile);
			return allBattingStatistics;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Updation Failed", ex);
		} 
		return new ArrayList<BowlingStatistics>();
	}
}
