package com.thinqq.qsports.server.match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.core.statemachine.IState;
import com.thinqq.qsports.server.core.statemachine.StateMachine;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.CricketInningsDAO;
import com.thinqq.qsports.server.persistence.dao.CricketMatchDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketInnings;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketMatch;
import com.thinqq.qsports.shared.MatchStatus;
import com.thinqq.qsports.shared.validation.ErrorRepository;
import com.thinqq.qsports.shared.validation.ErrorVo;

/*
 * 
 Planned -> Started
 1. All mandatory fields filled
 2. Eleven players in the match
 Planned -> Abandoned
 No Criteria
 Started -> Completed
 1. Scorecard validation completed.
 2. Match result filled.
 Started -> Planned
 No Criteria
 Started -> Abandoned
 No Criteria
 Completed -> Approved
 1. At least one owner in each team approved
 Completed -> Rejected
 1. Reason for rejection given
 Rejected -> Completed
 1. Correction comment given
 */

public class MatchStateMachine extends StateMachine<CricketMatchWrapper>{

	private MatchStateMachine() {
	}
	private static final Logger logger = Logger.getLogger(MatchStateMachine.class.getName());
	private static MatchStateMachine s_instance = new MatchStateMachine();
	private static StartedState STARTED_STATE = new StartedState();

	public static MatchStateMachine getInstance() {
		return s_instance;
	}

	public static class StartedState implements IState<CricketMatchWrapper> {

		public List<ErrorVo> isEligible(CricketMatchWrapper matchWrapper) {
			CricketMatch match = matchWrapper.getMatch();
			List<ErrorVo> errors = new ArrayList<ErrorVo>();

				// 1. Match has to be in either Planned state to come here
				boolean isTransitionPossible = MatchStatus.PLANNED.getId() == match
						.getStatus().intValue();
				if(!isTransitionPossible){
					errors.add(ErrorRepository.matchAlreadyStarted);
					return errors;
				}
				// 2. All mandatory fields have to be filled
				boolean allMandatoryFieldsFilled = match.getDate() != null
						&& (match.getVenue() != null && !match.getVenue()
						.isEmpty())
						&& (match.getCountry() != null && !match.getCountry()
						.isEmpty())
						&& (match.getTeam1() != null && !match.getTeam1()
						.isEmpty())
						&& (match.getTeam2() != null && !match.getTeam2()
						.isEmpty()) && match.getFormat() != null
						&& match.getMatchOwner() != null;

				if(!allMandatoryFieldsFilled){
					errors.add(ErrorRepository.oppsSomethingBroke);
					return errors;
				}
				// 3. Minimum eleven players in each team
				boolean teamPlayersFilled = match.getTeam1Players() != null
						&& match.getTeam1Players().size() >= 11
						&& match.getTeam2Players() != null
						&& match.getTeam2Players().size() >= 11;
						if(!teamPlayersFilled){
							errors.add(ErrorRepository.min11Players);
						}
			
			return errors;
		}

		@Override
		public int getStateId() {
			return MatchStatus.STARTED.getId();
		}

		@Override
		public boolean onEntry(CricketMatchWrapper object) {
			// Create new Innings object
			CricketMatch match = object.getMatch();
			CricketMatchDAO matchDAO = new CricketMatchDAO();
			CricketInningsDAO inningsDAO = new CricketInningsDAO();
			PersistenceManager pm = DAO.getPersistenceManager();
			Date time = Calendar.getInstance().getTime();
			try{
			CricketInnings innings1 = new CricketInnings();
			innings1.setCreatedTime(time);
			innings1.setUpdatedTime(time);
			innings1.setUpdatedUserKey(match.getMatchOwner());
			innings1.setCreatedUserKey(match.getMatchOwner());
			innings1.setMatch(match.getKey());
			pm.currentTransaction().begin();
			inningsDAO.insert(innings1, pm);
			pm.currentTransaction().commit();
			match.setInnings1Key(innings1.getKey());
			
			CricketInnings innings2 = new CricketInnings();
			innings2.setCreatedTime(time);
			innings2.setUpdatedTime(time);
			innings2.setUpdatedUserKey(match.getMatchOwner());
			innings2.setCreatedUserKey(match.getMatchOwner());
			innings2.setMatch(match.getKey());
			pm.currentTransaction().begin();
			inningsDAO.insert(innings2, pm);
			pm.currentTransaction().commit();
			match.setInnings2Key(innings2.getKey());
			
/*	
 * Create new state for test since we need to determine the follow on criteria		
 * if(MatchFormatEnum.TEST.getId() == match.getFormat()){
				CricketInnings innings3 = new CricketInnings();
				innings3.setCreatedTime(time);
				innings3.setUpdatedTime(time);
				innings3.setUpdatedUserKey(match.getMatchOwner());
				innings3.setCreatedUserKey(match.getMatchOwner());
				innings3.setMatch(match.getKey());
				pm.currentTransaction().begin();
				inningsDAO.insert(innings3, pm);
				pm.currentTransaction().commit();
				match.setInnings3Key(innings3.getKey());
				
				
				CricketInnings innings4 = new CricketInnings();
				innings4.setCreatedTime(time);
				innings4.setUpdatedTime(time);
				innings4.setUpdatedUserKey(match.getMatchOwner());
				innings4.setCreatedUserKey(match.getMatchOwner());
				innings4.setMatch(match.getKey());
				pm.currentTransaction().begin();
				inningsDAO.insert(innings4, pm);
				pm.currentTransaction().commit();
				match.setInnings2Key(innings4.getKey());
			}*/
			pm.currentTransaction().begin();
			matchDAO.update(match, pm);
			pm.currentTransaction().commit();
			} catch(Exception e){
				logger.log(Level.SEVERE, "Couldn't create innings for match " 
						+ KeyFactory.keyToString(match.getKey()), e);
				return false;
			} 
			return true;
		}
	}

	@Override
	public IState<CricketMatchWrapper> getState(int stateId) {
		if(MatchStatus.STARTED.getId() == stateId){
			return STARTED_STATE;
		}
		return null;
	}

}
