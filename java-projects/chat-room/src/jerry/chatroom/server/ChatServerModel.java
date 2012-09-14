package jerry.chatroom.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jerry.chatroom.protocol.COMMANDS;
import jerry.niof.server.Server;
import jerry.niof.server.ServerCallback;
import jerry.niof.server.Session;

/**
 * @author alucard
 *
 */
public class ChatServerModel implements ServerCallback
{
	private List<String> users = new ArrayList<String>();

	public void onCloseSession(Server server, Session session)
	{
		String user = (String)session.getAttachment();
		if(user!=null)
		{
			users.remove(user);
			String msg = COMMANDS.USER_LOGOFF + COMMANDS.SPLITTER + user;
			server.sendToAllExcept(msg, session);
		}
		System.out.println("Session-" + session.getId() + " closed");
	}

	/**
	 * 当服务器收到新消息时会调用这个方法，它是服务器的核心所在
	 * 这个方法会解析消息内容，分析出请求内容，然后调用对应的
	 * 处理方法来完成功能.
	 * 从客户端发过来的消息都遵循“命令@命令附件”的格式，命令
	 * 代表不同的请求，比如send是发消息，login是登录，
	 *userlist是请求在线用户列表。
	 * 命令附件是命令的附带信息，比如send后面跟的附件是消息内容
	 * 以及收信人，login后面跟的是登录用户名等等
	 */
	public void onReceiveMsg(String msg, Server server, Session session)
	{
		System.out.println("new msg:" + msg);
		//查找分割符的位置
		int splitPos = msg.indexOf(COMMANDS.SPLITTER);
		//没有分割符说明请求不合法
		if (splitPos < 0)
		{
			session.sendMsg(COMMANDS.ERROR_BAD_COMMAND);
			return;
		}
		//分离出命令
		String cmd = msg.substring(0, splitPos).trim();
		//根据命令执行对应的处理方法
		if (cmd.equalsIgnoreCase(COMMANDS.LOGIN))
		{
			//处理登录
			handleLogin(msg.substring(splitPos+1), server, session);
		}
		else if (cmd.equalsIgnoreCase(COMMANDS.SEND_MSG))
		{
			//处理新消息
			handleNewMessage(msg.substring(splitPos+1), server, session);
		}
		else if( cmd.equalsIgnoreCase(COMMANDS.GET_ONLINE_USER))
		{
			handleOnlineUser(server, session);
		}
		else
		{
			//非法命令,回送错误信息
			session.sendMsg(COMMANDS.ERROR_BAD_COMMAND);
		}

	}

	public void onServerException(Server server, Exception e)
	{
		e.printStackTrace();
		try
		{
			server.stopServer();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void onSessionException(Server server, Session session, Exception e)
	{
		e.printStackTrace();
		try
		{
			session.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void onStartServer(Server server)
	{
		System.out.println("Chatroom server started.");
	}

	public void onStartSession(Server server, Session session)
	{
		System.out.println("New client connected.");
	}

	public void onStopServer(Server server)
	{
		System.out.println("Stoping server.");
	}

	//处理用户登录
	protected void handleLogin(String username, Server server, Session session)
	{
		String backMsg = COMMANDS.ERROR_USERNAME_EXISTS;
		String newUser = username.trim();
		if(!users.contains(newUser))
		{
			users.add(newUser);
			session.setAttachment(newUser);
			String newUserMsg = COMMANDS.USER_LOGIN + COMMANDS.SPLITTER + newUser;
			server.sendToAllExcept(newUserMsg, session);
			backMsg = COMMANDS.LOGIN_SUCCESS;
		}	
		session.sendMsg(backMsg);	
	}
	
	//处理查询在线用户的请求
	protected void handleOnlineUser(Server server, Session session)
	{
		StringBuffer buffer = new StringBuffer(COMMANDS.ONLINE_USER);
		for(Iterator<String> it = users.iterator();it.hasNext();)
		{
			buffer.append(COMMANDS.SPLITTER);
			buffer.append(it.next());
		}
		String onlineUserMsg = buffer.toString();
		session.sendMsg(onlineUserMsg);	
	}
	
	//当用户发送新消息的处理
	protected void handleNewMessage(String msg, Server server, Session session)
	{
		StringTokenizer tokens = new StringTokenizer(msg, COMMANDS.SPLITTER);
		String username = tokens.nextToken().trim();
		String message = tokens.nextToken();
		String sender = (String) session.getAttachment();
		if(sender==null || "".equals(sender))
		{
			String errorMsg = COMMANDS.ERROR_NOT_LOGGED_IN;
			session.sendMsg(errorMsg);
		}
		else if (username.equalsIgnoreCase(COMMANDS.ALL))
		{
			//如果接收者是所有人,则向所有客户端发送消息
			String backMsg = COMMANDS.NEW_MSG + COMMANDS.SPLITTER + sender
					+ COMMANDS.SPLITTER + message;
			server.sendToAllSessions(backMsg);
		}
		else if (!users.contains(username))
		{
			//如果接收者不存在,回送错误消息
			String errorMsg = COMMANDS.ERROR_USER_NOT_EXIST + COMMANDS.SPLITTER
					+ username;
			session.sendMsg(errorMsg);
		}
		else
		{
			//从当前session列表中找到接收者对应的session,并发送消息
			Iterator<Session> it = server.sessionIterator();
			Session to = null;
			while (it.hasNext())
			{
				Session temp = null;
				temp = it.next();
				String tempName = (String) temp.getAttachment();
				if (tempName.equalsIgnoreCase(username))
				{
					to = temp;
					break;
				}
			}
			if (to == null)
			{
				String errorMsg = COMMANDS.ERROR_USER_NOT_EXIST + COMMANDS.SPLITTER
						+ username;
				session.sendMsg(errorMsg);
			}
			else
			{
				String backMsg = COMMANDS.NEW_PRIVATE_MSG + COMMANDS.SPLITTER
						+ sender + COMMANDS.SPLITTER + message;
				to.sendMsg(backMsg);
			}
		}
	}

}
