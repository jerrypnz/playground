/**
 * SessionContainer.java
 * 名称:NIO Framework
 * 作者:彭睿
 * 邮箱:c_jerry@126.com
 * 博客:http://moonranger.blog.sohu.com
 * 	   http://blog.csdn.com/moonranger
 * 介绍:NIO Framework是本人花了两天完成的一个超超超轻量级
 * 	   的C/S框架,只对Java的NIO库中的SocketChannel等进行了
 * 	   最基本的封装,适合进行简单的,基于文本的C/S应用的开发.
 * 	   本框架还没有进行全面的测试,希望大家指正框架中的问题.
 * 	   希望这份源代码能让你学习到Java1.4以后新增的NIO库有所
 * 	   了解.代码请随便使用,如果想修改,请联系我,谢谢!	
 */

package jerry.niof.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionContainer
{
	private Map<Integer,Session> sessionMap = new HashMap<Integer,Session>();
	private static int sessionCount = 0;
	
	public static int currentCount()
	{
		return sessionCount;
	}
	
	public int push(Session session)
	{
		int id = sessionCount++;
		sessionMap.put(id, session);
		return id;
	}
	
	
	public Session getById(int id)
	{
		if(sessionMap.containsKey(id))
			return sessionMap.get(id);
		else
			return null;
	}
	
	public void delete(Session session)
	{
		int id = session.getId();
		sessionMap.remove(id);	
	}
	
	public void delete(int id)
	{
		sessionMap.remove(id);
	}
	
	public Iterator<Session> iterator()
	{
		return sessionMap.values().iterator();
	}
	
}
