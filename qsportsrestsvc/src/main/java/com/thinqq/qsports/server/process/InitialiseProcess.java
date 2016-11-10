package com.thinqq.qsports.server.process;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.TeamDAO;
import com.thinqq.qsports.server.persistence.dao.TeamProfileMapDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;
import com.thinqq.qsports.server.pesistence.dataobjects.TeamProfileMap;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.UserStatusEnum;

public class InitialiseProcess {
	public Boolean initialiseDatabase() {
		return initialiseUser();
	}

	private static Logger logger = Logger.getLogger(InitialiseProcess.class
			.getName());

	private boolean initialiseUser() {

		try {
			UserDAO userDAO = new UserDAO();
			PersistenceManager pm = DAO.getPersistenceManager();

			// 1. Create Super User
			User superUser = new User();
			Date userCreationTime = Calendar.getInstance().getTime();
			superUser.setCreatedTime(userCreationTime);
			superUser.setCreatedUserKey(null);
			superUser.setEmail("administrator@thinqq.com");
			superUser.setUpdatedTime(userCreationTime);
			superUser.setUpdatedUserKey(null);
			Transaction trans1 = pm.currentTransaction();
			trans1.begin();
			userDAO.insert(superUser, pm);
			trans1.commit();
			// 2. Create Super User
			User administrator = new User();
			administrator.setCreatedTime(userCreationTime);
			administrator.setCreatedUserKey(superUser.getKey());
			administrator.setEmail("administrator@thinqq.com");
			administrator.setUpdatedTime(userCreationTime);
			administrator.setUpdatedUserKey(superUser.getKey());
			Transaction trans2 = pm.currentTransaction();
			trans2.begin();
			userDAO.insert(administrator, pm);
			trans2.commit();


			
			for(int i=1;i<=100;i++){
				
			User player = new User();
			player.setCreatedTime(userCreationTime);
			player.setCreatedUserKey(superUser.getKey());
			player.setEmail("player"+i+"@mail.com");
			player.setStatus(UserStatusEnum.CONFIRMED.name());
			player.setUpdatedTime(userCreationTime);
			player.setUpdatedUserKey(superUser.getKey());
			Transaction trans3 = pm.currentTransaction();
			trans3.begin();
			userDAO.insert(player, pm);
			trans3.commit();
			}
			
			for(int i=1 ;i<4;i++){
				TeamProfileMapDAO teamPlayerDAO = new TeamProfileMapDAO();
				TeamDAO teamDAO = new TeamDAO();
					//Create Team
					pm.currentTransaction().begin();
					CricketTeam team = new CricketTeam();
					team.setName("Team"+i);
					team.setDescription("Team"+i);
					team.setDateOfEstd(Calendar.getInstance().getTime());
					team.setCity("City");
					team.setState("State");
					team.setIsoCountryCode("IN");
					teamDAO.insert(team, pm);
					pm.currentTransaction().commit();
					
					for(int j=1;j<=20;j++){
						pm.currentTransaction().begin();
						User user = userDAO.findUserByEmail("player"+j*i+"@mail.com", pm);
						TeamProfileMap map = new TeamProfileMap();
						map.setProfile(user.getKey());
						map.setTeam(team.getKey());
						Date createdTime = Calendar.getInstance().getTime();
						map.setCreatedTime(createdTime);
						map.setCreatedUserKey(user.getKey());
						map.setUpdatedTime(createdTime);
						map.setUpdatedUserKey(user.getKey());
						teamPlayerDAO.insert(map, pm);
						pm.currentTransaction().commit();
					}
					
					//Create Match Object
					
					
					
					
			}
			

			pm.close();
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}
	}
}
