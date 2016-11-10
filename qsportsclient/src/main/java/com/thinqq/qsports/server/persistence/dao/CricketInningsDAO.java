package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketInnings;

/**
 * @author Subramaniam
 * 
 */
public class CricketInningsDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketInningsDAO.class.getName());

	public CricketInnings insert(CricketInnings cricketInnings, PersistenceManager pm) {
		try {
			CricketInnings cricketInningsAfterInsertion = pm.makePersistent(cricketInnings);
			CricketInnings detachedCopy = (CricketInnings) pm.detachCopy(cricketInningsAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketInnings Insertion Failed", ex);
		} 
		return null;
	}

	public CricketInnings getCricketInningsByCricketInningsId(Long id, PersistenceManager pm) {
		try {
			CricketInnings user = pm.getObjectById(CricketInnings.class, id);
			CricketInnings detachedCopy = (CricketInnings) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketInnings Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketInnings cricketInnings, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketInnings);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketInnings Deletion Failed", ex);
		} 
	}

	public CricketInnings update(CricketInnings cricketInnings, PersistenceManager pm) {		
		try {
			CricketInnings cricketInningsAfterUpdate = pm.makePersistent(cricketInnings);
			CricketInnings detachedCopy = (CricketInnings) pm.detachCopy(cricketInningsAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketInnings Updation Failed", ex);
		} 
		return null;
	}
}
