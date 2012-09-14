package jerry.chatroom.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jerry.niof.server.Server;
import jerry.niof.util.CONSTANTS;



public class ChatServer
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		Server myServer;
		System.out.println("Swing Chatroom server v1.0");
		System.out.println("Author:Jerry");
		System.out.println("copyright (C) JStudio.\n");
		if(args.length!=0)
		{
			String portStr = args[0];
			System.out.println("Using user defined port:" + portStr);
			int port = Integer.parseInt(portStr);
			myServer = new Server(port,new ChatServerModel(),"UTF-8");
		}
		else
		{
			System.out.println("Using default port:" + CONSTANTS.DEFAULT_PORT);
			myServer = new Server(new ChatServerModel(),"UTF-8");
		}
		myServer.start();
		System.out.println("NOTE:Input \"quit\" to stop the server");
		while(true)
		{
			String cmd="";
			cmd = stdin.readLine();
			if("quit".equalsIgnoreCase(cmd))
			{
				myServer.stopServer();
				break;
			}
		}

	}

}
