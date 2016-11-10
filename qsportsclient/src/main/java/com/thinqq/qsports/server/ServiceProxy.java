package com.thinqq.qsports.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Servlet used to just forward request to another domain
//to avoid cross domain issue of calling web services in qsportsrestsvc domain
public class ServiceProxy extends HttpServlet{

	private static String SERVICE_BASE_URL="http://qsportsrestsvc.appspot.com";
	private static String DEV_SERVICE_BASE_URL = "http://localhost:8989";

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "DELETE", false);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "GET", false);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "HEAD", false);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "OPTIONS", false);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "POST", false);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        makeTheConnection(req, resp, "PUT", false);
	}
	
	//Metod which makes the connection and set the response
	public void makeTheConnection(HttpServletRequest req,
			HttpServletResponse resp, String method, boolean isDev) {

		try {
			URL url = new URL(isDev || System.getProperty("isDev") !=null?DEV_SERVICE_BASE_URL+req.getPathInfo():SERVICE_BASE_URL+req.getPathInfo());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			
			//Set request params
			Enumeration paramKey = req.getParameterNames();
			StringBuffer message = new StringBuffer();
			while (paramKey.hasMoreElements()) {
				String key = (String) paramKey.nextElement();
				message.append(key);
				message.append("=");
				message.append(req.getParameter(key));
				message.append("&");
			}
			
			//Set request headers
			Enumeration headerKeys = req.getHeaderNames();
			while(headerKeys.hasMoreElements()){
				String key = (String)headerKeys.nextElement();
				connection.setRequestProperty(key, req.getHeader(key));
			}
		
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(message.toString());
			writer.close();

			PrintWriter respWriter = resp.getWriter();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				while (br.ready()) {
					respWriter.write(br.read());
				}
				connection.disconnect();
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
