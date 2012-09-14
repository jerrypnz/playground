package com.jstudio.jrestoa.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.DomainBase;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Mail;
import com.jstudio.jrestoa.hbm.InTransaction;
import com.jstudio.jrestoa.util.ExtXmlResponseUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/sendmail")
public class SendMailService extends AbstractRESTResource
{
	
	private static Log log = LogFactory.getLog(SendMailService.class);

	@Override
	public void create(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
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
		// ---------------------------------------------------------
		
		final Mail mailToSend = new Mail();
		mailToSend.setTitle(request.getParameter("title"));
		mailToSend.setBody(request.getParameter("body"));
		final String receiver = request.getParameter("receiver");
		log.debug("Receiver:" + receiver);
		log.debug("Title:" + mailToSend.getTitle());
		log.debug("Body:" + mailToSend.getBody());
		if(receiver==null || "".equals(receiver))
		{
			log.debug("receiver is null or empty");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		DomainBase.doInTransaction(new InTransaction()
		{

			@Override
			public void execute() throws Exception
			{
				user.update();
				user.sendMail(receiver, mailToSend);
				out.println(ExtXmlResponseUtil.getSuccessXML());
				log.info("mail sent");
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				log.error("error sending mail:",e);
				out.println(ExtXmlResponseUtil.getErrorXML());
			}

		});
	}

}
