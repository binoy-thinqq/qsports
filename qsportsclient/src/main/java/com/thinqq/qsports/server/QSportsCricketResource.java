package com.thinqq.qsports.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsRequestVo;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.SaveUserProfileResponseVo;
import com.thinqq.qsports.shared.userprofile.UserProfileRequestVo;
import com.thinqq.qsports.shared.userprofile.UserProfileResponseVo;

@Controller
@RequestMapping("cricket")
public class QSportsCricketResource {
	UserProfileProcess userProcess = UserProfileProcess.getInstance();
	
	@RequestMapping(value="getuser/{userkey}", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserProfileResponseVo getUserProfile(@PathVariable String userkey){
		UserProfileRequestVo request = new UserProfileRequestVo();
		request.setUserKey(userkey);
		request.setSignedInUserKey(userkey);
		UserProfileResponseVo response = new UserProfileResponseVo();
		userProcess.getUserProfile(request, response);
		return response;
	}
	
	@RequestMapping(value="getuseremail/{email}", 
			method=RequestMethod.GET, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public UserProfileResponseVo getUserProfileWithEmail(@PathVariable String email){
		UserProfileRequestVo request = new UserProfileRequestVo();
		request.setEmail(email);
		request.setUseOnlyEmail(true);
		UserProfileResponseVo response = new UserProfileResponseVo();
		userProcess.getUserProfile(request, response);
		return response;
	}
	
	/**
	 * {
  			"signedInUserKey": "ag50aGlucXFjcmlja2V0MnIKCxIEVXNlchgBDA",
  			"serverName": null,
  			"returnToken": null,
  			"timeZone": "TimeZone",
  			"name": "Binoy",
  			"sex": "M",
  			"dob": 1390217713899,
  			"city": "Thrissur",
  			"state": "Kerala",
  			"country": "India",
  			"timeZoneStr": null,
  			"battingHandType": 1,
  			"battingOrder": 2,
  			"bowlingHandType": 3,
  			"bowlingMethod": null,
  			"fieldPosition": 3
		}
	 * 
	 */
	@RequestMapping(value="saveuser", 
			method=RequestMethod.POST, 
			headers="Accept=application/xml, application/json")
	@ResponseBody 
	public SaveUserProfileResponseVo saveUser(@RequestBody SaveUserProfileRequestVo request){
		SaveUserProfileResponseVo response = new SaveUserProfileResponseVo();
		userProcess.saveUserProfile(request, response);
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
	
}
