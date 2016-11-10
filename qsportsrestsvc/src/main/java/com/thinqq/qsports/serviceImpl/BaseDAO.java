package com.thinqq.qsports.serviceImpl;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.DO.BasePersistentObject;
import com.thinqq.qsports.util.PMF;

public abstract class BaseDAO {
	
	  private static PersistenceManager persistManager;
	  public PersistenceManager getPersistManager() {
		  if(persistManager == null) {
			  persistManager = PMF.get().getPersistenceManager();
		  }
		return persistManager;
	}

	  public String persistObject(BasePersistentObject persistObject){
		  PersistenceManager pm = getPersistManager();
			 try{
		        	pm.makePersistent(persistObject);
		        }finally{
		        	pm.close();
		        }
		        return "success";
		  
	  }
}
