package com.thinqq.qsports.service;

import com.thinqq.qsports.DO.dto.UserDto;

//User related api declaration
public interface UserService {

	String registerUser(String username, String password, String name, String email) throws NullPointerException;
	UserDto getUserProfile(String userId);
	String authUser(String username, String password);
}
