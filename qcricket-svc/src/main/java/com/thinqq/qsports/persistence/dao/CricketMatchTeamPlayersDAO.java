package com.thinqq.qsports.persistence.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.thinqq.qsports.persistence.dataobjects.CricketMatchTeamPlayers;

public class CricketMatchTeamPlayersDAO extends AbstractDAO<CricketMatchTeamPlayers>{

	private static CricketMatchTeamPlayersDAO instance = new CricketMatchTeamPlayersDAO(); 
	
	@Override
	public CricketMatchTeamPlayers getByPrimaryKey(Session session, Integer pk) {
		return (CricketMatchTeamPlayers)session.get(CricketMatchTeamPlayers.class, pk);
	}

	static CricketMatchTeamPlayersDAO getInstance() {
		return instance;
	}
	
	/**
	 * Get Match Team Players given MatchId and TeamId
	 * @param session
	 * @param matchId
	 * @param teamId
	 * @return
	 */
	public List<CricketMatchTeamPlayers> getByMatchIdTeamId(Session session, Integer matchId, Integer teamId) {
		Criteria  findPlayers = session.createCriteria(CricketMatchTeamPlayers.class);
		findPlayers.add(Restrictions.eq("match.matchId", matchId));
		findPlayers.add(Restrictions.eq("team.teamId", teamId));
		List players = findPlayers.list();
		List<CricketMatchTeamPlayers> playersList = new ArrayList<CricketMatchTeamPlayers>();
		Iterator it = players.iterator();
		while(it.hasNext()) {
			CricketMatchTeamPlayers team = (CricketMatchTeamPlayers)it.next();
			playersList.add(team);
		}
		return playersList;
	}
	
	/**
	 * Get Match Team Player given MatchId and ProfileId
	 * @param session
	 * @param matchId
	 * @param profileId
	 * @return
	 */
	public CricketMatchTeamPlayers getByMatchIdProfileId(Session session, Integer matchId, Integer profileId) {
		Criteria  findPlayers = session.createCriteria(CricketMatchTeamPlayers.class);
		findPlayers.add(Restrictions.eq("match.matchId", matchId));
		findPlayers.add(Restrictions.eq("profile.cricketProfileId", profileId));
		CricketMatchTeamPlayers player = (CricketMatchTeamPlayers) findPlayers.uniqueResult();
		return player;
	}
	
	/**
	 * Get Match Team Players given MatchId and TeamName
	 * @param session
	 * @param matchId
	 * @param teamName
	 * @return
	 */
	public List<CricketMatchTeamPlayers> getByMatchIdTeamName(Session session, Integer matchId, String teamName) {
		Criteria  findPlayers = session.createCriteria(CricketMatchTeamPlayers.class);
		findPlayers.add(Restrictions.eq("match.matchId", matchId));
		findPlayers.add(Restrictions.eq("teamName", teamName));
		List players = findPlayers.list();
		List<CricketMatchTeamPlayers> playersList = new ArrayList<CricketMatchTeamPlayers>();
		Iterator it = players.iterator();
		while(it.hasNext()) {
			CricketMatchTeamPlayers team = (CricketMatchTeamPlayers)it.next();
			playersList.add(team);
		}
		return playersList;
	}
	
	/**
	 * Get Match Team Player given MatchPlayerId and teamId
	 * @param session
	 * @param matchId
	 * @param profileId
	 * @return
	 */
	public CricketMatchTeamPlayers getByMatchPlayerIdAndTeamId(Session session, Integer matchPlayerId, Integer teamId) {
		Criteria  findPlayers = session.createCriteria(CricketMatchTeamPlayers.class);
		findPlayers.add(Restrictions.eq("matchTeamPlayerId", matchPlayerId));
		findPlayers.add(Restrictions.eq("team.teamId", teamId));
		
		CricketMatchTeamPlayers player = (CricketMatchTeamPlayers) findPlayers.uniqueResult();
		return player;
	}
	
	/**
	 * Get Match Team Player given MatchPlayerId and teamName
	 * @param session
	 * @param matchId
	 * @param profileId
	 * @return
	 */
	public CricketMatchTeamPlayers getByMatchPlayerIdAndTeamName(Session session, Integer matchPlayerId, String teamName) {
		Criteria  findPlayers = session.createCriteria(CricketMatchTeamPlayers.class);
		findPlayers.add(Restrictions.eq("matchTeamPlayerId", matchPlayerId));
		findPlayers.add(Restrictions.eq("teamName", teamName));
		CricketMatchTeamPlayers player = (CricketMatchTeamPlayers) findPlayers.uniqueResult();
		return player;
	}

}