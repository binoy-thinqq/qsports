package com.thinqq.qsports.test;

import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.codehaus.jackson.map.ObjectMapper;

import com.thinqq.qsports.persistence.dto.UserVo;

public class LoginAPITest extends TestCase {

	private String randomEmail = "email"+Math.round(Math.random()*1000000)+"@gmail.com";
	private static String loginKeyUser1 = null;
	private static String loginKeyUser2 = null;
	ObjectMapper mapper = new ObjectMapper();
	public void testLoginService(){
		Resource loginResource = RestClientUtil.getLoginResource(RestClientUtil.DEV_ENVIRONMENT);
		UserVo userVo = new UserVo();
		userVo.setFirstName(randomEmail+"111");
		userVo.setLastName("User1");
		userVo.setDob(Calendar.getInstance().getTime());
		userVo.setEmail("User1"+randomEmail);
		userVo.setRegistrationType("GOOGLE");
		ClientResponse response = null;
		try{
		String loginPayload = mapper.writeValueAsString(userVo);
		response = loginResource.post(loginPayload);
		} catch(Exception e){
			e.printStackTrace();
			assertTrue("Exception in connecting to login service",false);
		}
		
		assertNotNull(response);
		assertEquals(200, response.getStatusCode());
		assertNotNull(response.getEntity(String.class));;
		loginKeyUser1 = response.getEntity(String.class);
		System.out.print("Email and Login Key of User 1:"+userVo.getEmail()+";"+loginKeyUser1);
	}
	
	public void testLoginServiceWithAllDetails() {
		Resource loginResource = RestClientUtil.getLoginResource(RestClientUtil.DEV_ENVIRONMENT);
		UserVo userVo = new UserVo();
		userVo.setFirstName(randomEmail+"2");
		userVo.setLastName("User2");
		userVo.setDob(Calendar.getInstance().getTime());
		userVo.setEmail("User2"+randomEmail);
		userVo.setRegistrationType("GOOGLE");
		userVo.setCity("Chennai");
		userVo.setContactShown(true);
		userVo.setGender("M");
		userVo.setIsoCountryCode("IN");
		userVo.setMobileNumber("92832088292");
		userVo.setNotes("Here is where the user tells about oneself so do not worry about it.");
		userVo.setPassword("sadfsad");
		userVo.setPictureUrl("picture URL");
		userVo.setState("Tamil Nadu");
		userVo.setStatus("CRAPPIEST");
		userVo.setTimezone("UTC");
		userVo.setUserId(23423);
		//userVo
		ClientResponse response = null;
		try{
		String loginPayload = mapper.writeValueAsString(userVo);
		response = loginResource.post(loginPayload);
		} catch(Exception e){
			e.printStackTrace();
			assertTrue("Exception in connecting to login service",false);
		}
		
		assertNotNull(response);
		assertNotNull(response.getEntity(String.class));;
		loginKeyUser2 = response.getEntity(String.class);
		assertEquals(200, response.getStatusCode());
		System.out.print("Email and Login Key of User 1:"+userVo.getEmail()+";"+loginKeyUser2);
	}
	
	public void testLogoutService() {
		Resource logoutResource = RestClientUtil.getLogoutResource(RestClientUtil.DEV_ENVIRONMENT);
		logoutResource.header("AUTH_KEY", loginKeyUser2);
		ClientResponse response = logoutResource.post("");
		assertNotNull(response);
		assertEquals(200, response.getStatusCode());
		assertNotNull(response.getEntity(String.class));;
		
		Resource logoutResourceFailure = RestClientUtil.getLogoutResource(RestClientUtil.DEV_ENVIRONMENT);
		logoutResourceFailure.header("AUTH_KEY", loginKeyUser2);
		ClientResponse responseFailure = logoutResourceFailure.post("");
		assertNotNull(responseFailure);
		assertEquals(401, responseFailure.getStatusCode());
		assertNotNull(responseFailure.getEntity(String.class));;
	}
}
