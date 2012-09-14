package com.zhaonan.forum.util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DatabaseUtil
{
	private String driver;

	private String url;

	private String user;

	private String password;
	
	private String lastIdFunc;

	private static DatabaseUtil instance = null;

	private static final String dbConfigFile = "db.properties";

	static
	{
		instance = new DatabaseUtil();
	}

	private DatabaseUtil()
	{
		loadProperties();
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection(url, user, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getLastIdFunc()
	{
		return lastIdFunc;
	}
	
	public Timestamp getCurrentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static DatabaseUtil getInstance()
	{
		return instance;
	}

	private void loadProperties()
	{
		Properties props = new Properties();
		try
		{
			System.out.println("Loading " + dbConfigFile);
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = this.getClass().getClassLoader();
			}
			InputStream in = classLoader.getResourceAsStream(dbConfigFile);
			if (in == null)
				throw new RuntimeException(
						"Can not find the database configuration file");
			props.load(in);
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			user = props.getProperty("username");
			password = props.getProperty("password");
			lastIdFunc = props.getProperty("last_id_func");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
