package com.jstudio.jrestoa.domain.exception;

public class BusinessException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String msg)
	{
		super(msg);
	}
	
	public BusinessException(String msg,Exception e)
	{
		super(msg,e);
	}

}
