package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.Achievement;

/**
 * @author Subramaniam
 * 
 */
public class AchievementDAO extends DAO{
	private static Logger logger = Logger.getLogger(AchievementDAO.class.getName());

	public Achievement insert(Achievement achievement, PersistenceManager pm) {
		try {
			Achievement achievementAfterInsertion = pm.makePersistent(achievement);
			Achievement detachedCopy = (Achievement) pm.detachCopy(achievementAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Achievement Insertion Failed", ex);
		} 
		return null;
	}

	public Achievement getAchievementByAchievementId(Long id, PersistenceManager pm) {
		try {
			Achievement user = pm.getObjectById(Achievement.class, id);
			Achievement detachedCopy = (Achievement) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Achievement Not Found", ex);
		} 
		return null;
	}

	public void delete(Achievement achievement, PersistenceManager pm) {
		try {
			pm.deletePersistent(achievement);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Achievement Deletion Failed", ex);
		} 
	}

	public Achievement update(Achievement achievement, PersistenceManager pm) {		
		try {
			Achievement achievementAfterUpdate = pm.makePersistent(achievement);
			Achievement detachedCopy = (Achievement) pm.detachCopy(achievementAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Achievement Updation Failed", ex);
		} 
		return null;
	}
}
