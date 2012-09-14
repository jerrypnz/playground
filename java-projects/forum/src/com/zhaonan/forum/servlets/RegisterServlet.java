package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.User;
import com.zhaonan.forum.service.UserService;

public class RegisterServlet extends BaseServlet
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RegisterServlet()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		UserService userService = this.getServiceFactory().getUserService();
		boolean valid = true;
		List<String> errors = new ArrayList<String>();
		User user = new User();
		user.setLoginName(request.getParameter("username"));
		user.setNickName(request.getParameter("nickname"));
		user.setEmail(request.getParameter("email"));
		user.setHomepage(request.getParameter("homepage"));
		user.setQq(request.getParameter("qq"));
		user.setMark(0);
		user.setRank(0);
		user.setPassword(request.getParameter("pass1"));

		if ("".equals(user.getLoginName()))
		{
			valid = false;
			errors.add("用户名不能为空");
		}
		if ("".equals(user.getNickName()))
		{
			valid = false;
			errors.add("昵称不能为空");
		}
		if ("".equals(user.getEmail()))
		{
			valid = false;
			errors.add("邮箱不能为空");
		}
		if ("".equals(user.getPassword())
				|| !user.getPassword().equals(request.getParameter("pass2")))
		{
			valid = false;
			errors.add("没有输入密码或者两次输入不一致");
		}
		if(userService.getByName(user.getLoginName()) != null)
		{
			valid = false;
			errors.add("已经存在此用户");
		}
		
		if(!valid)
		{
			request.setAttribute("errors", errors);
			this.toView("register", request, response);
		}
		else
		{
			userService.save(user);
			request.getSession().setAttribute("user", user);
			String msgTitle = "注册成功";
			String[] msg = new String[]{
					"恭喜你注册成功，请牢记您的用户名和密码",
					"您的用户编号为" + user.getId(),
			};
			request.setAttribute("msg_title", msgTitle);
			request.setAttribute("msgs", msg);
			this.toView("msg", request, response);
		}
	}

}
