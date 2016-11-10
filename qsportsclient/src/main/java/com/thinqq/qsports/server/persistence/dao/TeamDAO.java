package com.thinqq.qsports.server.persistence.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.Team;

/**
 * @author Subramaniam
 * 
 */
public class TeamDAO extends DAO{
	private static Logger logger = Logger.getLogger(TeamDAO.class.getName());

	public Team insert(Team team, PersistenceManager pm) {
		try {
			Date date = Calendar.getInstance().getTime();
			team.setCreatedTime(date);
			team.setUpdatedTime(date);
			Team teamAfterInsertion = pm.makePersistent(team);
			Team detachedCopy = (Team) pm.detachCopy(teamAfterInsertion);
			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Team Insertion Failed", ex);
		} 
		return null;
	}

	public Team getTeamByTeamId(Long id, PersistenceManager pm) {
		try {
			Team team = pm.getObjectById(Team.class, id);
			Team detachedCopy = (Team) pm.detachCopy(team);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Team Not Found", ex);
		} 
		return null;
	}
	
	public Team findTeamByKey(String teamKeyStr, PersistenceManager pm) {
		Query q = pm.newQuery(Team.class);
		q.setFilter("key == teamKeyParam");
		q.declareParameters(Key.class.getName() + " teamKeyParam");
		Key teamKey = KeyFactory.stringToKey(teamKeyStr);
		try{
			@SuppressWarnings("unchecked")
			List<Team> results = (List<Team>)q.execute(teamKey);			
			if(!results.isEmpty()){
				return pm.detachCopy(results.get(0));
			}
		}finally{
			q.closeAll();
		}
		return null;
	}

	public void delete(Team team, PersistenceManager pm) {
		try {
			pm.deletePersistent(team);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Team Deletion Failed", ex);
		} 
	}

	public Team update(Team team, PersistenceManager pm) {		
		try {
			team.setUpdatedTime(Calendar.getInstance().getTime());
			Team teamAfterUpdate = pm.makePersistent(team);
			Team detachedCopy = (Team) pm.detachCopy(teamAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Team Updation Failed", ex);
		} 
		return null;
	}
}
