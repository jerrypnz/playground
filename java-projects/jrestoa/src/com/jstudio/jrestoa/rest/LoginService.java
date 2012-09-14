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

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.util.ExtXmlResponseUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/login")
public class LoginService extends AbstractRESTResource
{

	private static Log log = LogFactory.getLog(LoginService.class);
	
	@Override
	public void create(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		log.debug("logging in");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String username = request.getParameter("user");
		log.debug("username:" + username);
		String password = request.getParameter("password");
		log.debug("password:" + password);
		Employee emp = Employee.findByLoginName(username);
		if (emp == null)
		{
			log.debug("user not exists");
			out.println(ExtXmlResponseUtil.getErrorXML(new Object[]{"user","该用户不存在"}));
			return;
		}
		if(!emp.getLoginPassword().equals(password))
		{
			log.debug("wrong password");
			out.println(ExtXmlResponseUtil.getErrorXML(new Object[]{"password","密码错误"}));
			return;
		}
		session.setAttribute(RestConstants.SESSION_KEY_USER, emp);
		out.println(ExtXmlResponseUtil.getSuccessXML());
	}

}
