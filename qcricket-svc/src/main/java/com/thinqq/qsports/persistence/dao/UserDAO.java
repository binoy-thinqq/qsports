package com.thinqq.qsports.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.User;

public class UserDAO extends AbstractDAO<User>{

	private static UserDAO instance = new UserDAO(); 
	
	@Override
	public User getByPrimaryKey(Session session, Integer pk) {
		return (User)session.get(User.class, pk);
	}
	
	public User getByEmail(Session session, String email) {
		Criteria  findByPk = session.createCriteria(User.class);
		findByPk.add(Restrictions.eq("email", email));
		User user = (User)findByPk.uniqueResult();
		return user;
	}

	static UserDAO getInstance() {
		return instance;
	}

}
