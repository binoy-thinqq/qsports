package com.thinqq.qsports.resource;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class QsportsrestsvcServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
