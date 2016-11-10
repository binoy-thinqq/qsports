package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.ProfileType;

/**
 * @author Subramaniam
 * 
 */
public class ProfileTypeDAO extends DAO{
	private static Logger logger = Logger.getLogger(ProfileTypeDAO.class.getName());

	public ProfileType insert(ProfileType profileType, PersistenceManager pm) {
		try {
			ProfileType profileTypeAfterInsertion = pm.makePersistent(profileType);
			ProfileType detachedCopy = (ProfileType) pm.detachCopy(profileTypeAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "ProfileType Insertion Failed", ex);
		} 
		return null;
	}

	public ProfileType getProfileTypeByProfileTypeId(Long id, PersistenceManager pm) {
		try {
			ProfileType user = pm.getObjectById(ProfileType.class, id);
			ProfileType detachedCopy = (ProfileType) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "ProfileType Not Found", ex);
		} 
		return null;
	}

	public void delete(ProfileType profileType, PersistenceManager pm) {
		try {
			pm.deletePersistent(profileType);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "ProfileType Deletion Failed", ex);
		} 
	}

	public ProfileType update(ProfileType profileType, PersistenceManager pm) {		
		try {
			ProfileType profileTypeAfterUpdate = pm.makePersistent(profileType);
			ProfileType detachedCopy = (ProfileType) pm.detachCopy(profileTypeAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "ProfileType Updation Failed", ex);
		} 
		return null;
	}
}
