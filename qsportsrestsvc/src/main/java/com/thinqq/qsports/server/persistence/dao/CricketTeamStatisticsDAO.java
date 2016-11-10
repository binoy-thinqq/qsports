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
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeamStatistics;

/**
 * @author Subramaniam
 * 
 */
public class CricketTeamStatisticsDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketTeamStatisticsDAO.class.getName());

	public CricketTeamStatistics insert(CricketTeamStatistics cricketTeamStatistics, PersistenceManager pm) {
		try {
			CricketTeamStatistics cricketTeamStatisticsAfterInsertion = pm.makePersistent(cricketTeamStatistics);
			CricketTeamStatistics detachedCopy = (CricketTeamStatistics) pm.detachCopy(cricketTeamStatisticsAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeamStatistics Insertion Failed", ex);
		} 
		return null;
	}

	public CricketTeamStatistics getCricketTeamStatisticsByCricketTeamStatisticsId(Long id, PersistenceManager pm) {
		try {
			CricketTeamStatistics user = pm.getObjectById(CricketTeamStatistics.class, id);
			CricketTeamStatistics detachedCopy = (CricketTeamStatistics) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeamStatistics Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketTeamStatistics cricketTeamStatistics, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketTeamStatistics);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeamStatistics Deletion Failed", ex);
		} 
	}

	public CricketTeamStatistics update(CricketTeamStatistics cricketTeamStatistics, PersistenceManager pm) {		
		try {
			CricketTeamStatistics cricketTeamStatisticsAfterUpdate = pm.makePersistent(cricketTeamStatistics);
			CricketTeamStatistics detachedCopy = (CricketTeamStatistics) pm.detachCopy(cricketTeamStatisticsAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeamStatistics Updation Failed", ex);
		} 
		return null;
	}
	
	public List<CricketTeamStatistics> getCricketTeamStatisticsByTeamKey(String teamKey, PersistenceManager pm){
		return getCricketTeamStatisticsByTeamKey(KeyFactory.stringToKey(teamKey), pm);
	}
	
	public List<CricketTeamStatistics> getCricketTeamStatisticsByTeamKey(Key teamKey, PersistenceManager pm){
		try {
			Query q = pm.newQuery(CricketTeamStatistics.class);
			q.setFilter("cricketTeam == teamKeyParam");
			q.declareParameters(Key.class.getName() + " teamKeyParam");
			@SuppressWarnings("unchecked")
			List<CricketTeamStatistics> allTeamStatistics = (List<CricketTeamStatistics>) q.execute(teamKey);
			return allTeamStatistics;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, " Get CricketTeamStatistics Failed", ex);
		} 
		return new ArrayList<CricketTeamStatistics>();
	}
}
