package com.zhaonan.forum.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPicFilter implements Filter
{
	private static String userPicDir = "";
	
	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		String userId = request.getParameter("userid");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		File userIconFile = new File(userPicDir + userId);
		System.out.println("User icon file:" + userIconFile);
		System.out.println("It exits:" + userIconFile.exists());
		res.setHeader("Pragma","No-Cache");
		res.setHeader("Cache-Control","No-Cache");
		res.setDateHeader("Expires",0);
		if(userIconFile.exists())
		{
			req.getRequestDispatcher("UserFiles/UserFaces/" + userId).forward(request, response);
		}
		else
		{
			req.getRequestDispatcher("image/user_logo.gif").forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		userPicDir = config.getServletContext().getRealPath("/UserFiles/UserFaces") + "/";
		System.out.println("User Faces Dir:" + userPicDir);
	}

}
