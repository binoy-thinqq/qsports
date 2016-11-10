package com.thinqq.qsports.server.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.User;

/**
 * @author Subramaniam
 * 
 */
public class UserDAO extends DAO{
	private static Logger logger = Logger.getLogger(UserDAO.class.getName());

	public User insert(User user, PersistenceManager pm) {
		try {
			Date date = Calendar.getInstance().getTime();
			user.setCreatedTime(date);
			user.setUpdatedTime(date);
			User userAfterInsertion = pm.makePersistent(user);
			User detachedCopy = (User) pm.detachCopy(userAfterInsertion);
			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "User Insertion Failed", ex);
		} 
		return null;
	}

	public User getUserByUserId(Long id, PersistenceManager pm) {
		try {
			User user = pm.getObjectById(User.class, id);
			User detachedCopy = (User) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "User Not Found", ex);
		} 
		return null;
	}

	public void delete(User user, PersistenceManager pm) {
		try {
			pm.deletePersistent(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "User Deletion Failed", ex);
		} 
	}

	public User update(User user, PersistenceManager pm) {		
		try {
			user.setUpdatedTime(Calendar.getInstance().getTime());
			User userAfterUpdate = pm.makePersistent(user);
			User detachedCopy = (User) pm.detachCopy(userAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "User Updation Failed", ex);
		} 
		return null;
	}
	
	public User findUserByEmail(String email, PersistenceManager pm){
		Query q = pm.newQuery(User.class);
		q.setFilter("email == emailParam");
		q.declareParameters("String emailParam");
		try{
			@SuppressWarnings("unchecked")
			List<User> results = (List<User>)q.execute(email);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	
	public User findUserByKey(String userKey, PersistenceManager pm){
		return findUserByKey(KeyFactory.stringToKey(userKey), pm);
	}
	
	public User findUserByKey(Key userKey, PersistenceManager pm){
		Query q = pm.newQuery(User.class);
		q.setFilter("key == userKeyParam");
		q.declareParameters(Key.class.getName() + " userKeyParam");
		try{
			@SuppressWarnings("unchecked")
			List<User> results = (List<User>)q.execute(userKey);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	
	public Collection<User> findUsersByStartingChar(String startsWithKey, PersistenceManager pm) {
		startsWithKey = ( startsWithKey != null ? startsWithKey : "").trim();
		Query q = pm.newQuery(User.class);
		q.setFilter("displayName >= :1 && displayName < :2");
		try{
			@SuppressWarnings("unchecked")
			List<User> results = (List<User>)q.execute(startsWithKey, (startsWithKey + "\ufffd"));			
			if(!results.isEmpty()){
				return pm.detachCopyAll(results);
			}
		}finally{
			q.closeAll();
		}
		return new ArrayList<User>();
	}

}
