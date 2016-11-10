package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecardFOWEntry;

/**
 * @author Subramaniam
 * 
 */
public class CricketScorecardFOWEntryDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketScorecardFOWEntryDAO.class.getName());

	public CricketScorecardFOWEntry insert(CricketScorecardFOWEntry cricketScorecardFOWEntry, PersistenceManager pm) {
		try {
			CricketScorecardFOWEntry cricketScorecardFOWEntryAfterInsertion = pm.makePersistent(cricketScorecardFOWEntry);
			CricketScorecardFOWEntry detachedCopy = (CricketScorecardFOWEntry) pm.detachCopy(cricketScorecardFOWEntryAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardFOWEntry Insertion Failed", ex);
		} 
		return null;
	}

	public CricketScorecardFOWEntry getCricketScorecardFOWEntryByCricketScorecardFOWEntryId(Long id, PersistenceManager pm) {
		try {
			CricketScorecardFOWEntry user = pm.getObjectById(CricketScorecardFOWEntry.class, id);
			CricketScorecardFOWEntry detachedCopy = (CricketScorecardFOWEntry) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardFOWEntry Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketScorecardFOWEntry cricketScorecardFOWEntry, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketScorecardFOWEntry);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardFOWEntry Deletion Failed", ex);
		} 
	}

	public CricketScorecardFOWEntry update(CricketScorecardFOWEntry cricketScorecardFOWEntry, PersistenceManager pm) {		
		try {
			CricketScorecardFOWEntry cricketScorecardFOWEntryAfterUpdate = pm.makePersistent(cricketScorecardFOWEntry);
			CricketScorecardFOWEntry detachedCopy = (CricketScorecardFOWEntry) pm.detachCopy(cricketScorecardFOWEntryAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardFOWEntry Updation Failed", ex);
		} 
		return null;
	}
}
