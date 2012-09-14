/**
 * ServerCallback.java
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
 * 	   了解.代码请随便使用.	
 */

package jerry.niof.server;

public interface ServerCallback
{
	public void onReceiveMsg(String msg,Server server,Session session);
	public void onStartServer(Server server);
	public void onStopServer(Server server);
	public void onStartSession(Server server,Session session);
	public void onCloseSession(Server server,Session session);
	public void onServerException(Server server,Exception e);
	public void onSessionException(Server server,Session session,Exception e);
}
