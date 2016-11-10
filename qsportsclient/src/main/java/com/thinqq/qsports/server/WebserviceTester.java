package com.thinqq.qsports.server;

import static com.thinqq.qsports.shared.Constants.registerendPoint;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

//Junit test cases API's, call service through PROXY using mock http request, response

public class WebserviceTester {

	@Test
	public void testRegistration() {
		HttpServletRequest req = mock(HttpServletRequest.class);

		when(req.getParameterNames()).thenReturn(new Enumeration<String>() {
			
			HashMap<String, String> paramMap = new HashMap<String, String>() {
				{
					this.put("username", "rakesh");
					this.put("password", "pass");
					this.put("email", "rakesh@thinqq.com");
				}
			};
			
			Iterator<String> it = paramMap.keySet().iterator();
			
			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public String nextElement() {
				return it.next();
			}
		});
		
		//TODO: Need to mock each param once validation implemented on service layer
		when(req.getParameter(anyString())).thenReturn("sample param String");
		
		when(req.getHeader("Content-type")).thenReturn("application/x-www-form-urlencoded");
		when(req.getPathInfo()).thenReturn(registerendPoint.replace("/proxy", ""));
		
		try {
			HttpServletResponse resp = mock(HttpServletResponse.class);
			StringWriter strWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(strWriter);
			when(resp.getWriter()).thenReturn(printWriter);
			new ServiceProxy().makeTheConnection(req, resp, "POST",true);
			assertEquals("success",strWriter.getBuffer().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	void testAuthentication(){
//		
//	}
	
//	@Test
//	void testGetUserProfile(){
//		
//	}

}
