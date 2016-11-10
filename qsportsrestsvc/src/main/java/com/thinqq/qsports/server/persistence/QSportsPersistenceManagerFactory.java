package com.thinqq.qsports.server.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class QSportsPersistenceManagerFactory {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private QSportsPersistenceManagerFactory() {
	}

	public static PersistenceManagerFactory getPrimaryPersistenceManagerFactory() {
		return pmfInstance;
	}
}
