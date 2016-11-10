package com.thinqq.qsports.DO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class User implements BasePersistentObject{
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	   
	@Persistent
    private String username;

    @Persistent
    private String password;

	@Persistent
    private String email;
    
//    @Persistent
//    private List<UserProfile> profiles;

    public User(){
    	
    }
    
    public User(String username, String password, String email){
    	this.username = username;
    	this.password = password;
    	this.email = email;
//    	this.profiles = new ArrayList<UserProfile>(){
//    		{
//    		add(new UserProfile(ProfileType.DEFAULT, " ", " "));
//    		}
//    	};
    }

	public Key getKey() {
		return key;
	}
	
    public void setKey(Key key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
