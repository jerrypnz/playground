package com.jstudio.rest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jstudio.rest.exception.RESTException;

//资源注册表类，所有的REST类都注册在这里
public class ResourceRegistry
{
	//URL映射树的根
	private RouteNode root = new RouteNode();
	//用来匹配URL映射规则中的参数的正则表达式（例如匹配"/blog/{blog_id}"中的blog_id参数
	private Pattern paramPattern = Pattern.compile("\\{([a-zA-Z_]+[0-9]*)\\}");
	//树中的参数节点使用这个作为键，详见文档中的讲解
	private static final String PARAM_KEY = "_$__PARAM_$__";

	//注册REST资源类
	public void registerResource(String uriPattern, Class<?> resourceClass)
	{
		System.out.println("Registering " + uriPattern);
		//以"/"来分割URL,每一部分都是树中的一个节点
		String[] routePath = uriPattern.split("/");
		RouteNode current = root;
		RouteNode child = null;
		//迭代地向树中添加节点(或者查找节点),直到定位到本URL对应的节点
		for (String s : routePath)
		{
			s = s.trim();
			if ("".equals(s))
				continue;
			child = current.getChild(s);
			//如果已经存在对应子节点就找到定位到它,否则新建节点
			if (child == null)
			{
				//检查是否输入参数类型的节点
				Matcher matcher = paramPattern.matcher(s);
				if (matcher.matches())
				{
					if ((child = current.getChild(PARAM_KEY)) == null)
					{
						child = new RouteNode(matcher.group(1));
						current.addChild(PARAM_KEY, child);
						//System.out.println("Adding " + PARAM_KEY);
					}
				}
				else
				{
					child = new RouteNode();
					current.addChild(s, child);
					//System.out.println("Adding " + s);
				}
			}
			current = child;
			child = null;
		}
		try
		{
			//建立REST资源对象,注册到节点中
			RESTResource res = (RESTResource) resourceClass.newInstance();
			current.setResource(res);
			//调用REST对象的初始化方法
			res.init();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	//按照uri查找对应的REST资源对象,同时将解析出来的参数放到params对象中
	public RESTResource getResource(String uri, Map<String, String> params)
	{
		//System.out.println("Routing for " + uri);
		if (params == null)
		{
			throw new RESTException("\"param\" cannot be null");
		}
		String[] routePath = uri.split("/");
		RouteNode current = root;
		RouteNode child = null;
		for (String s : routePath)
		{
			s = s.trim();
			if ("".equals(s))
				continue;
			child = current.getChild(PARAM_KEY);
			if (child != null)
			{
				params.put(child.getParamName(), s);
				//System.out.println("Routing through " + PARAM_KEY + ":" + s);
			}
			else
			{
				child = current.getChild(s);
				if (child == null)
					return null;
				//System.out.println("Routing through " + s);
			}
			current = child;
			child = null;
		}
		RESTResource result = null;
		if (current.getResource() != null)
			result = (RESTResource) current.getResource();
		return result;
	}
}
