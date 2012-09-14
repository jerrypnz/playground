package com.zhaonan.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.User;
import com.zhaonan.forum.service.UserService;

public class LoginServlet extends BaseServlet
{

	private static final long serialVersionUID = 1L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		UserService userService = this.getServiceFactory().getUserService();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if("".equals(username) || "".equals(password))
		{
			request.setAttribute("msg_title", "……");
			request.setAttribute("msgs", new String[]{
				"请输入用户名和密码"	
			});
			toView("msg", request, response);
			return;
		}
		User user = userService.login(username, password);
		if(user == null)
		{
			request.setAttribute("msg_title", "登录失败");
			request.setAttribute("msgs", new String[]{
				"不存在此用户或密码错误"	
			});
			toView("msg", request, response);
			return;
		}
		else
		{
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("boards").forward(request, response);
		}
		
	}

}
