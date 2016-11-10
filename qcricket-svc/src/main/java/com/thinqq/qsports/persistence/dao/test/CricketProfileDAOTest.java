package com.thinqq.qsports.persistence.dao.test;

import java.util.Calendar;

import org.hibernate.Session;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.DAOFactory;
import com.thinqq.qsports.persistence.dataobjects.CricketProfile;
import com.thinqq.qsports.persistence.dataobjects.User;

public class CricketProfileDAOTest {

	
	private Integer insetTest(Session session){
		
		
		User user = DAOFactory.getUserDAO().getByEmail(session, "binoybt@gmail.com");
		
		CricketProfile cp = new CricketProfile();
	
		cp.setCreatedId(user.getUserId());
		cp.setCreatedTime(Calendar.getInstance().getTime());
		cp.setUpdatedId(user.getUserId());
		cp.setUpdatedTime(Calendar.getInstance().getTime());
		cp.setUser(user);
		cp.setWicketKeeper(false);
		
		return DAOFactory.getCricketProfileDAO().insert(session, cp);
	}
	
	private CricketProfile getByPK(Session session, Integer pk){
		return DAOFactory.getCricketProfileDAO().getByPrimaryKey(session, pk);
	}
	
	
	private CricketProfile getByUserId(Session session, Integer userId){
		return DAOFactory.getCricketProfileDAO().getByUserId(session, userId);
	}
	
	private boolean updateTest(Session session, CricketProfile profile){
		return DAOFactory.getCricketProfileDAO().update(session, profile);
	}
	public static void main (String args[]){
		CricketProfileDAOTest test = new CricketProfileDAOTest();
		
		Session session = SessionManager.getSessionFactory().openSession();
		/*session.getTransaction().begin();
		System.out.print(test.insetTest(session));
		session.getTransaction().commit();*/
		int pk = 1;
		CricketProfile profile = test.getByPK(session, pk);
		profile = test.getByUserId(session, profile.getUser().getUserId());
		session.getTransaction().begin();
		System.out.print(test.updateTest(session, profile));
		session.getTransaction().commit();
		
	}
}
