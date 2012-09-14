package com.zhaonan.forum.servlets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;

public class ConfigInitServlet extends javax.servlet.http.HttpServlet
{
	static final long serialVersionUID = 1L;

	private static void defaultMapping(Properties props)
	{
		props.put("board_list", "views/boards.jsp");
		props.put("topic_list", "views/topic_list.jsp");
		props.put("topic_detail", "views/topic.jsp");
		props.put("register", "views/register.jsp");
		props.put("login", "views/login.jsp");
	}

	@Override
	public void init() throws ServletException
	{
		System.out.println("Loading view mapping.");
		Properties views = new Properties();
		String dir = "";
		try
		{
			dir = this.getInitParameter("viewMappingConfig");
			if (dir == null || "".equals(dir))
				dir = "WEB-INF/views.properties";
			String path = this.getServletContext().getRealPath(dir);
			System.out.println("Loading " + dir);
			FileInputStream is = new FileInputStream(path);
			views.load(is);
			System.out.println("Finished loading view mapping");
		}
		catch (FileNotFoundException e)
		{
			System.out.print("[Warning]" + dir + "not found,using default mapping");
			defaultMapping(views);
		}
		catch (IOException e)
		{
			System.out.print("[Warning]Error reading " + dir + ",using default mapping");
			e.printStackTrace();
			defaultMapping(views);
		}
		this.getServletContext().setAttribute("view_mapping", views);
	}
}