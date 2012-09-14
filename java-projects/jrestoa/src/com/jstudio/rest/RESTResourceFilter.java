package com.jstudio.rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jstudio.rest.annotation.URLMapping;

public class RESTResourceFilter implements Filter
{
	private ResourceRegistry resourceReg = new ResourceRegistry();

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest servletReqest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletReqest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//REST资源的参数，这些参数都包含在URL中
		Map<String, String> params = new HashMap<String, String>();
		String uri = request.getRequestURI();
		uri = uri.replace(request.getContextPath(), "");
		//System.out.println(uri);
		if (uri == null || "".equals(uri))
		{
			filterChain.doFilter(request, response);
			return;
		}
		//从REST资源注册表中查找此URI对应的资源
		RESTResource res = resourceReg.getResource(uri, params);
		if (res == null)
			filterChain.doFilter(servletReqest, servletResponse);
		else
		{
			//根据不同的请求方法调用REST对象的不同方法
			String method = request.getMethod();
			if ("GET".equalsIgnoreCase(method))
				res.get(params, request, response);
			else if ("POST".equalsIgnoreCase(method))
				res.create(params, request, response);
			else if ("PUT".equalsIgnoreCase(method))
				res.update(params, request, response);
			else if ("DELETE".equalsIgnoreCase(method))
				res.delete(params, request, response);
			else
				filterChain.doFilter(servletReqest, servletResponse);
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		String resListFile = config.getInitParameter("resourceListFile");
		if ("".equals(resListFile))
		{
			System.out.println("[WARNING]No resource specified");
			return;
		}
		//获取REST资源配置文件名
		String fileName = config.getServletContext().getRealPath(resListFile);
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String className = "";
			//从文件中读取REST资源类，每一行代表一个类
			while ((className = reader.readLine()) != null)
			{
				if ("".equals(className))
					continue;
				//获取类的Class对象
				Class<?> resourceClass = Class.forName(className);
				//从Class对象中获取注解信息
				if (resourceClass.isAnnotationPresent(URLMapping.class))
				{
					//获取注解中包含的URL映射规则
					//一个资源可以映射到多个URL上，所以这里使用了数组
					String[] urlPatterns = resourceClass.getAnnotation(
							URLMapping.class).value();
					for (String s : urlPatterns)
					{
						//注册此REST资源
						resourceReg.registerResource(s, resourceClass);
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}

}
