package com.thinqq.qsports.server.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.persistence.dto.AddMemberToTeamVo;
import com.thinqq.qsports.persistence.dto.AddPlayerToMatchResponse;
import com.thinqq.qsports.persistence.dto.BaseResponseVo;
import com.thinqq.qsports.persistence.dto.BaseVo;
import com.thinqq.qsports.persistence.dto.BattingScorecardEntryVo;
import com.thinqq.qsports.persistence.dto.BowlingScorecardEntryVo;
import com.thinqq.qsports.persistence.dto.CountriesListVo;
import com.thinqq.qsports.persistence.dto.CountryTimeZoneVo;
import com.thinqq.qsports.persistence.dto.CricketMatchResponseVo;
import com.thinqq.qsports.persistence.dto.CricketMatchVo;
import com.thinqq.qsports.persistence.dto.CricketProfileMinVo;
import com.thinqq.qsports.persistence.dto.CricketProfileVo;
import com.thinqq.qsports.persistence.dto.CricketTeamListResponse;
import com.thinqq.qsports.persistence.dto.CricketTeamListVo;
import com.thinqq.qsports.persistence.dto.CricketTeamPlayerMatchVo;
import com.thinqq.qsports.persistence.dto.CricketTeamResponseVo;
import com.thinqq.qsports.persistence.dto.CricketTeamVo;
import com.thinqq.qsports.persistence.dto.InningsMinResponseVo;
import com.thinqq.qsports.persistence.dto.LoginResponseVo;
import com.thinqq.qsports.persistence.dto.MatchInningsDetailsResponse;
import com.thinqq.qsports.persistence.dto.NotificationVo;
import com.thinqq.qsports.persistence.dto.ReviseMatchTargetVo;
import com.thinqq.qsports.persistence.dto.SaveUserVo;
import com.thinqq.qsports.persistence.dto.TossInfoVo;
import com.thinqq.qsports.persistence.dto.UserRegisterVo;
import com.thinqq.qsports.persistence.dto.UserVo;
import com.thinqq.qsports.persistence.validation.BasicValidatorAdapter;
import com.thinqq.qsports.server.error.ErrorInfo;
import com.thinqq.qsports.server.error.InvalidRequestArgumentException;
import com.thinqq.qsports.server.error.QSportsServiceException;
import com.thinqq.qsports.server.error.UnauthorizedException;
import com.thinqq.qsports.server.process.AuthenticateUserProcess;
import com.thinqq.qsports.server.process.CricketMatchProcess;
import com.thinqq.qsports.server.process.CricketTeamProcess;
import com.thinqq.qsports.server.process.NotificationProcess;
import com.thinqq.qsports.server.process.UserProfileProcess;
import com.thinqq.qsports.server.process.UtilProcess;
import com.thinqq.qsports.shared.userprofile.CricketStatisticsResponseVo;

@Controller
@RequestMapping("ckt")
public class QCricketRDSController {

	private static final String SIGNED_IN_USER = "signedInUser";

	Logger logger = Logger.getLogger(QCricketRDSController.class.getName());
	
	@Autowired
	UserProfileProcess userProcess;

	@Autowired
	AuthenticateUserProcess authenticateProcess;
	
	@Autowired
	CricketTeamProcess cricketTeamProcess;
	
	@Autowired
	NotificationProcess notificationProcess;
	
	@Autowired
	CricketMatchProcess cricketMatchProcess;
	
	@Autowired
	UtilProcess utilProcess;
	
	BasicValidatorAdapter validator;
	
	@RequestMapping(value = "getuser/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public UserVo getUserProfile(@PathVariable Integer id, HttpServletRequest request) {
		UserVo signedInUser = getSignedInUser(request);
		UserVo userVo = userProcess.getUserById(id, signedInUser.getUserId(), signedInUser.getProfileId());
		return userVo;
	}

	@RequestMapping(value = "quser/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public UserVo getUserInfo(@PathVariable Integer id,HttpServletRequest request) {
		UserVo signedInUser = getSignedInUser(request);
		if (id == 0) {
			id = signedInUser.getUserId();
		}
		UserVo userVo = userProcess.getUserById(id, signedInUser.getUserId(), signedInUser.getProfileId());
		return userVo;
	}
	

	@RequestMapping(value = "quser/{id}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public UserVo saveUser(@RequestBody SaveUserVo saveUserVo,
			HttpServletRequest httpRequest)
			throws InvalidRequestArgumentException, UnauthorizedException {
		setSignedInUser(saveUserVo, httpRequest);
		validate(saveUserVo);
		UserVo userVo = userProcess.saveUser(saveUserVo);
		return userVo;
	}
	
	/**
	 * Get Cricket Profile Using userId
	 * @param id
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "quser/{id}/cricketprofile", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketProfileVo getCricketProfileByUserId(@PathVariable Integer id, HttpServletRequest httpRequest) {
		UserVo user = userProcess.getUserById(id);
		CricketProfileVo response = userProcess.getCricketProfileByUserId(user.getUserId(),
				!false);
		setSignedInUser(response, httpRequest);
		return response;
	}
	
	@RequestMapping(value = "cricketprofile/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketProfileVo getCricketProfile(@PathVariable Integer id, HttpServletRequest httpRequest) {
		CricketProfileVo response = userProcess.getCricketProfile(id, !false);
		setSignedInUser(response, httpRequest);
		return response;
	}

	@RequestMapping(value = "cricketprofile/{id}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketProfileMinVo saveCricketProfile(
			@RequestBody CricketProfileMinVo cricketProfileVo,
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException {
		validate(cricketProfileVo);
		setSignedInUser(cricketProfileVo, httpRequest);
		userProcess.saveCricketProfile(cricketProfileVo);
		return cricketProfileVo;
	}


	@RequestMapping(value = "login", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public LoginResponseVo login(@RequestBody UserVo request, HttpServletResponse response)
			throws InvalidRequestArgumentException, UnauthorizedException, IOException {
		validate(request);
		LoginResponseVo authKey = authenticateProcess.authenticate(request);
		if ((request.getRegistrationType().equals("GOOGLE")
				|| request.getRegistrationType().equals("FACEBOOK"))) {
			if (authKey.getAuthKey() == null) {
			    throw new UnauthorizedException("Cannot Authenticate User - Invalid Credentials - User might be deactivated -or- Check Application Credentials");
			}
		}
		Cookie cookie = new Cookie("QSPORTS_AUTH_KEY", authKey.getAuthKey());
		response.addCookie(cookie);
		return authKey;
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public UserRegisterVo register(@RequestBody UserRegisterVo request, HttpServletResponse response)
			throws InvalidRequestArgumentException, UnauthorizedException, IOException {
		validate(request);
		return authenticateProcess.register(request);
	}
	

	@RequestMapping(value = "logout", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws InvalidRequestArgumentException, UnauthorizedException, IOException {
		String authKey = ((HttpServletRequest) request)
				.getHeader("AUTH_KEY");
		boolean logoutSuccessful = authenticateProcess.unauthenticate(authKey);
		if (logoutSuccessful) {
			return;
		} else {
			throw new UnauthorizedException("Unable to perform logout operation - Invalid Credentials - User might be deactivated -or- Check credentials");
		}
	}


	@RequestMapping(value = "cricketprofile/{pid}/stats", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public CricketStatisticsResponseVo getProfileStatistics(@PathVariable Integer pid) {
		CricketStatisticsResponseVo response = new CricketStatisticsResponseVo();
		//response.setBattingStats()
		return response;
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public CricketMatchVo testme() {
		CricketMatchVo matchVo = new CricketMatchVo();
		matchVo.setMatchDate(new Date());
		return matchVo;
	}
	
	@RequestMapping(value = "team/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketTeamResponseVo getTeam(@PathVariable Integer id, @RequestParam(value="ismin",required=false) boolean ismin,
			HttpServletRequest httpRequest) {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		CricketTeamResponseVo cricketTeamVo = cricketTeamProcess.getTeamById(id, !ismin, signedInUser.getUserId());
		return cricketTeamVo;
	}
	
	@RequestMapping(value = "team/{id}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketTeamResponseVo createTeam(@PathVariable Integer id, @RequestBody CricketTeamVo teamVo,
			HttpServletRequest httpRequest)
			throws InvalidRequestArgumentException, QSportsServiceException {
		setSignedInUser(teamVo, httpRequest);
		validate(teamVo);
		
		CricketTeamResponseVo response = new CricketTeamResponseVo();
		if (id == 000) {
			cricketTeamProcess.createNewTeam(teamVo, response);
		} else {
			cricketTeamProcess.saveTeam(teamVo, response);
		}
		return response;
	}
	
	
	@RequestMapping(value = "cricketprofile/{pid}/teams", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public  List<CricketTeamListResponse> getTeams(@PathVariable Integer pid, @RequestParam(value="ismodonly",required=false) boolean isModonly, HttpServletRequest httpRequest) {
		return cricketTeamProcess.getTeams(pid, isModonly);
	}
	
	@RequestMapping(value = "team/{tid}/cricketprofile/{pid}/jointeam", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public AddMemberToTeamVo joinTeam(@PathVariable Integer tid,@PathVariable Integer pid,@RequestParam(value="status",required=false) boolean status, 
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException, UnauthorizedException {
		AddMemberToTeamVo responseVo = new AddMemberToTeamVo();
		setSignedInUser(responseVo, httpRequest);
		responseVo.setTeamId(tid);
		responseVo.setRejected(status);
		responseVo.setProfileId(pid);
		cricketTeamProcess.addMemberToTeam(responseVo, true);
		return responseVo;
	}

	@RequestMapping(value = "team/{tid}/cricketprofile/{pid}/addplayer", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public AddMemberToTeamVo addPlayer(@PathVariable Integer tid,@PathVariable Integer pid,@RequestParam(value="status",required=false) boolean status, 
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException, UnauthorizedException {
		AddMemberToTeamVo responseVo = new AddMemberToTeamVo();
		setSignedInUser(responseVo, httpRequest);
		responseVo.setTeamId(tid);
		responseVo.setProfileId(pid);
		responseVo.setRejected(status);
		cricketTeamProcess.addMemberToTeam(responseVo, false);
		return responseVo;
	}
	
	@RequestMapping(value = "team/{tid}/cricketprofile/{pid}/addmod", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public AddMemberToTeamVo addAsModerator(@PathVariable Integer tid,@PathVariable Integer pid,
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException, UnauthorizedException {
		AddMemberToTeamVo responseVo = new AddMemberToTeamVo();
		setSignedInUser(responseVo, httpRequest);
		responseVo.setTeamId(tid);
		responseVo.setProfileId(pid);
		cricketTeamProcess.addAsModerator(responseVo);
		return responseVo;
	}
	
	@RequestMapping(value = "getteamsbyplayer/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public  List<CricketTeamListVo> getTeamByPlayer(@PathVariable Integer id) {
		 List<CricketTeamListVo> cricketTeamVo = cricketTeamProcess.getTeamByPlayer(id);
		return cricketTeamVo;
	}
	

	
	@RequestMapping(value = "country/all", method= RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CountriesListVo getAllCountries(){
		 CountriesListVo countriesList = new CountriesListVo(utilProcess.getAllCountries()) ;
		 return countriesList;
	}
	
	@RequestMapping(value = "country/{countryCode}/timezone", method= RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CountryTimeZoneVo getTimeZoneForCountry(@PathVariable String countryCode){
		CountryTimeZoneVo countryTimezoneVo = utilProcess.getTimezoneForCountry(countryCode) ;
		 return countryTimezoneVo;
	}
	
	@RequestMapping(value = "getnotifications", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public  List<NotificationVo> getNotifications(HttpServletRequest httpRequest) {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		List<NotificationVo> notifications = notificationProcess.getNotifications(signedInUser.getUserId());
		return notifications;
	}

	@RequestMapping(value = "savematch", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketMatchResponseVo saveMatch(
			@RequestBody CricketMatchVo matchRequesVo,
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException, UnauthorizedException, QSportsServiceException {
		//validate(matchRequesVo);
		setSignedInUser(matchRequesVo, httpRequest);
		CricketMatchResponseVo response = new CricketMatchResponseVo();
		cricketMatchProcess.saveMatch(matchRequesVo, response);
		return response;
	}
	
	@RequestMapping(value = "getmatch/{id}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public CricketMatchResponseVo getMatch(@PathVariable Integer id, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		CricketMatchResponseVo response = new CricketMatchResponseVo();
		cricketMatchProcess.getMatch(id, signedInUser.getUserId(), response);
		return response;
	}
	
	@RequestMapping(value = "match/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo abandonMatch(@PathVariable Integer id, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		BaseResponseVo response = new BaseResponseVo();
		cricketMatchProcess.abandonMatch(id, response, signedInUser.getUserId());
		return response;
	}
	
	@RequestMapping(value = "match/{mid}/complete", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo completeMatch(@PathVariable Integer mid, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		BaseResponseVo response = new BaseResponseVo();
		cricketMatchProcess.completeMatch(mid, response, signedInUser.getUserId());
		return response;
	}
	
	@RequestMapping(value = "addplayertomatch", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public AddPlayerToMatchResponse addPlayerToMatch(
			@RequestBody CricketTeamPlayerMatchVo addMember,
			HttpServletRequest httpRequest) throws InvalidRequestArgumentException, UnauthorizedException {
		validate(addMember);
		setSignedInUser(addMember, httpRequest);
		AddPlayerToMatchResponse response = new AddPlayerToMatchResponse();
		cricketMatchProcess.addPlayerToMatch(addMember, response);
		return response;
	}
	
	@RequestMapping(value = "removeplayermatch/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo removePlayerFromMatch(@PathVariable Integer id, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		BaseResponseVo response = new BaseResponseVo();
		cricketMatchProcess.removePlayerFromMatch(id, response, signedInUser.getUserId());
		return response;
	}
	
	//This service will be added later. Data Model needs to be changed for soft delete
	@RequestMapping(value = "removeteam/{id}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo removeTeam(@PathVariable Integer id, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		BaseResponseVo response = new BaseResponseVo();
	//	cricketTeamProcess.addAsModerator(addModerator);
		cricketMatchProcess.removePlayerFromMatch(id, response, signedInUser.getUserId());
		return response;
	}
	
	@RequestMapping(value = "match/{mid}/start", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public InningsMinResponseVo startMatch(@PathVariable Integer mid,
			@RequestBody TossInfoVo request,
			HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		setSignedInUser(request, httpRequest);
		request.setMatchId(mid);
		InningsMinResponseVo response = new InningsMinResponseVo();
		cricketMatchProcess.startMatch(request, response);
		return response;
	}
	
	@RequestMapping(value = "addinnings/{matchid}", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public InningsMinResponseVo addInnings(
			@PathVariable Integer matchid,
			HttpServletRequest httpRequest,@RequestParam(value="isfon",required=false) boolean isfon) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		InningsMinResponseVo response = new InningsMinResponseVo();
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		cricketMatchProcess.addInnings(matchid, signedInUser.getUserId(),isfon, response);
		return response;
	}
	
	/*
	@RequestMapping(value = "test", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public List<BowlingScorecardEntryVo>  test() throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		List<BowlingScorecardEntryVo> response = new ArrayList<BowlingScorecardEntryVo>();
		BowlingScorecardEntryVo op1 = new BowlingScorecardEntryVo();
		BowlingScorecardEntryVo op2 = new BowlingScorecardEntryVo();
		response.add(op1);
		response.add(op2);
		return response;
	}*/
	
	@RequestMapping(value = "battingcardupdate", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo updateBattingEntry(
			@RequestParam(value="iid",required=true) Integer inningsid,
			@RequestBody List<BattingScorecardEntryVo> battingEntries,
			HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		BaseResponseVo response = new BaseResponseVo();
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		cricketMatchProcess.updateBattingEntries(battingEntries, inningsid, signedInUser.getSignedInUserId(), response);
		return response;
	}
	
	@RequestMapping(value = "bowlingcardupdate", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo updateBowlingEntry(
			@RequestParam(value="iid",required=true) Integer inningsid,
			@RequestBody List<BowlingScorecardEntryVo> bowlEntries,
			HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		BaseResponseVo response = new BaseResponseVo();
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		cricketMatchProcess.updateBowlingEntries(bowlEntries, inningsid, signedInUser.getSignedInUserId(), response);
		return response;
	}
	
	@RequestMapping(value = "match/{mid}/innings", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public MatchInningsDetailsResponse  getInnings(@PathVariable Integer mid, HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		MatchInningsDetailsResponse response  = new MatchInningsDetailsResponse();
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		cricketMatchProcess.getInnings(mid, signedInUser.getUserId(), response);
		return response;
	}
	
	
	@RequestMapping(value = "match/{mid}/revisetarget", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public BaseResponseVo reviseTarget(@PathVariable Integer mid, @RequestBody ReviseMatchTargetVo request,
			HttpServletRequest httpRequest) throws QSportsServiceException, InvalidRequestArgumentException, UnauthorizedException {
		setSignedInUser(request, httpRequest);
		BaseResponseVo response = new BaseResponseVo();
		cricketMatchProcess.reviseTarget(request, response);
		return response;
	}
	
	@Autowired
	public void setUserProcess(UserProfileProcess userProcess) {
		this.userProcess = userProcess;
	}

	@Autowired
	public void setAuthenticateProcess(
			AuthenticateUserProcess authenticateProcess) {
		this.authenticateProcess = authenticateProcess;
	}

	public UserProfileProcess getUserProcess() {
		return userProcess;
	}

	public AuthenticateUserProcess getAuthenticateProcess() {
		return authenticateProcess;
	}
	
	

	public NotificationProcess getNotificationProcess() {
		return notificationProcess;
	}

	@Autowired
	public void setNotificationProcess(NotificationProcess notificationProcess) {
		this.notificationProcess = notificationProcess;
	}

	/**
	 * Set Signed user
	 * @param request
	 * @param httpRequest
	 */
	private void setSignedInUser(BaseVo request, HttpServletRequest httpRequest) {
		UserVo signedInUser = (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
		request.setSignedInUserId(signedInUser.getUserId());
	}
	
	private UserVo getSignedInUser(HttpServletRequest httpRequest) {
		return (UserVo) httpRequest.getAttribute(SIGNED_IN_USER);
	}
	/**
	 * Validate a request
	 * @param request
	 * @throws InvalidRequestArgumentException
	 */
	private void validate(Object request) throws InvalidRequestArgumentException {
		List<ErrorInfo> errors = validator.validate(request);
		if (errors != null && !errors.isEmpty()) {
			throw new InvalidRequestArgumentException(errors);
		}
	}

	/**
	 * Set the validator to use.
	 * @param validator
	 */
	public void setValidator(BasicValidatorAdapter validator) {
		this.validator = validator;
	}
	
}
