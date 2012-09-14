package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.service.factory.JdbcServiceFactory;
import com.zhaonan.forum.service.factory.ServiceFactory;

/**
 * Servlet implementation class for Servlet: BaseServlet
 * 
 */
public abstract class BaseServlet extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	private ServiceFactory serviceFactory = null;

	/**
	 * @return the serviceFactory
	 */
	public ServiceFactory getServiceFactory()
	{
		return serviceFactory;
	}

	public BaseServlet()
	{
		serviceFactory = new JdbcServiceFactory();
	}
		

	public final void toView(final String viewName, HttpServletRequest request,
			HttpServletResponse response)
	{
		Properties views = (Properties)this.getServletContext().getAttribute("view_mapping"); 
		String viewPath = views.getProperty(viewName);
		try
		{
			System.out.println("Forwarding to view:" + viewName + "[" + viewPath +"]");
			request.getRequestDispatcher(viewPath).forward(request, response);
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}