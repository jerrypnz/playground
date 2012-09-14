package com.jstudio.rest.util;

import java.util.Map;

public class DebugHelper
{
	public static void printParams(Map<String,String> params)
	{
		System.out.println("Parameters:");
		for(String key : params.keySet())
		{
			String value = params.get(key);
			System.out.println(key + " = " + value);
		}
	}
}
