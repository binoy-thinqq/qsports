package com.thinqq.qsports.DO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserProfile {
	
    @Persistent
    private ProfileType type;

    @Persistent
    private String aboutme;

    @Persistent
    private String myinterest;

    public UserProfile(ProfileType type, String aboutme, String myinterest){
    	this.type = type;
    	this.aboutme = aboutme;
    	this.myinterest = myinterest;
    }
}
