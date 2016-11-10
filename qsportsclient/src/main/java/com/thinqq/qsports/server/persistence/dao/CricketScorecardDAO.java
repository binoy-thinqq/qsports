package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecard;

/**
 * @author Subramaniam
 * 
 */
public class CricketScorecardDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketScorecardDAO.class.getName());

	public CricketScorecard insert(CricketScorecard cricketScorecard, PersistenceManager pm) {
		try {
			CricketScorecard cricketScorecardAfterInsertion = pm.makePersistent(cricketScorecard);
			CricketScorecard detachedCopy = (CricketScorecard) pm.detachCopy(cricketScorecardAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecard Insertion Failed", ex);
		} 
		return null;
	}

	public CricketScorecard getCricketScorecardByCricketScorecardId(Long id, PersistenceManager pm) {
		try {
			CricketScorecard user = pm.getObjectById(CricketScorecard.class, id);
			CricketScorecard detachedCopy = (CricketScorecard) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecard Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketScorecard cricketScorecard, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketScorecard);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecard Deletion Failed", ex);
		} 
	}

	public CricketScorecard update(CricketScorecard cricketScorecard, PersistenceManager pm) {		
		try {
			CricketScorecard cricketScorecardAfterUpdate = pm.makePersistent(cricketScorecard);
			CricketScorecard detachedCopy = (CricketScorecard) pm.detachCopy(cricketScorecardAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecard Updation Failed", ex);
		} 
		return null;
	}
}
