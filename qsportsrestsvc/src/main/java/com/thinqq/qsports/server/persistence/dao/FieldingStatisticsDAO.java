package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.FieldingStatistics;

/**
 * @author Subramaniam
 * 
 */
public class FieldingStatisticsDAO extends DAO{
	private static Logger logger = Logger.getLogger(FieldingStatisticsDAO.class.getName());

	public FieldingStatistics insert(FieldingStatistics fieldingStatistics, PersistenceManager pm) {
		try {
			FieldingStatistics fieldingStatisticsAfterInsertion = pm.makePersistent(fieldingStatistics);
			FieldingStatistics detachedCopy = (FieldingStatistics) pm.detachCopy(fieldingStatisticsAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "FieldingStatistics Insertion Failed", ex);
		} 
		return null;
	}

	public FieldingStatistics getFieldingStatisticsByFieldingStatisticsId(Long id, PersistenceManager pm) {
		try {
			FieldingStatistics user = pm.getObjectById(FieldingStatistics.class, id);
			FieldingStatistics detachedCopy = (FieldingStatistics) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "FieldingStatistics Not Found", ex);
		} 
		return null;
	}

	public void delete(FieldingStatistics fieldingStatistics, PersistenceManager pm) {
		try {
			pm.deletePersistent(fieldingStatistics);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "FieldingStatistics Deletion Failed", ex);
		} 
	}

	public FieldingStatistics update(FieldingStatistics fieldingStatistics, PersistenceManager pm) {		
		try {
			FieldingStatistics fieldingStatisticsAfterUpdate = pm.makePersistent(fieldingStatistics);
			FieldingStatistics detachedCopy = (FieldingStatistics) pm.detachCopy(fieldingStatisticsAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "FieldingStatistics Updation Failed", ex);
		} 
		return null;
	}
}
