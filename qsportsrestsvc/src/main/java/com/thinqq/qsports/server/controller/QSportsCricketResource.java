package com.thinqq.qsports.server.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.server.error.InvalidRequestArgumentException;
import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.shared.QSportsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.UserInfoRequestVo;
import com.thinqq.qsports.shared.userprofile.UserInfoResponseVo;

@Controller
@RequestMapping("cricket")
public class QSportsCricketResource {
	UserProfileProcess userProcess;
	
	@RequestMapping(value="getuser/{userkey}", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserInfoResponseVo getUserProfile(@PathVariable String userkey){
		UserInfoRequestVo request = new UserInfoRequestVo();
		request.setKey(userkey);
		request.setSignedInUserKey(userkey);
		UserInfoResponseVo response = new UserInfoResponseVo();
		userProcess.getUserProfile(request, response);
		return response;
	}
	
	@RequestMapping(value="getuseremail", 
			method=RequestMethod.POST, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserInfoResponseVo getUserProfileWithEmail(@RequestBody String email){
		UserInfoRequestVo request = new UserInfoRequestVo();
		request.setEmail(email);
		UserInfoResponseVo response = new UserInfoResponseVo();
		userProcess.getUserProfile(request, response);
		return response;
	}
	
	

	@RequestMapping(value="login", 
			method=RequestMethod.POST, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public String login(@RequestBody UserInfoRequestVo request) throws InvalidRequestArgumentException{
		
		UserInfoResponseVo response = new UserInfoResponseVo();
		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			userProcess.saveAndLogin(request, response);
		} else {
			throw new InvalidRequestArgumentException("Please pass email to fetch the data");
		}
		byte[] keyBytes = Base64.encodeBase64(response.getUserKey().getBytes());
		String authKey = null;
		try {
			authKey = new String(keyBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authKey;
	}
	
	@RequestMapping(value="test", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserInfoRequestVo test() throws InvalidRequestArgumentException{
		UserInfoRequestVo request = new UserInfoRequestVo();
		return request;
	}
	
	
	@RequestMapping(value="saveuser", 
			method=RequestMethod.POST, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public boolean saveUser(@RequestBody UserInfoRequestVo request, HttpServletRequest httpRequest){
		setSignedInUser(request, httpRequest);
		userProcess.saveUserInfo(request);
		return true;
	}

	@RequestMapping(value="getcricketprofile/{userKey}&isMin={isMin}", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public CricketProfileResponseVo getCricketProfile(@PathVariable String userKey,  @PathVariable boolean isMin){
		CricketProfileResponseVo response = new CricketProfileResponseVo();
		userProcess.getCricketProfile(response, userKey, isMin);
		return response;
	}
	
	@RequestMapping(value="savecricketprofile", 
			method=RequestMethod.POST, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public CricketProfileResponseVo saveCricketProfile(@RequestBody CricketProfileRequestVo request, HttpServletRequest httpRequest){
		CricketProfileResponseVo response = new CricketProfileResponseVo();
		setSignedInUser(request, httpRequest);
		userProcess.saveCricketProfile(request, response);
		return response;
	}
	
	
	@RequestMapping(value="stats/{userkey}&ismin={ismin}", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	public CricketStatisticsResponseVo getCricketStatistics(@PathVariable String userkey, @PathVariable boolean ismin){
		CricketStatisticsRequestVo request = new CricketStatisticsRequestVo();
		request.setCricketProfileKey(userkey);
		request.setMinimumStatsReq(ismin);
		CricketStatisticsResponseVo response = new CricketStatisticsResponseVo();
		userProcess.getCricketStatistics(request, response);
		return response;
	}

	
	@Autowired
	public void setUserProcess(UserProfileProcess userProcess) {
		this.userProcess = userProcess;
	}
	
	private void setSignedInUser(QSportsRequestVo request,
			HttpServletRequest httpRequest) {
		UserInfoResponseVo signedInUser = (UserInfoResponseVo)httpRequest.getAttribute("signedInUser");
		request.setSignedInUserKey(signedInUser.getUserKey());
	}
}
