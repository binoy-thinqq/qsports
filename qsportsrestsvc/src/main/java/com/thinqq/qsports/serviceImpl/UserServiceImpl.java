package com.thinqq.qsports.serviceImpl;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.DO.User;
import com.thinqq.qsports.DO.dto.UserDto;
import com.thinqq.qsports.service.UserService;
import com.thinqq.qsports.util.PMF;

//Implementation of use API
public class UserServiceImpl extends BaseDAO implements UserService{

	@Override
	public String registerUser(String username, String password, String name, String email) {
		
        PersistenceManager pm = getPersistManager();
        User user = new User(username, password, email);
        user.setKey(getKey(username));
        return persistObject(user);
   	}

	private Key getKey(String username) {
		return KeyFactory.createKey(User.class.getSimpleName(), username);
	}

	@Override
	public UserDto getUserProfile(String userId) {
        User user = new User();
        user.setEmail("binoybt@gmail.com");
        //user.setKey(new Key);
        user.setPassword("password");
        user.setUsername("binoybt");
        	//PMF.get().getPersistenceManager().getObjectById(User.class, getKey(userId));
        
        return new UserDto(user);
	}

	@Override
	public String authUser(final String username,final String password) {
		 //TODO: Need to add some more logic in auth token
		 //TODO: Add other error tokens also
		 String message = "ERROR-CODE"+"NO_USER";
	 	 PersistenceManager pm = getPersistManager();
	 	 try{
	 		 User user = pm.getObjectById(User.class,getKey(username));
	 		 if(user != null && user.getPassword().equals(password)){
	 			 message = "QSPORTS-AUTHKEY="+user.getKey().getId();
	 		 }
	 	 }catch(JDOObjectNotFoundException e){
	 		message = "ERROR-CODE"+"NO_USER";
	 	 }
		return message;
	}

}

