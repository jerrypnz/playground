package jerry.niof.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;

import jerry.niof.util.CONSTANTS;

public class Client extends Thread
{
	private String host;

	private int port;

	private String encoding;

	private SocketChannel channel;

	private ClientCallback callback;

	private CharsetEncoder encoder;

	private CharsetDecoder decoder;

	private Object attachment;

	private boolean running = false;

	public Client(String host, int port, ClientCallback callback)
	{
		this(host, port, callback, "UTF-8");
	}

	public Client(String host, int port, ClientCallback callback,
			String encoding)
	{
		this.host = host;
		this.port = port;
		this.callback = callback;
		this.encoding = encoding;
	}

	public boolean isConnected()
	{
		return channel.isConnected();
	}

	public void close()
	{
		System.out.println("Closing...");
		callback.onStop(this);
		if (isConnected())
			this.sendMsg(CONSTANTS.QUIT_STRING);
		running = false;
		try
		{
			channel.close();
		}
		catch (IOException e)
		{
			return;
		}
		
	}

	public Object getAttachment()
	{
		return attachment;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public String getHost()
	{
		return host;
	}

	public int getPort()
	{
		return port;
	}

	protected void initialize()
	{
		try
		{
			channel = SocketChannel.open();
			channel.configureBlocking(true);
			InetSocketAddress ip = new InetSocketAddress(host, port);
			channel.connect(ip);
			while (!channel.finishConnect())
				;
			Charset charset = Charset.forName(encoding);
			encoder = charset.newEncoder();
			decoder = charset.newDecoder();
			running = true;
		}
		catch (IOException e)
		{
			callback.onException(e, this);
		}
	}

	public String receiveMsg() throws Exception
	{
		String msg = null;
		ByteBuffer buffer = ByteBuffer.allocate(CONSTANTS.BUFFER_SIZE);
		channel.read(buffer);
		buffer.flip();
		CharBuffer charBuffer = decoder.decode(buffer);
		msg = charBuffer.toString();
		if (msg.equals(CONSTANTS.QUIT_STRING))
			this.close();
		return msg;
	}

	public final void run()
	{
		initialize();
		callback.onStart(this);
		while (running)
		{
			try
			{
				String msg = receiveMsg();
				if(CONSTANTS.QUIT_STRING.equals(msg))
					this.close();
				else if(msg!=null && (!"".equals(msg)))
					this.callback.onReceiveMsg(msg, this);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				running = false;
			}
		}
	}

	public void sendMsg(String msg)
	{
		CharBuffer charBuffer = CharBuffer.wrap(msg);
		try
		{
			ByteBuffer buffer = encoder.encode(charBuffer);
			while (buffer.hasRemaining())
				channel.write(buffer);
		}
		catch (Exception e)
		{
			callback.onException(e, this);
		}
	}

	public void setAttachment(Object attachment)
	{
		this.attachment = attachment;
	}

}
