package com.thinqq.qsports.persistence.conf;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.thinqq.qsports.persistence.dataobjects.User;

public class SessionManager {
	public static SessionFactory sessionFactory;
	 private static ServiceRegistry serviceRegistry;
	
	 /**
	  * Get Session Factory
	  * @return
	  */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try{
				Configuration configuration = new Configuration();
		        configuration.configure("qcricket.cfg.xml");
		        addDataObjects(configuration);
		        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        
		        sessionFactory = configuration.buildSessionFactory(serviceRegistry);     
		      }catch (Throwable ex) { 
		         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		      }
		}
		return sessionFactory;
	}

	/**
	 * Add annotated Classes
	 * @param configuration
	 */
	private static void addDataObjects(Configuration configuration) {
		configuration.addAnnotatedClass(User.class);
	}

}
