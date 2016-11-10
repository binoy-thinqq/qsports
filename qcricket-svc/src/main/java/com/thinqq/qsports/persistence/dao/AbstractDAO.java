package com.thinqq.qsports.persistence.dao;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

public abstract class AbstractDAO<T extends Serializable> {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Integer insert(Session session, T o){
		int saved =  (Integer)session.save(o);
		return saved;
	}
	public boolean update(Session session, T o){
		try {
			session.update(o);
		} catch (Exception e){
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}
		return true;
	}
	public boolean delete(Session session, T o){
		try {
			session.delete(o);
		} catch (Exception e){
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}
		return true;
	}
	public abstract T getByPrimaryKey(Session session, Integer i);
}
