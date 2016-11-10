package com.thinqq.qsports.server.persistence.test;

import java.util.Calendar;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.User;

public class AllDAOTest {
public static void testAllDAO(){
	testUserDAO();
	testProfileTypeDAO();
	
}

private static void testProfileTypeDAO() {
	// TODO Auto-generated method stub
	
}

private static void testUserDAO() {
	UserDAO userDAO = new UserDAO();
	PersistenceManager pm = DAO.getPersistenceManager();

	// 1. Create Test User
	User superUser = new User();
	Date userCreationTime = Calendar.getInstance().getTime();
	superUser.setCreatedTime(userCreationTime);
	superUser.setCreatedUserKey(null);
	superUser.setEmail("testuser@thinqq.com");
	superUser.setUpdatedTime(userCreationTime);
	superUser.setUpdatedUserKey(null);
	//2. Insert User
	Transaction trans1 = pm.currentTransaction();
	trans1.begin();
	userDAO.insert(superUser, pm);
	trans1.commit();
	Key testUserKey = superUser.getKey();
	//3. Get User
	User testUser = userDAO.getUserByUserId(testUserKey.getId(), pm);
	//4. Update User
	testUser.setEmail("updatedTestUser@mail.com");
	Transaction trans2 = pm.currentTransaction();
	trans2.begin();
	userDAO.update(testUser, pm);
	trans2.commit();
	//5. Delete User
	Transaction trans3 = pm.currentTransaction();
	trans3.begin();
	userDAO.delete(testUser, pm);
	trans3.commit();
	
	pm.close();
}
}
