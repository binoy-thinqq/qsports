package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecardBowlingEntry;

/**
 * @author Subramaniam
 * 
 */
public class CricketScorecardBowlingEntryDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketScorecardBowlingEntryDAO.class.getName());

	public CricketScorecardBowlingEntry insert(CricketScorecardBowlingEntry cricketScorecardBowlingEntry, PersistenceManager pm) {
		try {
			CricketScorecardBowlingEntry cricketScorecardBowlingEntryAfterInsertion = pm.makePersistent(cricketScorecardBowlingEntry);
			CricketScorecardBowlingEntry detachedCopy = (CricketScorecardBowlingEntry) pm.detachCopy(cricketScorecardBowlingEntryAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBowlingEntry Insertion Failed", ex);
		} 
		return null;
	}

	public CricketScorecardBowlingEntry getCricketScorecardBowlingEntryByCricketScorecardBowlingEntryId(Long id, PersistenceManager pm) {
		try {
			CricketScorecardBowlingEntry user = pm.getObjectById(CricketScorecardBowlingEntry.class, id);
			CricketScorecardBowlingEntry detachedCopy = (CricketScorecardBowlingEntry) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBowlingEntry Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketScorecardBowlingEntry cricketScorecardBowlingEntry, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketScorecardBowlingEntry);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBowlingEntry Deletion Failed", ex);
		} 
	}

	public CricketScorecardBowlingEntry update(CricketScorecardBowlingEntry cricketScorecardBowlingEntry, PersistenceManager pm) {		
		try {
			CricketScorecardBowlingEntry cricketScorecardBowlingEntryAfterUpdate = pm.makePersistent(cricketScorecardBowlingEntry);
			CricketScorecardBowlingEntry detachedCopy = (CricketScorecardBowlingEntry) pm.detachCopy(cricketScorecardBowlingEntryAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBowlingEntry Updation Failed", ex);
		} 
		return null;
	}
}
