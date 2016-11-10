package com.thinqq.qsports.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.AuthKey;

public class AuthKeyDAO extends AbstractDAO<AuthKey>{

	private static AuthKeyDAO instance = new AuthKeyDAO(); 
	
	@Override
	public AuthKey getByPrimaryKey(Session session, Integer pk) {
		return (AuthKey)session.get(AuthKey.class, pk);
	}
	

	public AuthKey getByAuthKey(Session session, String authKey) {
		Criteria byAuthKey = session.createCriteria(AuthKey.class);
		byAuthKey.add(Restrictions.eq("key", authKey));
		List<AuthKey> authKeyList = byAuthKey.list();
		if (authKeyList != null && !authKeyList.isEmpty()) {
			return (AuthKey)authKeyList.get(0);
		} else {
			return null;
		}
	}
	
	public static AuthKeyDAO getInstance() {
		return instance;
	}

}
