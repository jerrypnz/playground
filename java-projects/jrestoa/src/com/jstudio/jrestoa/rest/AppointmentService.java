package com.jstudio.jrestoa.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.Appointment;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.hbm.InTransaction;
import com.jstudio.jrestoa.util.ExtXmlResponseUtil;
import com.jstudio.jrestoa.util.XMLUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/appointment/{info}")
public class AppointmentService extends AbstractRESTResource
{
	private Log log = LogFactory.getLog(AppointmentService.class);

	@Override
	public void create(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		if (!"new".equalsIgnoreCase(params.get("info")))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.setCharacterEncoding("UTF-8");
		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		final Employee user = (Employee) session
				.getAttribute(RestConstants.SESSION_KEY_USER);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		//----------------------------------------------------------------------
		
		final Appointment app = new Appointment();
		app.setOwner(user);
		String day = request.getParameter("date");
		String startTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");
		Timestamp startTime = Timestamp.valueOf(day + " " + startTimeStr);
		Timestamp endTime = Timestamp.valueOf(day + " " + endTimeStr);
		app.setTitle(request.getParameter("title"));
		app.setDetail(request.getParameter("detail"));
		app.setStartTime(startTime);
		app.setCompleteTime(endTime);
		
		Appointment.doInTransaction(new InTransaction()
		{

			@Override
			public void execute() throws Exception
			{
				app.save();
				out.print(ExtXmlResponseUtil.getSuccessXML());
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				out.print(ExtXmlResponseUtil.getErrorXML());
			}

		});
	}

	@Override
	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		final Employee user = (Employee) session
				.getAttribute(RestConstants.SESSION_KEY_USER);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		//----------------------------------------------------------------------
		String date = params.get("info");
		if(!date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
			Timestamp temp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.format(temp);
		}
		List<Appointment> appointments = user.listTodayAppointments(date);
		String temp = XMLUtil.listToXML(appointments, "appointments");
		log.debug(temp);
		out.println(XMLUtil.xmlHeader);
		out.println(temp);
	}

}
