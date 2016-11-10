package com.thinqq.qsports.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.DO.dto.UserDto;
import com.thinqq.qsports.serviceImpl.UserServiceImpl;
@Controller
@RequestMapping("/userservice")
public class UserResources {

	public UserResources(){}
	//method to serve the user registration 
	//
	@RequestMapping(value="/register", method=RequestMethod.POST,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public String registerUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("name") String name, @FormParam("email") String email ) {
		return new UserServiceImpl().registerUser(username, password, name, email);
	}		
		
	@RequestMapping(value="/user/{userID}", method=RequestMethod.GET,headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserDto getUser(@PathVariable String userID){
		return new UserServiceImpl().getUserProfile(userID);
	}
	
	@RequestMapping(value="/auth", method=RequestMethod.POST,headers="Content-Type=application/x-www-form-urlencoded")
	@ResponseBody
	public String authUser(@FormParam("username") String username, @FormParam("password") String password, HttpServletResponse response){
		return new UserServiceImpl().authUser(username, password);
	}	
}
