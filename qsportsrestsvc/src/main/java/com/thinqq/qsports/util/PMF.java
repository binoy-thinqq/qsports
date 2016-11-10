package com.thinqq.qsports.util;

import java.io.InputStream;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * This class loads the JDO configuration
 *
 */
//TODO: Move the jdoconfig.properties to properties.
//TODO: Resolve the issue in pom, resources are not coping into war/WEB-INF/class
public final class PMF {
	private static final String JDON_CONF_FILE = "com/thinqq/qsports/util/jdoconfig.properties";
	private static PersistenceManagerFactory pmfInstance = null;

    private PMF() {
    	
    }

    private InputStream getTheConfiguration() {
    	return this.getClass().getClassLoader().getResourceAsStream(JDON_CONF_FILE);
	}

	public static PersistenceManagerFactory get() {
		if(pmfInstance==null){
			new PMF().inite();
		}
        return pmfInstance;
    }

	private void inite() {
		if(pmfInstance == null){
			pmfInstance = JDOHelper.getPersistenceManagerFactory(getTheConfiguration());
		}
		
	}
}