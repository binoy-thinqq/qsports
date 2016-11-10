package com.thinqq.qsports.server.persistence.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;

/**
 * @author Subramaniam
 * 
 */
public class CricketTeamDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketTeamDAO.class.getName());

	public CricketTeam insert(CricketTeam cricketTeam, PersistenceManager pm) {
		try {
			CricketTeam cricketTeamAfterInsertion = pm.makePersistent(cricketTeam);
			CricketTeam detachedCopy = (CricketTeam) pm.detachCopy(cricketTeamAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeam Insertion Failed", ex);
		} 
		return null;
	}

	public CricketTeam getCricketTeamByCricketTeamId(Long id, PersistenceManager pm) {
		try {
			CricketTeam user = pm.getObjectById(CricketTeam.class, id);
			CricketTeam detachedCopy = (CricketTeam) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeam Not Found", ex);
		} 
		return null;
	}

	public void delete(CricketTeam cricketTeam, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketTeam);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeam Deletion Failed", ex);
		} 
	}

	public CricketTeam update(CricketTeam cricketTeam, PersistenceManager pm) {		
		try {
			CricketTeam cricketTeamAfterUpdate = pm.makePersistent(cricketTeam);
			CricketTeam detachedCopy = (CricketTeam) pm.detachCopy(cricketTeamAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketTeam Updation Failed", ex);
		} 
		return null;
	}
	
	public CricketTeam findTeamByKey(String teamKey, PersistenceManager pm) {
		return findTeamByKey(KeyFactory.stringToKey(teamKey), pm);
	}
	
	public CricketTeam findTeamByKey(Key teamKey, PersistenceManager pm) {
		Query q = pm.newQuery(CricketTeam.class);
		q.setFilter("key == teamKeyParam");
		q.declareParameters(Key.class.getName() + " teamKeyParam");
		try{
			@SuppressWarnings("unchecked")
			List<CricketTeam> results = (List<CricketTeam>)q.execute(teamKey);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}
	public Collection<CricketTeam> findTeamsByStartingChar(String startsWithKey, PersistenceManager pm) {
		startsWithKey = ( startsWithKey != null ? startsWithKey : "").trim();
		Query q = pm.newQuery(CricketTeam.class);
		q.setFilter("name >= :1 && name < :2");
		try{
			@SuppressWarnings("unchecked")
			List<CricketTeam> results = (List<CricketTeam>)q.execute(startsWithKey, (startsWithKey + "\ufffd"));			
			if(!results.isEmpty()){
				return pm.detachCopyAll(results);
			}
		}finally{
			q.closeAll();
		}
		return new ArrayList<CricketTeam>();
	}
}
