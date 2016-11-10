package com.thinqq.qsports.server.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;



public abstract class DAO {
	private static PersistenceManagerFactory pmf = QSportsPersistenceManagerFactory
			.getPrimaryPersistenceManagerFactory();

	public static PersistenceManager getPersistenceManager() {
		return pmf.getPersistenceManager();
	}

}
