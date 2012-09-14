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
import com.jstudio.jrestoa.domain.PersonalFile;
import com.jstudio.jrestoa.util.XMLUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/file/{file_id}")
public class PersonalFileService extends AbstractRESTResource
{
	private static Log log = LogFactory.getLog(PersonalFileService.class);

	@Override
	public void delete(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{

	}

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
		}

		String temp = params.get("file_id");
		if ("list".equalsIgnoreCase(temp))
		{
			List<PersonalFile> files = user.listPersonalFiles();
			out.println(XMLUtil.xmlHeader);
			String result = XMLUtil.listToXML(files, "files");
			log.debug(result);
			out.println(result);
		}
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
