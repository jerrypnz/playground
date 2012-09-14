package com.zhaonan.forum.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zhaonan.forum.po.User;
import com.zhaonan.forum.service.UserService;
import com.zhaonan.forum.util.DatabaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUserServiceImpl implements UserService
{

	@Override
	public void delete(final int userId)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{

			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "delete from t_user where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, userId);
					stmt.executeUpdate();
					stmt.close();					
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listAll()
	{
		final List<User> temp = (List<User>) new JdbcTemplate().doTask(new JdbcTask()
		{
			public Object executeTask(Connection conn)
			{
				User temp = null;
				List<User> userList = new ArrayList<User>();
				try
				{
					String sql = "select * from t_user";
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet result = stmt.executeQuery();
					while(result.next())
					{
						temp = new User();
						temp.setId(result.getInt("id"));
						temp.setLoginName(result.getString("login_name"));
						temp.setNickName(result.getString("nick_name"));
						temp.setEmail(result.getString("email"));
						temp.setQq(result.getString("qq"));
						temp.setHomepage(result.getString("homepage"));
						temp.setMark(result.getInt("mark"));
						temp.setRank(result.getInt("rank"));
						userList.add(temp);
					}
					stmt.close();

				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return userList;
			}
		});
		
		return temp;
	}

	@Override
	public User login(final String username, final String password)
	{
		User candidate = this.getByName(username);
		System.out.println(candidate);
		if(candidate!=null && password.equals(candidate.getPassword()))
			return candidate;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> query(final String sql)
	{
		final List<User> temp = (List<User>) new JdbcTemplate().doTask(new JdbcTask()
		{
			public Object executeTask(Connection conn)
			{
				User temp = null;
				List<User> userList = new ArrayList<User>();
				try
				{
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet result = stmt.executeQuery();
					while(result.next())
					{
						temp = new User();
						temp.setId(result.getInt("id"));
						temp.setLoginName(result.getString("login_name"));
						temp.setNickName(result.getString("nick_name"));
						temp.setEmail(result.getString("email"));
						temp.setQq(result.getString("qq"));
						temp.setHomepage(result.getString("homepage"));
						temp.setMark(result.getInt("mark"));
						temp.setRank(result.getInt("rank"));
						userList.add(temp);
					}
					stmt.close();

				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return userList;
			}
		});
		
		return temp;
	}

	@Override
	public void save(final User user)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{

			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "insert into t_user(login_name,nick_name,password,email,homepage,qq,mark,rank) values(?,?,?,?,?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, user.getLoginName());
					stmt.setString(2, user.getNickName());
					stmt.setString(3, user.getPassword());
					stmt.setString(4, user.getEmail());
					stmt.setString(5, user.getHomepage());
					stmt.setString(6, user.getQq());
					stmt.setInt(7, user.getMark());
					stmt.setInt(8, user.getRank());
					stmt.executeUpdate();
					String getId = DatabaseUtil.getInstance().getLastIdFunc();
					Statement st2 = conn.createStatement();
					ResultSet set = st2.executeQuery(getId);
					if(set.next())
					{
						user.setId(set.getInt(1));
					}
					stmt.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@Override
	public void update(final User user)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "update t_user set login_name=?,nick_name=?,password=?,email=?,homepage=?,qq=?,mark=?,rank=? where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, user.getLoginName());
					stmt.setString(2, user.getNickName());
					stmt.setString(3, user.getPassword());
					stmt.setString(4, user.getEmail());
					stmt.setString(5, user.getHomepage());
					stmt.setString(6, user.getQq());
					stmt.setInt(7, user.getMark());
					stmt.setInt(8, user.getRank());
					stmt.setInt(9, user.getId());
					stmt.executeUpdate();
					stmt.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@Override
	public User get(final int userId)
	{
		User temp = (User) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				User temp = null;
				try
				{
					String sql = "select * from t_user where id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, userId);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = new User();
						temp.setId(result.getInt("id"));
						temp.setLoginName(result.getString("login_name"));
						temp.setNickName(result.getString("nick_name"));
						temp.setPassword(result.getString("password"));
						temp.setEmail(result.getString("email"));
						temp.setQq(result.getString("qq"));
						temp.setHomepage(result.getString("homepage"));
						temp.setMark(result.getInt("mark"));
						temp.setRank(result.getInt("rank"));
					}
					stmt.close();

				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return temp;
			}
		});
		return temp;
	}

	@Override
	public User getByName(final String loginName)
	{
		User temp = (User) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				User temp = null;
				try
				{
					String sql = "select * from t_user where login_name = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, loginName);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = new User();
						temp.setId(result.getInt("id"));
						temp.setLoginName(result.getString("login_name"));
						temp.setPassword(result.getString("password"));
						temp.setNickName(result.getString("nick_name"));
						temp.setEmail(result.getString("email"));
						temp.setQq(result.getString("qq"));
						temp.setHomepage(result.getString("homepage"));
						temp.setMark(result.getInt("mark"));
						temp.setRank(result.getInt("rank"));
					}
					stmt.close();

				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return temp;
			}
		});
		return temp;
	}
}
