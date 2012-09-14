package jerry.niof.client;


public interface ClientCallback
{
	public void onReceiveMsg(String msg, Client client);

	public void onStart(Client client);

	public void onStop(Client client);

	public void onException(Exception e, Client client);

}
