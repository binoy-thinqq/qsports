package com.thinqq.qsports.server.persistence.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketMatch;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;

/**
 * @author Subramaniam
 * 
 */
public class CricketMatchDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketMatchDAO.class.getName());

	public CricketMatch insert(CricketMatch cricketMatch, PersistenceManager pm) {
		try {
			CricketMatch cricketMatchAfterInsertion = pm.makePersistent(cricketMatch);
			CricketMatch detachedCopy = (CricketMatch) pm.detachCopy(cricketMatchAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketMatch Insertion Failed", ex);
		} 
		return null;
	}

	public CricketMatch getCricketMatchByCricketMatchId(Long id, PersistenceManager pm) {
		try {
			CricketMatch match = pm.getObjectById(CricketMatch.class, id);
			CricketMatch detachedCopy = (CricketMatch) pm.detachCopy(match);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketMatch Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketMatch cricketMatch, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketMatch);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketMatch Deletion Failed", ex);
		} 
	}

	public CricketMatch update(CricketMatch cricketMatch, PersistenceManager pm) {		
		try {
			cricketMatch.setVersion(cricketMatch.getVersion() + 1);
			CricketMatch cricketMatchAfterUpdate = pm.makePersistent(cricketMatch);
			CricketMatch detachedCopy = (CricketMatch) pm.detachCopy(cricketMatchAfterUpdate);			
			return detachedCopy;
		} catch (Throwable ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketMatch Updation Failed", ex);
		} 
		return null;
	}
	
	public CricketMatch getCricketProfile(CricketMatch cricketMatch, PersistenceManager pm) {		
		try {
			CricketMatch cricketMatchAfterUpdate = pm.makePersistent(cricketMatch);
			CricketMatch detachedCopy = (CricketMatch) pm.detachCopy(cricketMatchAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketMatch Updation Failed", ex);
		} 
		return null;
	}
	public CricketMatch findMatchByKey(String matchKey, PersistenceManager pm) {
		return findMatchByKey(KeyFactory.stringToKey(matchKey), pm);
	}
	
	public CricketMatch findMatchByKey(Key matchKey, PersistenceManager pm) {
		Query q = pm.newQuery(CricketMatch.class);
		q.setFilter("key == matchKeyParam");
		q.declareParameters(Key.class.getName() + " matchKeyParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketMatch> results = (List<CricketMatch>)q.execute(matchKey);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
}
