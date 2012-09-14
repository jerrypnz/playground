package com.jstudio.jrestoa.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintParamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Entering Pring parameter servlet.");
		String resultXml = "success.xml";
		Enumeration paramKeys = request.getParameterNames();
		System.out.println("Incoming request");
		while(paramKeys.hasMoreElements()) {
			String key = (String) paramKeys.nextElement();
			String value = request.getParameter(key);
			if ("success".equalsIgnoreCase(key)
					&& "false".equalsIgnoreCase(value)) {
				resultXml = "error.xml";
			}
			System.out.println(key + " = " + value);
		}
		request.getRequestDispatcher("../dummyXML/" + resultXml).forward(request,
				response);
	}

	/**
	 * Initialization of t	he servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		//System.out.println("System Home Dir:" + System.getProperty("user.dir"));
	}

}
