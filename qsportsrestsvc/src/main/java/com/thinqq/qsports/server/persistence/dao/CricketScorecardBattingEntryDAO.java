package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketScorecardBattingEntry;

/**
 * @author Subramaniam
 * 
 */
public class CricketScorecardBattingEntryDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketScorecardBattingEntryDAO.class.getName());

	public CricketScorecardBattingEntry insert(CricketScorecardBattingEntry cricketScorecardBattingEntry, PersistenceManager pm) {
		try {
			CricketScorecardBattingEntry cricketScorecardBattingEntryAfterInsertion = pm.makePersistent(cricketScorecardBattingEntry);
			CricketScorecardBattingEntry detachedCopy = (CricketScorecardBattingEntry) pm.detachCopy(cricketScorecardBattingEntryAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBattingEntry Insertion Failed", ex);
		} 
		return null;
	}

	public CricketScorecardBattingEntry getCricketScorecardBattingEntryByCricketScorecardBattingEntryId(Long id, PersistenceManager pm) {
		try {
			CricketScorecardBattingEntry user = pm.getObjectById(CricketScorecardBattingEntry.class, id);
			CricketScorecardBattingEntry detachedCopy = (CricketScorecardBattingEntry) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBattingEntry Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketScorecardBattingEntry cricketScorecardBattingEntry, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketScorecardBattingEntry);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBattingEntry Deletion Failed", ex);
		} 
	}

	public CricketScorecardBattingEntry update(CricketScorecardBattingEntry cricketScorecardBattingEntry, PersistenceManager pm) {		
		try {
			CricketScorecardBattingEntry cricketScorecardBattingEntryAfterUpdate = pm.makePersistent(cricketScorecardBattingEntry);
			CricketScorecardBattingEntry detachedCopy = (CricketScorecardBattingEntry) pm.detachCopy(cricketScorecardBattingEntryAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketScorecardBattingEntry Updation Failed", ex);
		} 
		return null;
	}
}
