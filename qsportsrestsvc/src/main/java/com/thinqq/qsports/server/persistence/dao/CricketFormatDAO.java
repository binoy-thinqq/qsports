package com.thinqq.qsports.server.persistence.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketFormat;

/**
 * @author Subramaniam
 * 
 */
public class CricketFormatDAO extends DAO{
	private static Logger logger = Logger.getLogger(CricketFormatDAO.class.getName());

	public CricketFormat insert(CricketFormat cricketFormat, PersistenceManager pm) {
		try {
			CricketFormat cricketFormatAfterInsertion = pm.makePersistent(cricketFormat);
			CricketFormat detachedCopy = (CricketFormat) pm.detachCopy(cricketFormatAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketFormat Insertion Failed", ex);
		} finally {
			pm.close();
		}
		return null;
	}

	public CricketFormat getCricketFormatByCricketFormatId(Long id, PersistenceManager pm) {
		try {
			CricketFormat user = pm.getObjectById(CricketFormat.class, id);
			CricketFormat detachedCopy = (CricketFormat) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketFormat Not Found", ex);
		} 
		return null;
	}

	
	
	public CricketFormat getCricketFormatByName(String name, PersistenceManager pm) {
		Query q = pm.newQuery(CricketFormat.class);
		try {
			q.setFilter("formatName == '" + name + "'");
			List<CricketFormat> test = (List<CricketFormat>)q.execute();
			if (test != null) {
				CricketFormat format = (CricketFormat) test.get(0);
				CricketFormat detachedCopy = (CricketFormat) pm
						.detachCopy(format);
				return detachedCopy;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketFormat Not Found", ex);
		} 
		finally {
			q.closeAll();
			pm.close();
		}
		return null;
	}
	
	
	public void delete(CricketFormat cricketFormat, PersistenceManager pm) {
		try {
			pm.deletePersistent(cricketFormat);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketFormat Deletion Failed", ex);
		} 
	}

	public CricketFormat update(CricketFormat cricketFormat, PersistenceManager pm) {		
		try {
			CricketFormat cricketFormatAfterUpdate = pm.makePersistent(cricketFormat);
			CricketFormat detachedCopy = (CricketFormat) pm.detachCopy(cricketFormatAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "CricketFormat Updation Failed", ex);
		} 
		return null;
	}
}
