package com.thinqq.qsports.persistence.conf;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.thinqq.qsports.persistence.dataobjects.AuthKey;
import com.thinqq.qsports.persistence.dataobjects.BattingStatistics;
import com.thinqq.qsports.persistence.dataobjects.BowlingStatistics;
import com.thinqq.qsports.persistence.dataobjects.CricketInningsDetails;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.CricketTeam;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamPlayers;
import com.thinqq.qsports.persistence.dataobjects.CricketTeamStats;
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
		      } catch (Throwable ex) { 
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
		configuration.addAnnotatedClass(AuthKey.class);
		configuration.addAnnotatedClass(CricketInningsDetails.class);
		configuration.addAnnotatedClass(CricketProfile.class);
		configuration.addAnnotatedClass(BattingStatistics.class);
		configuration.addAnnotatedClass(BowlingStatistics.class);
		configuration.addAnnotatedClass(CricketTeam.class);
		configuration.addAnnotatedClass(CricketTeamStats.class);
		configuration.addAnnotatedClass(CricketTeamPlayers.class);
	}

}
