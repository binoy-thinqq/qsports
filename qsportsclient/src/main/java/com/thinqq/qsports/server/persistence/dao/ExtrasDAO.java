package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.Extras;

/**
 * @author Subramaniam
 * 
 */
public class ExtrasDAO extends DAO{
	private static Logger logger = Logger.getLogger(ExtrasDAO.class.getName());

	public Extras insert(Extras extras, PersistenceManager pm) {
		try {
			Extras extrasAfterInsertion = pm.makePersistent(extras);
			Extras detachedCopy = (Extras) pm.detachCopy(extrasAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Extras Insertion Failed", ex);
		} 
		return null;
	}

	public Extras getExtrasByExtrasId(Long id, PersistenceManager pm) {
		try {
			Extras user = pm.getObjectById(Extras.class, id);
			Extras detachedCopy = (Extras) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Extras Not Found", ex);
		} 
		return null;
	}

	public void delete(Extras extras, PersistenceManager pm) {
		try {
			pm.deletePersistent(extras);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Extras Deletion Failed", ex);
		} 
	}

	public Extras update(Extras extras, PersistenceManager pm) {		
		try {
			Extras extrasAfterUpdate = pm.makePersistent(extras);
			Extras detachedCopy = (Extras) pm.detachCopy(extrasAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Extras Updation Failed", ex);
		} 
		return null;
	}
}
