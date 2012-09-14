package com.jstudio.jrestoa.hbm;

public interface InTransaction
{
	public void execute() throws Exception;
	public void onException(Throwable e) throws Exception;
}
