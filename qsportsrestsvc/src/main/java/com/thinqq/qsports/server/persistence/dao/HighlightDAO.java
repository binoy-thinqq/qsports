package com.thinqq.qsports.server.persistence.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.pesistence.dataobjects.Highlight;

/**
 * @author Subramaniam
 * 
 */
public class HighlightDAO extends DAO{
	private static Logger logger = Logger.getLogger(HighlightDAO.class.getName());

	public Highlight insert(Highlight highlight, PersistenceManager pm) {
		try {
			Highlight highlightAfterInsertion = pm.makePersistent(highlight);
			Highlight detachedCopy = (Highlight) pm.detachCopy(highlightAfterInsertion);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Highlight Insertion Failed", ex);
		} 
		return null;
	}

	public Highlight getHighlightByHighlightId(Long id, PersistenceManager pm) {
		try {
			Highlight user = pm.getObjectById(Highlight.class, id);
			Highlight detachedCopy = (Highlight) pm.detachCopy(user);
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Highlight Not Found", ex);
		} 
		return null;
	}

	public void delete(Highlight highlight, PersistenceManager pm) {
		try {
			pm.deletePersistent(highlight);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Highlight Deletion Failed", ex);
		} 
	}

	public Highlight update(Highlight highlight, PersistenceManager pm) {		
		try {
			Highlight highlightAfterUpdate = pm.makePersistent(highlight);
			Highlight detachedCopy = (Highlight) pm.detachCopy(highlightAfterUpdate);			
			return detachedCopy;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "Highlight Updation Failed", ex);
		} 
		return null;
	}
}
