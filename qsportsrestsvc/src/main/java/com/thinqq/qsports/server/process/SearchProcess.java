package com.thinqq.qsports.server.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.thinqq.qsports.server.persistence.DAO;
import com.thinqq.qsports.server.persistence.dao.CricketTeamDAO;
import com.thinqq.qsports.server.persistence.dao.UserDAO;
import com.thinqq.qsports.server.pesistence.dataobjects.CricketTeam;
import com.thinqq.qsports.server.pesistence.dataobjects.User;
import com.thinqq.qsports.shared.CountryList;
import com.thinqq.qsports.shared.NameAndKey;
import com.thinqq.qsports.shared.vo.SearchResultsRequestVo;
import com.thinqq.qsports.shared.vo.SearchResultsResponseVo;
import com.thinqq.qsports.shared.vo.SearchResultsResponseVo.SearchResult;

public class SearchProcess {
	private static SearchProcess s_instance = new SearchProcess();
	public static SearchProcess getInstance(){
		return s_instance;
	}
	
	public void searchTeam(SearchResultsRequestVo request, SearchResultsResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		CricketTeamDAO teamDAO = new CricketTeamDAO();
		if(request.getSearchKey()==null){
			response.setInvlaidSearchKey(true);
		}
		Collection<CricketTeam> teamList = teamDAO.findTeamsByStartingChar(request.getSearchKey(), pm);
		List<SearchResult> resultList = new ArrayList<SearchResultsResponseVo.SearchResult>();
		for(CricketTeam team : teamList){
			resultList.add(new SearchResult(team.getName(), 
					new NameAndKey(team.getName(), KeyFactory.keyToString(team.getKey())), 
					team.getCity()+", "+team.getState()+", "+CountryList.getListCountry().get(team.getIsoCountryCode())));
		}
		response.setResults(resultList);
	}
	
	public void searchPlayer(SearchResultsRequestVo request, SearchResultsResponseVo response){
		PersistenceManager pm = DAO.getPersistenceManager();
		UserDAO userDAO = new UserDAO();
		if(request.getSearchKey()==null){
			response.setInvlaidSearchKey(true);
		}
		Collection<User> userList = userDAO.findUsersByStartingChar(request.getSearchKey(), pm);
		List<SearchResult> resultList = new ArrayList<SearchResultsResponseVo.SearchResult>();

		for(User user : userList){
			String city = "";
			String state = "";
			String country = "";
			if(user.getCity()!=null){
				city = user.getCity();
			}
			if(user.getState()!=null){
				state = user.getState();
			}
			if(user.getIsoCountryCode()!=null){
				country = CountryList.getListCountry().get(user.getIsoCountryCode());
			}
			resultList.add(new SearchResult(user.getGivenName()+" ("+user.getEmail()+")", 
					new NameAndKey(user.getGivenName()+" ("+user.getEmail()+")", KeyFactory.keyToString(user.getKey())), 
					city+", "+state+", "+country));
		}
		response.setResults(resultList);
	}
}
