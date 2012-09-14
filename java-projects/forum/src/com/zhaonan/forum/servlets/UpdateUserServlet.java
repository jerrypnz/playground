package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.User;
import com.zhaonan.forum.service.UserService;

/**
 * Servlet implementation class for Servlet: UpdateUserServlet
 * 
 */
public class UpdateUserServlet extends com.zhaonan.forum.servlets.BaseServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UpdateUserServlet()
	{
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		UserService userService = this.getServiceFactory().getUserService();
		boolean valid = true;
		List<String> errors = new ArrayList<String>();
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			request.setAttribute("errors",new String[]{
					"请先登录再使用本功能"
				});
			request.getRequestDispatcher("login.jsp").forward(request,response);
			return;
		}
		user.setEmail(request.getParameter("email"));
		user.setHomepage(request.getParameter("homepage"));
		user.setQq(request.getParameter("qq"));
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
		
		if(!valid)
		{
			request.setAttribute("errors", errors);
			this.toView("edit_profile", request, response);
		}
		else
		{
			userService.update(user);
			request.getSession().setAttribute("user", user);
			String msgTitle = "操作成功";
			String[] msg = new String[]{
					"更新用户信息成功",
					"您的用户编号为" + user.getId(),
			};
			request.setAttribute("msg_title", msgTitle);
			request.setAttribute("msgs", msg);
			this.toView("msg", request, response);
		}
	}
}