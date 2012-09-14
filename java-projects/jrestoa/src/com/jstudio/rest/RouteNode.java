package com.jstudio.rest;

import java.util.HashMap;
import java.util.Map;


public class RouteNode
{
	private RouteNode parent;
	
	private RESTResource resource = null;
	
	private String paramName = "";
	
	private Map<String,RouteNode> children = new HashMap<String,RouteNode>();
	

	public RouteNode()
	{
	}
	
	public RouteNode(String paramName)
	{
		this.paramName = paramName;
	}
	
	public void addChild(String key,RouteNode child)
	{
		if(children.containsKey(key))
			return;
		children.put(key, child);
	}
	
	public RouteNode getChild(String key)
	{
		return children.get(key);
	}

	/**
	 * @return the paramName
	 */
	public String getParamName()
	{
		return paramName;
	}

	/**
	 * @return the parent
	 */
	public RouteNode getParent()
	{
		return parent;
	}

	/**
	 * @return the res
	 */
	public RESTResource getResource()
	{
		return resource;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName)
	{
		this.paramName = paramName;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(RouteNode parent)
	{
		this.parent = parent;
	}

	/**
	 * @param res the res to set
	 */
	public void setResource(RESTResource res)
	{
		this.resource = res;
	}
	

}
