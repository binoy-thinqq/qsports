package com.thinqq.qsports.server.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.TeamProfileMap;

/**
 * @author Subramaniam
 * 
 */
public class TeamProfileMapDAO extends DAO{
	private static Logger logger = Logger.getLogger(TeamProfileMapDAO.class.getName());

	public TeamProfileMap insert(TeamProfileMap teamProfileMap, PersistenceManager pm) {
		try {
			TeamProfileMap teamProfileMapAfterInsertion = pm.makePersistent(teamProfileMap);
			TeamProfileMap detachedCopy = (TeamProfileMap) pm.detachCopy(teamProfileMapAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Insertion Failed", ex);
		} 
		return null;
	}

	public TeamProfileMap getTeamProfileMapByTeamProfileMapId(Long id, PersistenceManager pm) {
		try {
			TeamProfileMap user = pm.getObjectById(TeamProfileMap.class, id);
			TeamProfileMap detachedCopy = (TeamProfileMap) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Not Found", ex);
		} 
		return null;
	}
	public TeamProfileMap getTeamProfileMapByTeamAndProfile(Key profile, Key team, PersistenceManager pm) {
		try {
			Query q = pm.newQuery(TeamProfileMap.class);
			q.setFilter("profile == profileParam && team == teamParam");
			q.declareParameters(Key.class.getName() + " profileParam, "+Key.class.getName() + " teamParam");
			@SuppressWarnings("unchecked")
			List<TeamProfileMap> results = (List<TeamProfileMap>)q.execute(profile,team);
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Not Found", ex);
		} 
		return null;
	}
	
	public List<TeamProfileMap> getTeamProfilesMapByTeam(String teamKey, PersistenceManager pm) {
		return getTeamProfilesMapByTeam(KeyFactory.stringToKey(teamKey), pm);
	}
	
	public List<TeamProfileMap> getTeamProfilesMapByTeam(Key teamKey, PersistenceManager pm) {
		try {
			Query q = pm.newQuery(TeamProfileMap.class);
			q.setFilter("team == teamParam");
			q.declareParameters(Key.class.getName() + " teamParam");
			@SuppressWarnings("unchecked")
			List<TeamProfileMap> results = (List<TeamProfileMap>)q.execute(teamKey);
			if(!results.isEmpty()){
				return results;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Not Found", ex);
		} 
		return new ArrayList<TeamProfileMap>();
	}

	public List<TeamProfileMap> getTeamProfileMapByProfile(String profileKey, PersistenceManager pm){
		return getTeamProfileMapByProfile(KeyFactory.stringToKey(profileKey), pm);
	}
	
	public List<TeamProfileMap> getTeamProfileMapByProfile(Key profileKey, PersistenceManager pm){
		try {
			Query q = pm.newQuery(TeamProfileMap.class);
			q.setFilter("profile == profileParam");
			q.declareParameters(Key.class.getName() + " profileParam");
			@SuppressWarnings("unchecked")
			List<TeamProfileMap> results = (List<TeamProfileMap>)q.execute(profileKey);
			if(!results.isEmpty()){
				return results;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Not Found", ex);
		} 
		return new ArrayList<TeamProfileMap>();
	}
	
	
	public List<TeamProfileMap> findTeamProfileMapofOwners(Key teamKey, PersistenceManager pm) {
		try {
			Query q = pm.newQuery(TeamProfileMap.class);
			Boolean owner= true;
			//q.declareParameters();
			q.declareParameters(Key.class.getName() + " teamKeyParam,"+Boolean.class.getName() + " ownerParam");
			q.setFilter("team == teamKeyParam && owner == ownerParam ");
			@SuppressWarnings("unchecked")
			List<TeamProfileMap> results = (List<TeamProfileMap>)q.execute(teamKey,owner);
			return results;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Not Found", ex);
		} 
		return new ArrayList<TeamProfileMap>();
	}

	public void delete(TeamProfileMap teamProfileMap, PersistenceManager pm) {
		try {
			pm.deletePersistent(teamProfileMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Deletion Failed", ex);
		} 
	}

	public TeamProfileMap update(TeamProfileMap teamProfileMap, PersistenceManager pm) {		
		try {
			TeamProfileMap teamProfileMapAfterUpdate = pm.makePersistent(teamProfileMap);
			TeamProfileMap detachedCopy = (TeamProfileMap) pm.detachCopy(teamProfileMapAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "TeamProfileMap Updation Failed", ex);
		} 
		return null;
	}
}
