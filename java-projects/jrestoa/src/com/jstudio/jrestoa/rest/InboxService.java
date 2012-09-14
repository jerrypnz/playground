package com.jstudio.jrestoa.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.ReceivedMail;
import com.jstudio.jrestoa.util.XMLUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/mail/inbox/{mail_id}")
public class InboxService extends AbstractRESTResource
{
	
	private static Log log = LogFactory.getLog(InboxService.class);

	@Override
	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Employee user = (Employee) session
				.getAttribute(RestConstants.SESSION_KEY_USER);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		// ---------------------------------------------------------
		if ("list".equalsIgnoreCase(params.get("mail_id")))
		{
			List<ReceivedMail> inboxMails = user.receiveMail();
			out.println(XMLUtil.xmlHeader);
			String result = XMLUtil.listToXML(inboxMails, "inboxMails");
			log.debug(result);
			out.println(result);
		}
		else
		{
			Long mailId = Long.parseLong(params.get("mail_id"));
			ReceivedMail mail = ReceivedMail.findById(mailId);
			response.setContentType("text/html");
			String body = mail.getBody();
			log.debug(body);
			out.println(body);
		}
	}

}
