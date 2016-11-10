package com.thinqq.qsports.resource;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.DO.dto.UserDto;
import com.thinqq.qsports.service.MatchService;
import com.thinqq.qsports.serviceImpl.MatchServiceDao;

@Controller
@RequestMapping("/match")
public class MatchResource {
	
	private static MatchService service = new MatchServiceDao();
	@RequestMapping(value="/create", method=RequestMethod.POST,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public String createMatch(Map<String,Object> paramMap) {

		service.createMatch(paramMap);
		return null;
		//return new UserServiceImpl().registerUser(username, password, name, email);
	}	

	@RequestMapping(value="/getmatch/{matchId}", method=RequestMethod.GET,headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserDto getUser(@PathVariable String matchID){
		service.getMatchInfo();
		return null;
		//return new UserServiceImpl().getUserProfile(matchID);
	}
}
