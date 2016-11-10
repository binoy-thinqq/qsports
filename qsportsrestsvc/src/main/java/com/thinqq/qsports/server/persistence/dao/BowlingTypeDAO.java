package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BowlingType;

/**
 * @author Subramaniam
 * 
 */
public class BowlingTypeDAO extends DAO{
	private static Logger logger = Logger.getLogger(BowlingTypeDAO.class.getName());

	public BowlingType insert(BowlingType bowlingType, PersistenceManager pm) {
		try {
			BowlingType bowlingTypeAfterInsertion = pm.makePersistent(bowlingType);
			BowlingType detachedCopy = (BowlingType) pm.detachCopy(bowlingTypeAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingType Insertion Failed", ex);
		} 
		return null;
	}

	public BowlingType getBowlingTypeByBowlingTypeId(Long id, PersistenceManager pm) {
		try {
			BowlingType user = pm.getObjectById(BowlingType.class, id);
			BowlingType detachedCopy = (BowlingType) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingType Not Found", ex);
		} 
		return null;
	}

	public void delete(BowlingType bowlingType, PersistenceManager pm) {
		try {
			pm.deletePersistent(bowlingType);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingType Deletion Failed", ex);
		} 
	}

	public BowlingType update(BowlingType bowlingType, PersistenceManager pm) {		
		try {
			BowlingType bowlingTypeAfterUpdate = pm.makePersistent(bowlingType);
			BowlingType detachedCopy = (BowlingType) pm.detachCopy(bowlingTypeAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BowlingType Updation Failed", ex);
		} 
		return null;
	}
}
