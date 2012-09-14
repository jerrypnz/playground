package jerry.chatroom.client;

import jerry.niof.client.Client;

public class ClientCallback implements jerry.niof.client.ClientCallback
{

	public void onException(Exception e, Client client)
	{
		e.printStackTrace();
	}

	public void onReceiveMsg(String msg, Client client)
	{
		System.out.println("new msg:" + msg + "\n");
	}

	public void onStart(Client client)
	{
		System.out.println("Client started");
	}

	public void onStop(Client client)
	{
		System.out.println("Client stopped");
	}

}
