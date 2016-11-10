package com.thinqq.qsports.service;

import java.util.Map;

public interface MatchService {
	
	public String getMatchInfo();
	public String createMatch(Map<String,Object> paramMap);

}
