/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhaonan.forum.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jerry
 */
public class SchemaGen
{
    public static void generateSchema()
    {
        ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("dbschema.sql");
        if(in==null)
            throw new RuntimeException("Can not read dbschema.sql");
        BufferedReader file = new BufferedReader(
                    new InputStreamReader(in)
                );
        String temp;
        try
        {
        	Connection conn = DatabaseUtil.getInstance().getConnection();
            Statement stat = conn.createStatement();
            while((temp=file.readLine())!=null)
            {
                stat.execute(temp);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void dropSchema()
    {
    	Connection conn = DatabaseUtil.getInstance().getConnection();
    	try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("drop table t_post");
			stmt.executeUpdate("drop table t_topic");
			stmt.executeUpdate("drop table t_board");
			stmt.executeUpdate("drop table t_user");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args)
    {
    	generateSchema();
    	System.out.println("Database tables generated");
    }
}
