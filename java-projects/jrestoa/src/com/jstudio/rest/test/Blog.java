package com.jstudio.rest.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/blog/{blog_id}")
public class Blog extends AbstractRESTResource
{

	@Override
	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			out.println("<h2>Blog(id:" + params.get("blog_id") + ")</h2>");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
