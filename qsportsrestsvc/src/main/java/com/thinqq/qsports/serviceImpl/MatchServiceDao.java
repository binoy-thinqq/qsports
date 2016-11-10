package com.thinqq.qsports.serviceImpl;

import java.util.Map;

import javax.jdo.PersistenceManager;

import com.thinqq.qsports.event.DO.CricketMatchDo;
import com.thinqq.qsports.service.MatchService;

public class MatchServiceDao extends BaseDAO implements MatchService{

	@Override
	public String getMatchInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String createMatch(Map<String,Object> paramMap){
		CricketMatchDo cricketMatch = new CricketMatchDo();
		return persistObject(cricketMatch);
	}

}
