package com.jstudio.rest.exception;

public class RESTException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RESTException(String info)
	{
		super(info);
	}
}
