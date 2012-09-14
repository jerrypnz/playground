/**
 * Server.java
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import jerry.niof.util.CONSTANTS;

public final class Server extends Thread
{
	private ServerSocketChannel serverChannel;

	private ServerCallback callback;

	private SessionContainer sessions = new SessionContainer();

	private int port;

	private String encoding;

	private Selector selector;

	private boolean running;

	public Server(int port, ServerCallback callback, String encoding)
	{
		this.port = port;
		this.callback = callback;
		this.encoding = encoding;
	}

	public Server(int port, ServerCallback callback)
	{
		this(port, callback, CONSTANTS.DEFAULT_ENCODING);
	}

	public Server(ServerCallback callback, String encoding)
	{
		this(CONSTANTS.DEFAULT_PORT, callback, encoding);
	}

	public Server(ServerCallback callback)
	{
		this(CONSTANTS.DEFAULT_PORT, callback,
				CONSTANTS.DEFAULT_ENCODING);
	}

	public final void run()
	{
		initialize();
		callback.onStartServer(this);
		while (running)
		{
			int n = 0;
			try
			{
				n = selector.select(1000);
			}
			catch (IOException e)
			{
				callback.onServerException(this, e);
			}
			if (n <= 0)
				continue;

			Iterator it = selector.selectedKeys().iterator();
			while (it.hasNext())
			{
				SelectionKey key = (SelectionKey) it.next();
				it.remove();
				if (key.isAcceptable())
				{
					ServerSocketChannel serverChannel = (ServerSocketChannel) key
							.channel();
					try
					{
						SocketChannel newChannel = serverChannel
								.accept();
						makeNewSession(newChannel);
					}
					catch (IOException e)
					{
						callback.onServerException(
								this, e);
					}
				}
				if (key.isReadable())
				{
					Session currentSession = (Session) key
							.attachment();
					currentSession.receiveMsg();
				}
			}

		}
	}

	public void deleteSession(Session session)
	{
		sessions.delete(session);
	}

	public void deleteSession(int id)
	{
		sessions.delete(id);
	}

	protected void initialize()
	{
		try
		{
			serverChannel = ServerSocketChannel.open();
			InetSocketAddress address = new InetSocketAddress(
					this.port);
			serverChannel.socket().bind(address);
			serverChannel.configureBlocking(false);
			selector = Selector.open();
			serverChannel
					.register(selector,
							SelectionKey.OP_ACCEPT);
		}
		catch (Exception e)
		{
			callback.onServerException(this, e);
		}
		running = true;
	}

	protected void makeNewSession(SocketChannel channel)
	{
		try
		{
			channel.configureBlocking(false);
			Session newSession = new Session(0, encoding, channel,
					callback, this);
			int id = sessions.push(newSession);
			newSession.setId(id);
			channel.register(selector, SelectionKey.OP_READ
					| SelectionKey.OP_WRITE, newSession);
			callback.onStartSession(this, newSession);
		}
		catch (Exception e)
		{
			callback.onServerException(this, e);
		}
	}

	public void sendToAllSessions(String msg)
	{
		Iterator<Session> it = sessions.iterator();
		while (it.hasNext())
			it.next().sendMsg(msg);
	}

	public void sendToAllExcept(String msg, Session session)
	{
		Iterator<Session> it = sessions.iterator();
		while (it.hasNext())
		{
			Session temp = it.next();
			if (temp.equals(session))
				continue;
			temp.sendMsg(msg);
		}
	}

	public Iterator<Session> sessionIterator()
	{
		return sessions.iterator();
	}

	public void stopServer() throws IOException
	{
		callback.onStopServer(this);
		sendToAllSessions(CONSTANTS.QUIT_STRING);
		running = false;
		Iterator<Session> it = sessions.iterator();
		while (it.hasNext())
		{
			Session current = it.next();
			current.close();
		}
		serverChannel.close();
	}
}
