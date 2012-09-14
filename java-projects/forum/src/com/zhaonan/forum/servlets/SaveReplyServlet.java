package com.zhaonan.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.Post;
import com.zhaonan.forum.po.User;

/**
 * Servlet implementation class for Servlet: SaveReplyServlet
 * 
 */
public class SaveReplyServlet extends com.zhaonan.forum.servlets.BaseServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SaveReplyServlet()
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
		try
		{
			User user = (User) request.getSession().getAttribute("user");
			int topicId = Integer.parseInt(request.getParameter("topicid"));
			Post post = new Post();
			post.setContent(request.getParameter("content"));
			post.setTopicId(topicId);
			post.setUserId(user.getId());
			user.setMark(user.getMark() + 5);
			this.getServiceFactory().getPostService().save(post);
			this.getServiceFactory().getUserService().update(user);
			String target = "topic?topicid=" + topicId;
			int page = Integer.parseInt(request.getParameter("current_page")) - 1;
			target = target + "&page=" + page;
			request.getRequestDispatcher(target).forward(request, response);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			request.setAttribute("msg_title", "糟糕，发生了异常");
			request.setAttribute("msgs", new String[]
			{ "服务器端异常：" + e.getMessage() });
			toView("msg", request, response);
		}
	}
}