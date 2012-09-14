/**
 * Session.java
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

import jerry.niof.util.CONSTANTS;
import java.io.IOException;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;

public class Session
{
	private Server server;

	private SocketChannel channel;

	private ServerCallback callback;

	private int id;

	private String encoding;

	private Charset charset;

	private CharsetEncoder encoder;

	private CharsetDecoder decoder;

	private Object attachment;

	/**
	 * @return the attachment
	 */
	public Object getAttachment()
	{
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(Object attachment)
	{
		this.attachment = attachment;
	}

	public Session(int id, String encoding, SocketChannel channel,
			ServerCallback callback, Server server)
	{
		this.channel = channel;
		this.id = id;
		this.callback = callback;
		if (!"".equals(encoding))
			this.encoding = encoding;
		else
			this.encoding = CONSTANTS.DEFAULT_ENCODING;
		this.server = server;
		charset = Charset.forName(encoding);
		encoder = charset.newEncoder();
		decoder = charset.newDecoder();
	}

	public Session(int id, SocketChannel channel, ServerCallback callback,
			Server server)
	{
		this(id, CONSTANTS.DEFAULT_ENCODING, channel, callback, server);
	}

	/**
	 * @return the channel
	 */
	public SocketChannel getChannel()
	{
		return channel;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding()
	{
		return encoding;
	}

	/**
	 * @return the name
	 */
	public int getId()
	{
		return id;
	}

	public String receiveMsg()
	{
		String msg = null;
		ByteBuffer buffer = ByteBuffer.allocate(CONSTANTS.BUFFER_SIZE);
		try
		{
			/*
			 * if (!channel.isOpen()) throw new SessionClosedException();
			 */
			while (channel.read(buffer) > 0)
				;
			buffer.flip();
			CharBuffer charBuffer = decoder.decode(buffer);
			msg = charBuffer.toString();
			/*
			 * byte[] data = new byte[buffer.limit()]; buffer.get(data); msg =
			 * new String(data);
			 */
			if (msg.equals(CONSTANTS.QUIT_STRING))
				this.close();
			else
				callback.onReceiveMsg(msg, server, this);
		}
		catch (Exception e)
		{
			callback.onSessionException(server, this, e);
		}
		return msg;
	}

	public void sendMsg(String msg)
	{
		CharBuffer charBuffer = CharBuffer.wrap(msg);
		try
		{
			ByteBuffer buffer = encoder.encode(charBuffer);
			/*
			 * ByteBuffer buffer = ByteBuffer.allocate(2048);
			 * buffer.put(msg.getBytes());
			 */
			/*
			 * if (!channel.isOpen()) throw new SessionClosedException();
			 */
			while (buffer.hasRemaining())
				channel.write(buffer);
		}
		catch (Exception e)
		{
			callback.onSessionException(server, this, e);
		}
	}

	public void close() throws IOException
	{
		callback.onCloseSession(server, this);
		server.deleteSession(this);
		channel.close();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
}
