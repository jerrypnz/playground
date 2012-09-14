package com.jstudio.jrestoa.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.Appointment;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.ReceivedMail;
import com.jstudio.jrestoa.rss.RSSChanel;
import com.jstudio.jrestoa.rss.RSSItem;
import com.jstudio.jrestoa.rss.RSSItemComparator;
import com.jstudio.jrestoa.util.XMLUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;
import com.thoughtworks.xstream.XStream;

@URLMapping("/rss/{user_id}")
public class RSSFeedService extends AbstractRESTResource
{

	private Log log = LogFactory.getLog(RSSFeedService.class);
	
	@Override
	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		String userName = params.get("user_id");
		final Employee user = Employee.findByLoginName(userName);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		// --------------------------------------------------------------------
		RSSChanel chanel = new RSSChanel();
		chanel.setTitle(user.getName() + "的个性化RSS feed");
		chanel.setDescription(user.getName() + "的个性化RSS feed");
		List<ReceivedMail> mails = user.receiveMail();
		List<Appointment> apps = user.listNoticeAppointments(new Timestamp(
				System.currentTimeMillis()));
		for(ReceivedMail mail : mails)
			mail.addToChannel(chanel);
		for(Appointment app : apps)
			app.addToChannel(chanel);
		Collections.sort(chanel.getItems(),new RSSItemComparator());
		XStream xs = new XStream();
		xs.alias("item", RSSItem.class);
		xs.alias("channel", RSSChanel.class);
		xs.addImplicitCollection(RSSChanel.class, "items");
		String result = xs.toXML(chanel);
		log.debug(result);
		out.println(XMLUtil.xmlHeader);
		out.println("<rss version=\"2.0\">");
		out.println(result);
		out.println("</rss>");
		
	}

}
