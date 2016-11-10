package com.thinqq.qsports.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinqq.qsports.persistence.conf.SessionManager;
import com.thinqq.qsports.persistence.dao.CricketMatchDetailsDAO;
import com.thinqq.qsports.persistence.dataobjects.CricketInningsDetails;

@Controller
@RequestMapping("cktds")
public class QsportsMatchDetailsController {

	@RequestMapping(value = "innings", method = RequestMethod.PUT, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public String createInnings(@RequestBody String inningsDetails, HttpServletRequest request) {
		Integer inningsID = 0;
		Session session = SessionManager.getSessionFactory().openSession();
		try{
		CricketInningsDetails details = new CricketInningsDetails();
		JSONObject obj = new JSONObject(inningsDetails);
		String matchName = obj.get("name").toString();
		details.setMatchName(matchName);
		details.setInningsDetails(inningsDetails);
		CricketMatchDetailsDAO dao = new CricketMatchDetailsDAO();
		session.beginTransaction();
		 inningsID = dao.insert(session, details);
		 session.getTransaction().commit();
		
	} catch (Exception e) {
		e.printStackTrace();
		if (session != null && session.isOpen()) {
			session.getTransaction().rollback();
		}
	} finally {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
		return Integer.toString(inningsID);
	}
	
	@RequestMapping(value = "innings/{id}", method = RequestMethod.POST, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public String updateInnings(@RequestBody String inningsDetails,@PathVariable Integer id, HttpServletRequest request) {
		CricketInningsDetails details = new CricketInningsDetails();
		details.setInningsDetails(inningsDetails);
		CricketMatchDetailsDAO dao = new CricketMatchDetailsDAO();
		Session session = SessionManager.getSessionFactory().openSession();
		session.beginTransaction();
		dao.update(session, details);
		 session.getTransaction().commit();
		return inningsDetails;
	}
	
	@RequestMapping(value = "innings/{id}&callback={callbackfn}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	@ResponseBody
	public Response readInnings(@PathVariable Integer id, HttpServletRequest request, @PathVariable String callbackfn) {
		CricketMatchDetailsDAO dao = new CricketMatchDetailsDAO();
		Session session = SessionManager.getSessionFactory().openSession();
		CricketInningsDetails details =  dao.getByPrimaryKey(session, id);
		ResponseBuilder builder = Response.ok(callbackfn + "({" + details.getInningsDetails() + "})", "application/javascript");
		return builder.build();
	}
	
	
}
