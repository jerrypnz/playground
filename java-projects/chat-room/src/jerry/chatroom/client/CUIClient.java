package jerry.chatroom.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jerry.niof.client.Client;

public class CUIClient
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String host;
		int port;
		System.out.println("Please input host name:");
		host = stdin.readLine();
		System.out.println("Please input host port:");
		String temp = stdin.readLine();
		port = Integer.parseInt(temp);
		Client client = new Client(host, port, new ClientCallback());
		client.start();
		while (true)
		{
			System.out.println("\nPlease input something:");
			temp = stdin.readLine();
			if ("quit".equalsIgnoreCase(temp))
				client.close();
			else
				client.sendMsg(temp);
		}

	}

}
