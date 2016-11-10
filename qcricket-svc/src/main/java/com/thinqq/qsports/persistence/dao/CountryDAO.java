package com.thinqq.qsports.persistence.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.thinqq.qsports.persistence.dataobjects.Country;
import com.thinqq.qsports.persistence.dataobjects.CricketTeam;

public class CountryDAO extends AbstractDAO<Country> {
	
	private static CountryDAO instance = new CountryDAO();
	
	static CountryDAO getInstance() {
		return instance;
	}

	@Override
	public Country getByPrimaryKey(Session session, Integer i) {
		return (Country)session.get(Country.class, i);
	}
	
	public List<Country> getAllCountries(Session session) {
		Criteria  allCountries = session.createCriteria(Country.class);
		List countryList = allCountries.list();
		
		List<Country> cricketTeams = new ArrayList<Country>();
		 Iterator it = countryList.iterator();
		while(it.hasNext()) {
			Country team = (Country)it.next();
			cricketTeams.add(team);
		}
		return cricketTeams;
	}

}
