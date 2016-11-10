package com.thinqq.qsports.server.persistence.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketProfile;
import com.thinqq.qsports.server.pesistence.dataobjects.User;

/**
 * @author Subramaniam
 * 
 */
public class CricketProfileDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketProfileDAO.class.getName());

	public CricketProfile insert(CricketProfile cricketProfile, PersistenceManager pm) {
		try {
			CricketProfile cricketProfileAfterInsertion = pm.makePersistent(cricketProfile);
			CricketProfile detachedCopy = (CricketProfile) pm.detachCopy(cricketProfileAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketProfile Insertion Failed", ex);
		} 
		return null;
	}

	public CricketProfile getCricketProfileByCricketProfileId(Long id, PersistenceManager pm) {
		try {
			CricketProfile user = pm.getObjectById(CricketProfile.class, id);
			CricketProfile detachedCopy = (CricketProfile) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketProfile Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketProfile cricketProfile, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketProfile);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketProfile Deletion Failed", ex);
		} 
	}

	public CricketProfile update(CricketProfile cricketProfile, PersistenceManager pm) {		
		try {
			CricketProfile cricketProfileAfterUpdate = pm.makePersistent(cricketProfile);
			CricketProfile detachedCopy = (CricketProfile) pm.detachCopy(cricketProfileAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketProfile Updation Failed", ex);
		} 
		return null;
	}
	public CricketProfile getCricketProfileByKey(String profile, PersistenceManager pm){
		return getCricketProfileByKey(KeyFactory.stringToKey(profile), pm);
	}
	public CricketProfile getCricketProfileByKey(Key profile, PersistenceManager pm){
		Query q = pm.newQuery(CricketProfile.class);
		q.setFilter("key == profileParam");
		q.declareParameters(Key.class.getName() + " profileParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketProfile> results = (List<CricketProfile>)q.execute(profile);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	public CricketProfile getCricketProfileByUser(User user, PersistenceManager pm){
		Query q = pm.newQuery(CricketProfile.class);
		q.setFilter("user == userParam");
		q.declareParameters(Key.class.getName() + " userParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketProfile> results = (List<CricketProfile>)q.execute(user.getKey());			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	
	public List<CricketProfile> getProfileUsingName(String user, PersistenceManager pm){
		Query q = pm.newQuery(User.class);
		q.setFilter("firstName == firstNameParam");
		q.declareParameters("String firstNameParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketProfile> results = (List<CricketProfile>)q.execute(user + "%");			
			if(!results.isEmpty()){
				System.out.println(results);
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	
	public CricketProfile findCricketProfileByUserKey(Key userKey, PersistenceManager pm){
		Query q = pm.newQuery(CricketProfile.class);
		q.setFilter("user == userKeyParam");
		q.declareParameters(Key.class.getName() + " userKeyParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketProfile> results = (List<CricketProfile>)q.execute(userKey);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	
	public CricketProfile findCricketProfileByUserKey(String userKey, PersistenceManager pm){
		return findCricketProfileByUserKey(KeyFactory.stringToKey(userKey), pm);
	}
}
