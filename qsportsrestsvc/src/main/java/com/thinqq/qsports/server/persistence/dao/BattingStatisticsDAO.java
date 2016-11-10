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
import com.thinqq.qsports.server.pesistence.dataobjects.BattingStatistics;

/**
 * @author Subramaniam
 * 
 */
public class BattingStatisticsDAO extends DAO{
	private static Logger logger = Logger.getLogger(BattingStatisticsDAO.class.getName());

	public BattingStatistics insert(BattingStatistics battingStatistics, PersistenceManager pm) {
		try {
			BattingStatistics battingStatisticsAfterInsertion = pm.makePersistent(battingStatistics);
			BattingStatistics detachedCopy = (BattingStatistics) pm.detachCopy(battingStatisticsAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Insertion Failed", ex);
		} 
		return null;
	}

	public BattingStatistics getBattingStatisticsByBattingStatisticsId(Long id, PersistenceManager pm) {
		try {
			BattingStatistics user = pm.getObjectById(BattingStatistics.class, id);
			BattingStatistics detachedCopy = (BattingStatistics) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Not Found", ex);
		} 
		return null;
	}

	public void delete(BattingStatistics battingStatistics, PersistenceManager pm) {
		try {
			pm.deletePersistent(battingStatistics);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Deletion Failed", ex);
		} 
	}

	public BattingStatistics update(BattingStatistics battingStatistics, PersistenceManager pm) {		
		try {
			BattingStatistics battingStatisticsAfterUpdate = pm.makePersistent(battingStatistics);
			BattingStatistics detachedCopy = (BattingStatistics) pm.detachCopy(battingStatisticsAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Updation Failed", ex);
		} 
		return null;
	}
	
	public List<BattingStatistics> getBattingStatisticsForCricketProfile(String cricketProfile, PersistenceManager pm){
		return getBattingStatisticsForCricketProfile(KeyFactory.stringToKey(cricketProfile), pm);
	}
	public List<BattingStatistics> getBattingStatisticsForCricketProfile(Key cricketProfile, PersistenceManager pm){
				try {
			Query q = pm.newQuery(BattingStatistics.class);
			q.setFilter("cricketProfile == cricketProfileParam");
			q.declareParameters(Key.class.getName() + " cricketProfileParam");
			@SuppressWarnings("unchecked")
			List<BattingStatistics> allBattingStatistics = (List<BattingStatistics>) q.execute(cricketProfile);
			return allBattingStatistics;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingStatistics Updation Failed", ex);
		} 
		return new ArrayList<BattingStatistics>();
	}
}
