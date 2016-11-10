package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.BattingType;

/**
 * @author Subramaniam
 * 
 */
public class BattingTypeDAO extends DAO{
	private static Logger logger = Logger.getLogger(BattingTypeDAO.class.getName());

	public BattingType insert(BattingType battingType, PersistenceManager pm) {
		try {
			BattingType battingTypeAfterInsertion = pm.makePersistent(battingType);
			BattingType detachedCopy = (BattingType) pm.detachCopy(battingTypeAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingType Insertion Failed", ex);
		} 
		return null;
	}

	public BattingType getBattingTypeByBattingTypeId(Long id, PersistenceManager pm) {
		try {
			BattingType user = pm.getObjectById(BattingType.class, id);
			BattingType detachedCopy = (BattingType) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingType Not Found", ex);
		} 
		return null;
	}

	public void delete(BattingType battingType, PersistenceManager pm) {
		try {
			pm.deletePersistent(battingType);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingType Deletion Failed", ex);
		} 
	}

	public BattingType update(BattingType battingType, PersistenceManager pm) {		
		try {
			BattingType battingTypeAfterUpdate = pm.makePersistent(battingType);
			BattingType detachedCopy = (BattingType) pm.detachCopy(battingTypeAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "BattingType Updation Failed", ex);
		} 
		return null;
	}
}
